package com.example.habit_cache.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

/**
 * Configuration of my custom theme.
 * Change ACTIVE_PALETTE to try a different pixel-art palette.
 */

private enum class PixelPalette {
    MINT_TERMINAL,
    SUNSET_AMBER,
    OCEAN_NEON,
    IRONY_CRAW
}

private val ACTIVE_PALETTE = PixelPalette.IRONY_CRAW

private val MintTerminalDark = darkColorScheme(
    primary = Color(0xFF64FF9C),
    onPrimary = Color(0xFF032513),
    secondary = Color(0xFF54D8BC),
    onSecondary = Color(0xFF02231D),
    tertiary = Color(0xFFFFC36A),
    onTertiary = Color(0xFF2A1600),
    background = Color(0xFF0D1411),
    onBackground = Color(0xFFE7F6ED),
    surface = Color(0xFF131C18),
    onSurface = Color(0xFFE7F6ED),
    outline = Color(0xFF3E6B56)
)

private val MintTerminalLight = lightColorScheme(
    primary = Color(0xFF0E8D4D),
    onPrimary = Color(0xFFFFFFFF),
    secondary = Color(0xFF0F8A74),
    onSecondary = Color(0xFFFFFFFF),
    tertiary = Color(0xFF8B5A00),
    onTertiary = Color(0xFFFFFFFF),
    background = Color(0xFFF1FBF6),
    onBackground = Color(0xFF122019),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF122019),
    outline = Color(0xFF58826E)
)

private val SunsetAmberDark = darkColorScheme(
    primary = Color(0xFFFFB347),
    onPrimary = Color(0xFF261400),
    secondary = Color(0xFFFF7A66),
    onSecondary = Color(0xFF2A0A04),
    tertiary = Color(0xFFFFE27A),
    onTertiary = Color(0xFF2B2400),
    background = Color(0xFF1A120D),
    onBackground = Color(0xFFFFF0E3),
    surface = Color(0xFF241913),
    onSurface = Color(0xFFFFF0E3),
    outline = Color(0xFF84604A)
)

private val SunsetAmberLight = lightColorScheme(
    primary = Color(0xFFB75C00),
    onPrimary = Color(0xFFFFFFFF),
    secondary = Color(0xFFB84433),
    onSecondary = Color(0xFFFFFFFF),
    tertiary = Color(0xFF8D6C00),
    onTertiary = Color(0xFFFFFFFF),
    background = Color(0xFFFFF7F2),
    onBackground = Color(0xFF281A12),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF281A12),
    outline = Color(0xFF9A725A)
)

private val OceanNeonDark = darkColorScheme(
    primary = Color(0xFF6BB5FF),
    onPrimary = Color(0xFF001A33),
    secondary = Color(0xFF7AF5FF),
    onSecondary = Color(0xFF002529),
    tertiary = Color(0xFFB78CFF),
    onTertiary = Color(0xFF23053A),
    background = Color(0xFF0D111A),
    onBackground = Color(0xFFE8EEFF),
    surface = Color(0xFF141A26),
    onSurface = Color(0xFFE8EEFF),
    outline = Color(0xFF485D85)
)

private val OceanNeonLight = lightColorScheme(
    primary = Color(0xFF0066CC),
    onPrimary = Color(0xFFFFFFFF),
    secondary = Color(0xFF007C8A),
    onSecondary = Color(0xFFFFFFFF),
    tertiary = Color(0xFF6F39C8),
    onTertiary = Color(0xFFFFFFFF),
    background = Color(0xFFF4F7FF),
    onBackground = Color(0xFF131A28),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF131A28),
    outline = Color(0xFF6679A3)
)

private val IronyCrawDark = darkColorScheme(
    primary = IronyMint,
    onPrimary = Color(0xFF062922),
    secondary = IronyRed,
    onSecondary = Color(0xFF2B0606),
    tertiary = IronyOchre,
    onTertiary = Color(0xFF2B1E07),
    background = IronyNavy,
    onBackground = IronySand,
    surface = IronySlateBlue,
    onSurface = Color(0xFFF5E8C3),
    outline = IronyTeal
)

private val IronyCrawLight = lightColorScheme(
    primary = IronyTeal,
    onPrimary = Color(0xFFFFFFFF),
    secondary = IronyBurntOrange,
    onSecondary = Color(0xFFFFFFFF),
    tertiary = IronyRed,
    onTertiary = Color(0xFFFFFFFF),
    background = IronySand,
    onBackground = IronyNavy,
    surface = IronyOchre,
    onSurface = IronyNavy,
    outline = IronySlateBlue
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
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        ACTIVE_PALETTE == PixelPalette.MINT_TERMINAL && darkTheme -> MintTerminalDark
        ACTIVE_PALETTE == PixelPalette.MINT_TERMINAL && !darkTheme -> MintTerminalLight

        ACTIVE_PALETTE == PixelPalette.SUNSET_AMBER && darkTheme -> SunsetAmberDark
        ACTIVE_PALETTE == PixelPalette.SUNSET_AMBER && !darkTheme -> SunsetAmberLight

        ACTIVE_PALETTE == PixelPalette.OCEAN_NEON && darkTheme -> OceanNeonDark
        ACTIVE_PALETTE == PixelPalette.OCEAN_NEON && !darkTheme -> OceanNeonLight

        ACTIVE_PALETTE == PixelPalette.IRONY_CRAW && darkTheme -> IronyCrawDark
        else -> IronyCrawLight
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = PixelShapes,
        content = content
    )
}
