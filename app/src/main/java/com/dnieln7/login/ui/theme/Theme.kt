package com.dnieln7.login.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = Green300,
    primaryVariant = Green700,
    secondary = Pink400,
    secondaryVariant = Green700
)

private val LightColorPalette = lightColors(
    primary = Green500,
    primaryVariant = Green700,
    secondary = Pink400,
    secondaryVariant = Green700,
)

@Composable
fun LoginTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}