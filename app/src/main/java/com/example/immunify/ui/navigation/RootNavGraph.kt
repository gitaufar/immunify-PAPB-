package com.example.immunify.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.immunify.ui.auth.*
import com.example.immunify.ui.clinics.*
import com.example.immunify.ui.insight.*
import com.example.immunify.ui.onboarding.*
import com.example.immunify.ui.splash.SplashScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RootNavGraph(
    navController: NavHostController = rememberNavController(),
    userLatitude: Double,
    userLongitude: Double
) {
    NavHost(
        navController = navController,
        // buat deploy
//        startDestination = Routes.SPLASH
        // buat testing
        startDestination = Routes.MAIN_GRAPH
    ) {
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

        composable(Routes.ONBOARDING1) {
            Onboarding1Screen(
                onNext = { navController.navigate(Routes.ONBOARDING2) },
                onSkip = { navController.navigate(Routes.REGISTER) }
            )
        }

        composable(Routes.ONBOARDING2) {
            Onboarding2Screen(
                onNext = { navController.navigate(Routes.ONBOARDING3) },
                onSkip = { navController.navigate(Routes.REGISTER) }
            )
        }

        composable(Routes.ONBOARDING3) {
            Onboarding3Screen(
                getStarted = {
                    navController.navigate(Routes.REGISTER)
                }
            )
        }

        composable(Routes.REGISTER) {
            RegisterScreen(
                onRegisterSuccess = { navController.navigate(Routes.LOGIN) },
                onLoginClick = { navController.navigate(Routes.LOGIN) }
            )
        }

        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = { navController.navigate(Routes.MAIN_GRAPH) },
                onForgotPasswordClick = {},
                onRegisterClick = { navController.navigate(Routes.REGISTER) }
            )
        }

        composable(Routes.MAIN_GRAPH) {
            MainScaffold(
                onMapClick = { navController.navigate(Routes.CLINIC_MAP) },
                userLatitude = userLatitude,
                userLongitude = userLongitude
            )
        }

        composable(Routes.CLINICS) {
            ClinicsScreen(
                userLatitude = userLatitude,
                userLongitude = userLongitude,
                onMapClick = { navController.navigate(Routes.CLINIC_MAP) }
            )
        }

        composable(Routes.CLINIC_MAP) {
            ClinicMapScreen(
                userLatitude = userLatitude,
                userLongitude = userLongitude,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Routes.INSIGHTS) {
            InsightScreen()
        }

        composable(
            route = Routes.INSIGHT_DETAIL,
            arguments = listOf(navArgument("insightId") { type = NavType.StringType })
        ) { backStackEntry ->
            val insightId = backStackEntry.arguments?.getString("insightId")
            InsightDetail(navController = navController, insightId = insightId ?: "")
        }
    }
}
