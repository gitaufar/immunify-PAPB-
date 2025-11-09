package com.example.immunify.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.immunify.ui.clinics.ClinicsScreen
import com.example.immunify.ui.home.HomeScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScaffold(
    onMapClick: () -> Unit = {},
    userLatitude: Double,
    userLongitude: Double
) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavBar(navController) }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Routes.HOME,
            modifier = Modifier.padding(padding)
        ) {
            composable(Routes.HOME) {
                HomeScreen(
                    navController = navController,
                    userLatitude = userLatitude,
                    userLongitude = userLongitude
                )
            }

            composable(Routes.CLINICS) {
                ClinicsScreen(
                    userLatitude = userLatitude,
                    userLongitude = userLongitude,
                    onMapClick = onMapClick
                )
            }

            composable(Routes.TRACKER) { /* TrackerScreen() */ }
            composable(Routes.PROFILE) { /* ProfileScreen() */ }
        }
    }
}
