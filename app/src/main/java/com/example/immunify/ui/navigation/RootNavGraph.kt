package com.example.immunify.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.immunify.ui.Onboarding1Screen
import com.example.immunify.ui.Onboarding2Screen
import com.example.immunify.ui.Onboarding3Screen
import com.example.immunify.ui.SplashScreen

@Composable
fun RootNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH
    ) {
        // Splash screen
        composable(Routes.SPLASH) {
            SplashScreen(
                onFinished = {
                    navController.navigate(Routes.ONBOARDING1) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        // Onboarding 1
        composable(Routes.ONBOARDING1) {
            Onboarding1Screen(
                onNext = {
                    navController.navigate(Routes.ONBOARDING2) {
                        popUpTo(Routes.ONBOARDING1) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onSkip = {
                    navController.navigate(Routes.REGISTER) {
                        popUpTo(Routes.ONBOARDING1) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        // Onboarding 2
        composable(Routes.ONBOARDING2) {
            Onboarding2Screen(
                onNext = {
                    navController.navigate(Routes.ONBOARDING3) {
                        popUpTo(Routes.ONBOARDING2) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onSkip = {
                    navController.navigate(Routes.REGISTER) {
                        popUpTo(Routes.ONBOARDING2) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        // Onboarding 3
        composable(Routes.ONBOARDING3) {
            Onboarding3Screen(
                getStarted = {
                    navController.navigate(Routes.REGISTER) {
                        popUpTo(Routes.ONBOARDING3) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        // Main application (setelah onboarding)
        composable(Routes.MAIN_GRAPH) {
            MainScaffold()
        }
    }
}
