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
import androidx.compose.foundation.layout.width
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

@Composable
fun MenuScreen(
    currentDate: String,
    startTrackingLabel: String,
    onStartTracking: () -> Unit
) {
    val displayDate = remember(currentDate) { displayDateFromKey(currentDate) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Box(
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
                    text = displayDate.topLine,
                    minFontSize = 11.sp,
                    maxFontSize = 17.sp,
                    modifier = Modifier.fillMaxWidth()
                )
                AutoFitDateLine(
                    text = displayDate.bottomLine,
                    minFontSize = 18.sp,
                    maxFontSize = 32.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Column(
            modifier = Modifier.align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Habit\nCache",
                fontSize = 32.sp,
                fontFamily = PixelFont,
                lineHeight = 42.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(28.dp))
            Column(
                modifier = Modifier.fillMaxWidth(0.82f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onStartTracking,
                    shape = ZeroCornerShape,
                    border = BorderStroke(ButtonBorderThickness, MaterialTheme.colorScheme.primary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                ) {
                    AutoFitDateLine(
                        text = startTrackingLabel,
                        minFontSize = 11.sp,
                        maxFontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                OutlinedButton(
                    onClick = { },
                    shape = ZeroCornerShape,
                    border = BorderStroke(ButtonBorderThickness, MaterialTheme.colorScheme.primary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                ) {
                    AutoFitDateLine(
                        text = "Options",
                        minFontSize = 11.sp,
                        maxFontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}
