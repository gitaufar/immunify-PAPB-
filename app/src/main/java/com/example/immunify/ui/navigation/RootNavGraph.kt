package com.example.immunify.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.immunify.data.local.ClinicSamples
import com.example.immunify.ui.auth.*
import com.example.immunify.ui.clinics.*
import com.example.immunify.ui.insight.*
import com.example.immunify.ui.onboarding.*
import com.example.immunify.ui.splash.AppPreferencesViewModel
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
            val prefsViewModel: AppPreferencesViewModel = hiltViewModel()

            SplashScreen(
                onFinished = {
                    val firstTime = prefsViewModel.isFirstTime.value

                    if (firstTime) {
                        prefsViewModel.setNotFirstTime()
                        navController.navigate(Routes.ONBOARDING1) {
                            popUpTo(Routes.SPLASH) { inclusive = true }
                        }
                    } else {
                        navController.navigate(Routes.LOGIN) {
                            popUpTo(Routes.SPLASH) { inclusive = true }
                        }
                    }
                })
        }

        composable(Routes.ONBOARDING1) {
            Onboarding1Screen(
                onNext = { navController.navigate(Routes.ONBOARDING2) },
                onSkip = { navController.navigate(Routes.REGISTER) })
        }

        composable(Routes.ONBOARDING2) {
            Onboarding2Screen(
                onNext = { navController.navigate(Routes.ONBOARDING3) },
                onSkip = { navController.navigate(Routes.REGISTER) })
        }

        composable(Routes.ONBOARDING3) {
            Onboarding3Screen(
                getStarted = {
                    navController.navigate(Routes.REGISTER)
                })
        }

        composable(Routes.REGISTER) {
            RegisterScreen(
                onRegisterSuccess = { navController.navigate(Routes.LOGIN) },
                onLoginClick = { navController.navigate(Routes.LOGIN) })
        }

        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = { navController.navigate(Routes.MAIN_GRAPH) },
                onForgotPasswordClick = {},
                onRegisterClick = { navController.navigate(Routes.REGISTER) })
        }

        composable(Routes.MAIN_GRAPH) {
            MainScaffold(
                rootNavController = navController,
                onMapClick = { navController.navigate(Routes.CLINIC_MAP) },
                userLatitude = userLatitude,
                userLongitude = userLongitude
            )
        }


        composable(Routes.CLINICS) {
            ClinicsScreen(
                navController = navController,
                userLatitude = userLatitude,
                userLongitude = userLongitude,
                onMapClick = { navController.navigate(Routes.CLINIC_MAP) })
        }

        composable(Routes.CLINIC_MAP) {
            ClinicMapScreen(
                userLatitude = userLatitude,
                userLongitude = userLongitude,
                onBackClick = { navController.popBackStack() })
        }

        composable(
            route = Routes.CLINIC_DETAIL,
            arguments = listOf(navArgument("clinicId") { type = NavType.StringType })
        ) { backStackEntry ->
            val clinicId = backStackEntry.arguments?.getString("clinicId") ?: ""
            val clinic = ClinicSamples.find { it.id == clinicId }
            clinic?.let {
                ClinicDetailScreen(
                    clinic = it,
                    userLatitude = userLatitude,
                    userLongitude = userLongitude,
                    onBackClick = { navController.popBackStack() }
                )
            }
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
