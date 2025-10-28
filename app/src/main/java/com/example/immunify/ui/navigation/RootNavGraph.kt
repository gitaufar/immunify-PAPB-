package com.example.immunify.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.immunify.ui.auth.LoginScreen
import com.example.immunify.ui.onboarding.Onboarding1Screen
import com.example.immunify.ui.onboarding.Onboarding2Screen
import com.example.immunify.ui.onboarding.Onboarding3Screen
import com.example.immunify.ui.auth.RegisterScreen
import com.example.immunify.ui.splash.SplashScreen
import com.example.immunify.ui.insight.InsightDetail
import com.example.immunify.ui.insight.InsightScreen

@Composable
fun RootNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH
    ) {
        // Splash Screen
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

        // Register Screen
        composable(Routes.REGISTER) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.REGISTER) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onLoginClick = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.REGISTER) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        // Login Screen
        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Routes.MAIN_GRAPH) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onRegisterClick = {
                    navController.navigate(Routes.REGISTER) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        // Main Application (setelah login)
        composable(Routes.MAIN_GRAPH) {
            MainScaffold()
        }

        composable(Routes.INSIGHTS) {
            InsightScreen()
        }

        composable(
            route = Routes.INSIGHT_DETAIL,
            arguments = listOf(
                navArgument("insightId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val insightId = backStackEntry.arguments?.getString("insightId")
            InsightDetail(
                navController = navController,
                insightId = insightId ?: ""
            )
        }


    }
}
