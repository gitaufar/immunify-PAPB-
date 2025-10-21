package com.example.immunify.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*

@Composable
fun MainScaffold() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavBar(navController) }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Routes.HOME,
            modifier = Modifier.padding(padding)
        ) {
            composable(Routes.HOME) { /* HomeScreen() */ }
            composable(Routes.CLINICS) { /* ClinicsScreen() */ }
            composable(Routes.TRACKER) { /* TrackerScreen() */ }
            composable(Routes.PROFILE) { /* ProfileScreen() */ }
        }
    }
}