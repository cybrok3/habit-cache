package com.example.habit_cache

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

/**
 * Main Habit cache app
 */

// Load my pixel font
val PixelFont = FontFamily(Font(R.font.press_start_2p))

// Make all boxes have sharp edges
val ZeroCornerShape = RoundedCornerShape(0.dp)

// General button border thickness tweaker
val ButtonBorderThickness = 5.dp

@Composable
fun HabitCacheApp() {
    // Get access to android system context e.g. files/data
    val context = LocalContext.current
    /**
     * Run this block once, keep the result, and reuse it during future recompositions.
     * Open the "habit_cache_daily_store" file that only you have private access at,
     * and remember the key-value pairs
     */
    val prefs = remember {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    // All the variables the app needs
    var trackingStarted by rememberSaveable { mutableStateOf(false) }
    var currentDate by remember { mutableStateOf(todayKey()) }
    var startedToday by remember { mutableStateOf(false) }
    var coffee by rememberSaveable { mutableIntStateOf(0) }
    var iqos by rememberSaveable { mutableIntStateOf(0) }
    var ciggies by rememberSaveable { mutableIntStateOf(0) }
    var calories by rememberSaveable { mutableIntStateOf(0) }

    // Function for reading from storage and updating all the variables
    fun loadStateForToday() {
        val state = readOrResetForToday(prefs, todayKey())
        currentDate = state.date
        startedToday = state.startedToday
        coffee = state.coffee
        iqos = state.iqos
        ciggies = state.ciggies
        calories = state.calories
    }

    fun saveCurrentState() {
        writeDailyState(
            prefs,
            DailyHabitState(
                date = currentDate,
                startedToday = startedToday,
                coffee = coffee,
                iqos = iqos,
                ciggies = ciggies,
                calories = calories
            )
        )
    }

    fun incrementWithDateCheck(update: () -> Unit) {
        if (todayKey() != currentDate) {
            loadStateForToday()
        }
        update()
        if (!startedToday) {
            startedToday = true
        }
        saveCurrentState()
    }

    fun clearCurrentDayCache() {
        currentDate = todayKey()
        startedToday = false
        coffee = 0
        iqos = 0
        ciggies = 0
        calories = 0
        saveCurrentState()
    }

    LaunchedEffect(Unit) {
        loadStateForToday()
    }

    BackHandler(enabled = trackingStarted) {
        trackingStarted = false
    }

    if (!trackingStarted) {
        MenuScreen(
            currentDate = currentDate,
            startTrackingLabel = if (startedToday) "Resume" else "Start",
            onStartTracking = {
                loadStateForToday()
                if (!startedToday) {
                    startedToday = true
                    saveCurrentState()
                }
                trackingStarted = true
            }
        )
    } else {
        TrackingScreen(
            coffee = coffee,
            iqos = iqos,
            ciggies = ciggies,
            calories = calories,
            onClearCache = { clearCurrentDayCache() },
            onCoffeeIncrement = { incrementWithDateCheck { coffee++ } },
            onIqosIncrement = { incrementWithDateCheck { iqos++ } },
            onCiggiesIncrement = { incrementWithDateCheck { ciggies++ } },
            onCaloriesIncrement = { incrementWithDateCheck { calories++ } }
        )
    }
}

@Composable
fun AutoFitDateLine(
    text: String,
    minFontSize: TextUnit,
    maxFontSize: TextUnit,
    textAlign: TextAlign = TextAlign.Start,
    modifier: Modifier = Modifier
) {
    val textMeasurer = rememberTextMeasurer()
    val density = LocalDensity.current

    BoxWithConstraints(modifier = modifier) {
        val availableWidthPx = with(density) { maxWidth.toPx() }
        val fittedFontSize = remember(text, availableWidthPx, minFontSize, maxFontSize) {
            var low = with(density) { minFontSize.toPx() }
            var high = with(density) { maxFontSize.toPx() }
            var best = low

            repeat(18) {
                val mid = (low + high) / 2f
                val trialFontSize = with(density) { mid.toSp() }
                val measured = textMeasurer.measure(
                    text = text,
                    style = TextStyle(
                        fontFamily = PixelFont,
                        fontSize = trialFontSize
                    ),
                    maxLines = 1,
                    softWrap = false
                )

                if (measured.size.width <= availableWidthPx) {
                    best = mid
                    low = mid + 0.25f
                } else {
                    high = mid - 0.25f
                }
            }
            with(density) { best.toSp() }
        }

        Text(
            text = text,
            fontFamily = PixelFont,
            fontSize = fittedFontSize,
            textAlign = textAlign,
            maxLines = 1,
            softWrap = false,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
