package com.example.immunify

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.immunify.data.model.LocationState
import com.example.immunify.ui.navigation.RootNavGraph
import com.example.immunify.ui.theme.ImmunifyTheme
import com.example.immunify.ui.theme.White10
import com.example.immunify.ui.viewmodel.LocationViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ImmunifyTheme {
                Surface(color = White10) {
                    val context = LocalContext.current
                    val locationViewModel: LocationViewModel = viewModel()
                    val fusedLocationClient: FusedLocationProviderClient =
                        remember { LocationServices.getFusedLocationProviderClient(context) }

                    // launcher izin lokasi
                    val permissionLauncher = rememberLauncherForActivityResult(
                        ActivityResultContracts.RequestPermission()
                    ) { isGranted ->
                        if (isGranted) {
                            locationViewModel.fetchUserLocation(fusedLocationClient)
                        }
                    }

                    // cek & minta izin lokasi saat app mulai
                    LaunchedEffect(Unit) {
                        val permission = Manifest.permission.ACCESS_FINE_LOCATION
                        when {
                            ContextCompat.checkSelfPermission(context, permission) ==
                                    PackageManager.PERMISSION_GRANTED -> {
                                locationViewModel.fetchUserLocation(fusedLocationClient)
                            }

                            else -> permissionLauncher.launch(permission)
                        }
                    }

                    val locationState = locationViewModel.locationState.value

                    val (userLatitude, userLongitude) = when (locationState) {
                        is LocationState.Success -> Pair(
                            locationState.location.latitude,
                            locationState.location.longitude
                        )

                        else -> Pair(-7.9770, 112.6339) // default: Malang
                    }

                    // kirim lokasi ke navigasi utama
                    RootNavGraph(
                        userLatitude = userLatitude,
                        userLongitude = userLongitude
                    )
                }
            }
        }
    }
}
