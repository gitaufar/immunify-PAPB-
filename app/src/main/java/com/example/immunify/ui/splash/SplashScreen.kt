package com.example.immunify.ui.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.immunify.data.model.LocationState
import com.example.immunify.ui.viewmodel.LocationViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    locationViewModel: LocationViewModel = hiltViewModel(),
    onFinished: () -> Unit
) {
    val locState = locationViewModel.locationState

    LaunchedEffect(Unit) {
        locationViewModel.loadUserLocation()
    }

    LaunchedEffect(locState.value) {
        if (locState.value is LocationState.Success) {
            delay(1200)
            onFinished()
        }
    }

    SplashContent()
}
