package com.example.habit_cache

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.habit_cache.ui.theme.HabitcacheTheme

/**
 * MainActivity.kt
 *     Android starts here.
 *     Sets up Compose.
 *     Applies HabitcacheTheme.
 *     Calls HabitCacheApp.
 */

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // This is my UI using the Jetpack Compose
        setContent {
            // Theme wrapper
            HabitcacheTheme(darkTheme = false, dynamicColor = false) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp)
                    ) {
                        HabitCacheApp()
                    }
                }
            }
        }
    }
}
