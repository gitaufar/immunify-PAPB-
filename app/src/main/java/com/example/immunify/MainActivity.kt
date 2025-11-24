package com.example.immunify

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.CompositionLocalProvider
import com.example.immunify.core.AppState
import com.example.immunify.core.LocalAppState
import com.example.immunify.ui.navigation.RootNavGraph
import com.example.immunify.ui.theme.ImmunifyTheme
import com.example.immunify.ui.viewmodel.LocationViewModel
import com.example.immunify.data.model.LocationState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val locationViewModel: LocationViewModel by viewModels()

    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                locationViewModel.loadUserLocation()
            }
        }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)

        setContent {
            ImmunifyTheme {

                val appState = LocalAppState.current
                val loc = locationViewModel.locationState.value

                LaunchedEffect(loc) {
                    if (loc is LocationState.Success) {
                        appState.userLatitude = loc.location.latitude
                        appState.userLongitude = loc.location.longitude
                    }
                }

                RootNavGraph()
            }
        }
    }
}
