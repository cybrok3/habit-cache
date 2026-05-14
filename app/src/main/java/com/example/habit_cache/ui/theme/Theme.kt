package com.example.habit_cache.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * App theme configuration.
 * Uses only the Irony Craw light palette.
 */

private val IronyCrawLight = lightColorScheme(
    primary = HabitCacheColors.IronyTeal,
    onPrimary = Color(0xFFFFFFFF),
    secondary = HabitCacheColors.IronyBurntOrange,
    onSecondary = Color(0xFFFFFFFF),
    tertiary = HabitCacheColors.IronyRed,
    onTertiary = Color(0xFFFFFFFF),
    background = HabitCacheColors.IronySand,
    onBackground = HabitCacheColors.IronyNavy,
    surface = HabitCacheColors.IronyOchre,
    onSurface = HabitCacheColors.IronyNavy,
    outline = HabitCacheColors.IronySlateBlue
)

private val PixelShapes = Shapes(
    extraSmall = RoundedCornerShape(0.dp),
    small = RoundedCornerShape(0.dp),
    medium = RoundedCornerShape(0.dp),
    large = RoundedCornerShape(0.dp),
    extraLarge = RoundedCornerShape(0.dp)
)

@Composable
fun HabitcacheTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = IronyCrawLight,
        typography = Typography,
        shapes = PixelShapes,
        content = content
    )
}
