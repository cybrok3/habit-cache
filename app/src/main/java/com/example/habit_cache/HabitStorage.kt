package com.example.habit_cache

import android.content.SharedPreferences
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * This file is directing the data part of the app, counters dates, etc.
 */

// Preferences key names
const val PREFS_NAME = "habit_cache_daily_store"

private const val KEY_DATE = "date"
private const val KEY_STARTED_TODAY = "started_today"
private const val KEY_ENTRIES_JSON = "entries_json"

// Legacy keys for one-time migration
private const val KEY_COFFEE = "coffee"
private const val KEY_IQOS = "iqos"
private const val KEY_CIGGIES = "ciggies"

enum class HabitValueKind {
    INT,
    FLOAT
}

enum class HabitActionType {
    INCREMENT_ONE,
    ADD_CUSTOM
}

// Data class for a Habit
data class Habit(
    val id: String,
    val name: String,
    val iconRes: Int,
    val valueKind: HabitValueKind,
    val actionType: HabitActionType
)

// Data class for a Habit entry on the Habits List
data class HabitEntry(
    val habitId: String,
    val value: Float
)

// Data class for the daily habit state
data class DailyHabitState(
    val date: String,
    val startedToday: Boolean,
    val entries: List<HabitEntry>
)

data class DisplayDateParts(
    val topLine: String,
    val bottomLine: String
)

// Default legacy habit list
val DEFAULT_HABITS = listOf(
    Habit("coffee", "Coffee", 0, HabitValueKind.INT, HabitActionType.INCREMENT_ONE),
    Habit("iqos", "IQOS", 0, HabitValueKind.INT, HabitActionType.INCREMENT_ONE),
    Habit("ciggies", "Ciggies", R.drawable.ciggy_icon, HabitValueKind.INT, HabitActionType.INCREMENT_ONE)
)

fun todayKey(): String {
    return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
}

// For more pretty display of the day of the date displayed in menu screen
private fun ordinalSuffix(day: Int): String {
    if (day % 100 in 11..13) return "th"
    return when (day % 10) {
        1 -> "st"
        2 -> "nd"
        3 -> "rd"
        else -> "th"
    }
}

// This function formats the date parts for displaying on the menu screen
fun displayDateFromKey(key: String): DisplayDateParts {
    val parts = key.split("-")
    if (parts.size != 3) return DisplayDateParts(key, "")

    val year = parts[0]
    val monthNumber = parts[1].toIntOrNull() ?: return DisplayDateParts(key, "")
    val day = parts[2].toIntOrNull() ?: return DisplayDateParts(key, "")
    if (monthNumber !in 1..12) return DisplayDateParts(key, "")

    val monthNames = listOf(
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    )
    val monthName = monthNames[monthNumber - 1]
    return DisplayDateParts(
        topLine = "${day}${ordinalSuffix(day)} $monthName",
        bottomLine = year
    )
}

private fun zeroEntriesFromDefinitions(defs: List<Habit>): List<HabitEntry> {
    return defs.map { HabitEntry(habitId = it.id, value = 0f) }
}

private fun normalizeEntries(entries: List<HabitEntry>, defs: List<Habit>): List<HabitEntry> {
    val byId = entries.associateBy { it.habitId }
    return defs.map { def ->
        byId[def.id] ?: HabitEntry(def.id, 0f)
    }
}

private fun entriesToJson(entries: List<HabitEntry>): String {
    val arr = JSONArray()
    entries.forEach { e ->
        val obj = JSONObject()
        obj.put("habitId", e.habitId)
        obj.put("value", e.value.toDouble())
        arr.put(obj)
    }
    return arr.toString()
}

private fun entriesFromJson(json: String): List<HabitEntry> {
    val arr = JSONArray(json)
    val list = mutableListOf<HabitEntry>()
    for (i in 0 until arr.length()) {
        val obj = arr.getJSONObject(i)
        list.add(
            HabitEntry(
                habitId = obj.getString("habitId"),
                value = obj.getDouble("value").toFloat()
            )
        )
    }
    return list
}

private fun migrateLegacyEntries(prefs: SharedPreferences): List<HabitEntry> {
    return listOf(
        HabitEntry("coffee", prefs.getInt(KEY_COFFEE, 0).toFloat()),
        HabitEntry("iqos", prefs.getInt(KEY_IQOS, 0).toFloat()),
        HabitEntry("ciggies", prefs.getInt(KEY_CIGGIES, 0).toFloat())
    )
}

// Loads state of the habits counters according to the day, if new day we have clean data by default
fun readOrResetForToday(
    prefs: SharedPreferences,
    today: String,
    defs: List<Habit> = DEFAULT_HABITS
): DailyHabitState {
    val savedDate = prefs.getString(KEY_DATE, null)
    val startedToday = prefs.getBoolean(KEY_STARTED_TODAY, false)

    if (savedDate == today) {
        val json = prefs.getString(KEY_ENTRIES_JSON, null)
        val loadedEntries = if (json.isNullOrBlank()) {
            migrateLegacyEntries(prefs)
        } else {
            runCatching { entriesFromJson(json) }.getOrDefault(emptyList())
        }
        val normalized = normalizeEntries(loadedEntries, defs)
        // ensure JSON exists after migration/normalize
        prefs.edit().putString(KEY_ENTRIES_JSON, entriesToJson(normalized)).apply()
        return DailyHabitState(today, startedToday, normalized)
    }

    val reset = DailyHabitState(
        date = today,
        startedToday = false,
        entries = zeroEntriesFromDefinitions(defs)
    )
    writeDailyState(prefs, reset)
    return reset
}

// Writes state on the preferences
fun writeDailyState(prefs: SharedPreferences, state: DailyHabitState) {
    prefs.edit()
        .putString(KEY_DATE, state.date)
        .putBoolean(KEY_STARTED_TODAY, state.startedToday)
        .putString(KEY_ENTRIES_JSON, entriesToJson(state.entries))
        .apply()
}
