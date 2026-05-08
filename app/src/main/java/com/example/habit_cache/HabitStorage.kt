package com.example.habit_cache

import android.content.SharedPreferences
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * This file is directing the data part of the app, counters dates, etc.
 */

// Preferences key names
const val PREFS_NAME = "habit_cache_daily_store"
private const val KEY_DATE = "date"
private const val KEY_COFFEE = "coffee"
private const val KEY_IQOS = "iqos"
private const val KEY_CIGGIES = "ciggies"
private const val KEY_CALORIES = "calories"
private const val KEY_STARTED_TODAY = "started_today"

// Data class for the daily habit state
data class DailyHabitState(
    val date: String,
    val startedToday: Boolean,
    val coffee: Int,
    val iqos: Int,
    val ciggies: Int,
    val calories: Int
)

data class DisplayDateParts(
    val topLine: String,
    val bottomLine: String
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

// Loads state of the habits counters according to the day, if new day we have clean data by default
fun readOrResetForToday(prefs: SharedPreferences, today: String): DailyHabitState {
    val savedDate = prefs.getString(KEY_DATE, null)
    return if (savedDate == today) {
        DailyHabitState(
            date = today,
            startedToday = prefs.getBoolean(KEY_STARTED_TODAY, false),
            coffee = prefs.getInt(KEY_COFFEE, 0),
            iqos = prefs.getInt(KEY_IQOS, 0),
            ciggies = prefs.getInt(KEY_CIGGIES, 0),
            calories = prefs.getInt(KEY_CALORIES, 0)
        )
    } else {
        val resetState = DailyHabitState(today, false, 0, 0, 0, 0)
        writeDailyState(prefs, resetState)
        resetState // Return this reseted state
    }
}

// Writes state on the preferences
fun writeDailyState(prefs: SharedPreferences, state: DailyHabitState) {
    prefs.edit()
        .putString(KEY_DATE, state.date)
        .putBoolean(KEY_STARTED_TODAY, state.startedToday)
        .putInt(KEY_COFFEE, state.coffee)
        .putInt(KEY_IQOS, state.iqos)
        .putInt(KEY_CIGGIES, state.ciggies)
        .putInt(KEY_CALORIES, state.calories)
        .apply()
}
