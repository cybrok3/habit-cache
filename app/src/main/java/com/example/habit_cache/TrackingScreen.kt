package com.example.habit_cache

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habit_cache.ui.theme.HabitCacheColors
import java.util.Locale

/**
 * This file directs the UI elements of the Tracking screen of the app
 */

private val HabitIconSlotWidth = 34.dp
private val HabitIconSize = 50.dp
private val HabitIconStartPadding = 0.dp
private val HabitIconTextGap = 12.dp

val buttonFillColor = HabitCacheColors.IronyAmberBrown
val buttonBorderColor = HabitCacheColors.IronyOchre
val buttonTextColor = HabitCacheColors.IronySand

// This function takes the variables from the memory and the callback functions to start the tracking process
@Composable
fun TrackingScreen(
    habits: List<Habit>,
    entries: List<HabitEntry>,
    onClearCache: () -> Unit,
    onIncrementHabit: (String) -> Unit,
    onAddCustomHabit: (String) -> Unit
) {

    Box( // Outer-most box
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp, end = 24.dp, bottom = 24.dp)
    ) {
        PixelShadowBox(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 24.dp)
                .size(48.dp)
        ) {
            OutlinedButton( // Clear cache button
                onClick = onClearCache,
                shape = ZeroCornerShape,
                border = BorderStroke(ButtonBorderThickness, buttonBorderColor),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = buttonFillColor,
                    contentColor = buttonTextColor
                ),
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_menu_delete),
                    contentDescription = "Clear today's cache"
                )
            }
        }

        Box( // Today's Habits title top left
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 24.dp, top = 20.dp)
                .width(176.dp)
                .height(86.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(3.dp),
                horizontalAlignment = Alignment.Start
            ) {
                AutoFitDateLine(
                    text = "Today's",
                    minFontSize = 11.sp,
                    maxFontSize = 17.sp,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(x = (-4).dp),
                    color = HabitCacheColors.IronyNavy
                )
                AutoFitDateLine(
                    text = "Habits",
                    minFontSize = 18.sp,
                    maxFontSize = 32.sp,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth(),
                    color = HabitCacheColors.IronyNavy
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 140.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            habits.forEach { habit ->
                val value = entries.firstOrNull { it.habitId == habit.id }?.value ?: 0f
                HabitRow(
                    habit = habit,
                    value = value,
                    onIncrement = {
                        when (habit.actionType) {
                            HabitActionType.INCREMENT_ONE -> onIncrementHabit(habit.id)
                            HabitActionType.ADD_CUSTOM -> onAddCustomHabit(habit.id) // your other function
                        }
                    }
                )
            }
        }
    }
}

// Function to dynamically add each row, without hardcoding them
@Composable
fun HabitRow(habit: Habit, value: Float, onIncrement: () -> Unit) {
    val displayValue = when (habit.valueKind) {
        HabitValueKind.INT -> value.toInt().toString()
        HabitValueKind.FLOAT -> String.format(Locale.US, "%.1f", value)
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .width(HabitIconSlotWidth),
                contentAlignment = Alignment.CenterStart
            ) {
                if (habit.iconRes != 0) {
                    Image(
                        painter = painterResource(id = habit.iconRes),
                        contentDescription = "${habit.name} icon",
                        modifier = Modifier
                            .padding(start = HabitIconStartPadding)
                            .size(HabitIconSize)
                    )
                }
            }
            Spacer(modifier = Modifier.width(HabitIconTextGap))
            Text("${habit.name}: $displayValue", fontSize = 15.sp, fontFamily = PixelFont, color = HabitCacheColors.IronyNavy)
        }
        PixelShadowBox(modifier = Modifier.size(52.dp)) {
            OutlinedButton(
                onClick = onIncrement,
                shape = ZeroCornerShape,
                border = BorderStroke(ButtonBorderThickness, buttonBorderColor),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = buttonFillColor,
                    contentColor = buttonTextColor
                ),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(0.dp)
            ) {
                val actionLabel = if (habit.actionType == HabitActionType.ADD_CUSTOM) "+" else "+1"
                Text(actionLabel, fontFamily = PixelFont, fontSize = 15.sp)
            }
        }
    }
}
