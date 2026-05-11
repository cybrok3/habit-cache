package com.example.habit_cache

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.width
import androidx.compose.ui.res.painterResource

/**
 * This file directs the UI elements of the Menu screen of the app
 */

// UI Composable function for the menu screen
@Composable
fun MenuScreen(
    currentDate: String,
    startTrackingLabel: String, // The text of the Start tracking button, either "Start"" or "Resume"
    onStartTracking: () -> Unit // Callback function, the UI calls it when the 'Start' button is pressed, this function does not know what it calls
) {
    val displayDate = remember(currentDate) { displayDateFromKey(currentDate) } // Format the date and display it, remember it until currentDate changes

    Box( // the outer-most container
        modifier = Modifier
            .fillMaxSize() // Fill the whole screen
            .padding(24.dp) // General padding of the outer container
    ) {
        Box( // Date area box
            modifier = Modifier
                .align(Alignment.TopStart) // Top left
                .padding(top = 20.dp) // Little padding on the top
                .width(176.dp)
                .height(86.dp)
        ) {
            Column( // Date Column for vertical placement of the children elements
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(3.dp),
                horizontalAlignment = Alignment.Start
            ) {
                AutoFitDateLine( // Top date line
                    text = displayDate.topLine,
                    minFontSize = 11.sp,
                    maxFontSize = 17.sp,
                    modifier = Modifier.fillMaxWidth()
                )
                AutoFitDateLine( // Bot date line
                    text = displayDate.bottomLine,
                    minFontSize = 18.sp,
                    maxFontSize = 32.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Column( // Stack vertically the App Title text and the buttons
            modifier = Modifier.align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text( // App title
                text = "Habit\nCache",
                fontSize = 32.sp,
                fontFamily = PixelFont,
                lineHeight = 42.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(28.dp)) // A little space below the Title and above the column of the buttons
            Column( // For the buttons stack
                modifier = Modifier.fillMaxWidth(0.82f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton( // Start button look and functionality
                    onClick = onStartTracking,
                    shape = ZeroCornerShape,
                    border = BorderStroke(ButtonBorderThickness, MaterialTheme.colorScheme.primary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                ) {
                    Box(modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center) {
                        Image(
                            painter = painterResource(id = R.drawable.start_icon),
                            contentDescription = "Options icon",
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding (start = 20.dp)
                                .size(32.dp)
                        )
                        Spacer(modifier = Modifier.padding(10.dp))
                        Text(
                            text = startTrackingLabel,
                            fontFamily = PixelFont,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                OutlinedButton( // Options Button looks
                    onClick = { },
                    shape = ZeroCornerShape,
                    contentPadding = PaddingValues(0.dp),
                    border = BorderStroke(ButtonBorderThickness, MaterialTheme.colorScheme.primary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.settings_icon),
                            contentDescription = "Options icon",
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(start = 26.dp)
                                .size(32.dp)
                        )
                        Text(
                            text = "Options",
                            fontFamily = PixelFont,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}
