package com.example.immunify.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = PrimaryMain,
    secondary = WarningSurface,
    tertiary = White10,
    background = White20,
    surface = PrimarySurface,
    onPrimary = White10,
    onSecondary = WarningMain,
    onTertiary = Grey70,
    onBackground = Black100,
    onSurface = PrimaryMain,
    outline = Grey30,
    error = Red,
)

@Composable
fun ImmunifyTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}