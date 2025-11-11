package com.example.immunify.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.immunify.ui.clinics.ClinicsScreen
import com.example.immunify.ui.home.HomeScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScaffold(
    rootNavController: NavHostController,
    onMapClick: () -> Unit = {},
    userLatitude: Double,
    userLongitude: Double
) {
    val bottomNavController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavBar(bottomNavController) }
    ) { padding ->
        NavHost(
            navController = bottomNavController,
            startDestination = Routes.HOME,
            modifier = Modifier.padding(padding)
        ) {
            composable(Routes.HOME) {
                HomeScreen(
                    navController = rootNavController,
                    userLatitude = userLatitude,
                    userLongitude = userLongitude
                )
            }

            composable(Routes.CLINICS) {
                ClinicsScreen(
                    navController = rootNavController,
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

