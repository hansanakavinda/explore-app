package com.example.explore.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val LightColors = lightColorScheme(
    primary = BrandBlue,
    onPrimary = Color.White,
    primaryContainer = Color(0xFF2570D4),
    onPrimaryContainer = Color.White,
    background = Color.White,
    onBackground = Ink900,
    surface = Color.White,
    onSurface = Ink900,
    surfaceVariant = Blue50,
    onSurfaceVariant = Grey600,
    outlineVariant = BlueBorder,
    error = Red600
)

private val DarkColors = darkColorScheme(
    primary = Blue300,
    onPrimary = Night900,
    primaryContainer = DeepBlue,
    onPrimaryContainer = Color.White,
    background = Night900,
    onBackground = Color.White,
    surface = Night800,
    onSurface = Color.White,
    surfaceVariant = Night700,
    onSurfaceVariant = Grey300,
    outlineVariant = NightBorder,
    error = Red300
)

private val ExploreShapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(24.dp)
)

@Composable
fun ExploreTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    @Suppress("UNUSED_PARAMETER") dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = Typography,
        shapes = ExploreShapes,
        content = content
    )
}