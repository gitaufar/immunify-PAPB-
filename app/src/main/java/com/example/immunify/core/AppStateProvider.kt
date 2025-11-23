package com.example.immunify.core

import androidx.compose.runtime.staticCompositionLocalOf

data class AppState(
    var userLatitude: Double = 0.0,
    var userLongitude: Double = 0.0,
    var isLocationReady: Boolean = false
)

val LocalAppState = staticCompositionLocalOf { AppState() }
