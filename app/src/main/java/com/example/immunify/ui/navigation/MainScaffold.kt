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
import com.example.immunify.core.LocalAppState
import com.example.immunify.data.local.ChildSamples
import com.example.immunify.data.local.VaccineSamples
import com.example.immunify.ui.clinics.ClinicsScreen
import com.example.immunify.ui.home.HomeScreen
import com.example.immunify.ui.profile.ProfileScreen
import com.example.immunify.ui.tracker.TrackerScreen
import java.time.YearMonth

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScaffold(
    rootNavController: NavHostController,
    onMapClick: () -> Unit = {}
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

            // HOME
            composable(Routes.HOME) {
                HomeScreen(
                    rootNav = rootNavController,
                    bottomNav = bottomNavController
                )
            }

            // CLINICS
            composable(Routes.CLINICS) {
                ClinicsScreen(
                    navController = rootNavController,
                    onMapClick = onMapClick
                )
            }

            //TRACKER
            composable(Routes.TRACKER) {
                TrackerScreen(
                    yearMonth = YearMonth.now()
                )
            }

            // PROFILE
            composable(Routes.PROFILE) {
                ProfileScreen()
            }
        }
    }
}
