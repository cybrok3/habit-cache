package com.example.habit_cache

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * This file directs the UI elements of the Tracking screen of the app
 */

// This function takes the variables from the memory and the callback functions to start the tracking process
@Composable
fun TrackingScreen(
    coffee: Int,
    iqos: Int,
    ciggies: Int,
    calories: Int,
    onClearCache: () -> Unit,
    onCoffeeIncrement: () -> Unit,
    onIqosIncrement: () -> Unit,
    onCiggiesIncrement: () -> Unit,
    onCaloriesIncrement: () -> Unit
) {
    Box( // Outer-most box
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        OutlinedButton( // Clear cache button
            onClick = onClearCache,
            shape = ZeroCornerShape,
            border = BorderStroke(ButtonBorderThickness, MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 24.dp)
                .size(48.dp)
        ) {
            Icon(
                painter = painterResource(id = android.R.drawable.ic_menu_delete),
                contentDescription = "Clear today's cache"
            )
        }

        Box( // Today's Habits title top left
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 20.dp)
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
                    modifier = Modifier.fillMaxWidth()
                )
                AutoFitDateLine(
                    text = "Habits",
                    minFontSize = 18.sp,
                    maxFontSize = 32.sp,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Column( // the habits list, with the counters
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 140.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            HabitRow("Coffee", coffee, onCoffeeIncrement)
            HabitRow("IQOS", iqos, onIqosIncrement)
            HabitRow("Ciggies", ciggies, onCiggiesIncrement)
            HabitRow("Calories", calories, onCaloriesIncrement)
        }
    }
}

// Function to dynamically add each row, without hardcoding them
@Composable
fun HabitRow(name: String, count: Int, onIncrement: () -> Unit) {
    androidx.compose.foundation.layout.Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("$name: $count", fontSize = 18.sp, fontFamily = PixelFont)
        OutlinedButton(
            onClick = onIncrement,
            shape = ZeroCornerShape,
            border = BorderStroke(ButtonBorderThickness, MaterialTheme.colorScheme.primary),
            modifier = Modifier.size(52.dp),
            contentPadding = PaddingValues(0.dp)
        ) {
            Text("+1", fontFamily = PixelFont, fontSize = 12.sp)
        }
    }
}
