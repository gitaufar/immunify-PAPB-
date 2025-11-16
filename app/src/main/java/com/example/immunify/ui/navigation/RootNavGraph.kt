package com.example.immunify.ui.navigation

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.immunify.data.local.ClinicSamples
import com.example.immunify.ui.clinics.ClinicDetailScreen
import com.example.immunify.ui.clinics.ClinicMapScreen
import com.example.immunify.ui.insight.InsightDetail
import com.example.immunify.ui.insight.InsightScreen
import com.example.immunify.ui.onboarding.*
import com.example.immunify.ui.splash.SplashScreen
import com.example.immunify.ui.splash.AppPreferencesViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.immunify.data.local.UserSample
import com.example.immunify.data.model.AppointmentData
import com.example.immunify.ui.auth.LoginScreen
import com.example.immunify.ui.auth.RegisterScreen
import com.example.immunify.ui.clinics.AppointmentSuccessScreen
import com.example.immunify.ui.clinics.AppointmentSummaryScreen
import com.example.immunify.ui.clinics.SetAppointmentScreen
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RootNavGraph(
    navController: NavHostController = rememberNavController(),
    userLatitude: Double,
    userLongitude: Double
) {
    NavHost(
        navController = navController,
//        startDestination = Routes.SPLASH

        // untuk testing bisa ubah ke Routes.MAIN_GRAPH
        startDestination = Routes.MAIN_GRAPH
    ) {

        // SPLASH
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
                }
            )
        }

        // ONBOARDING
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
                getStarted = { navController.navigate(Routes.REGISTER) }
            )
        }

        // AUTH
        composable(Routes.REGISTER) {
            RegisterScreen(
                onRegisterSuccess = { navController.navigate(Routes.LOGIN) },
                onLoginClick = { navController.navigate(Routes.LOGIN) }
            )
        }

        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Routes.MAIN_GRAPH) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                },
                onForgotPasswordClick = {},
                onRegisterClick = { navController.navigate(Routes.REGISTER) }
            )
        }

        // MAIN GRAPH (with BottomNav)
        composable(Routes.MAIN_GRAPH) {
            MainScaffold(
                rootNavController = navController,
                onMapClick = { navController.navigate(Routes.CLINIC_MAP) },
                userLatitude = userLatitude,
                userLongitude = userLongitude
            )
        }

        // PAGES di luar BottomNav tapi masih bagian dari MAIN_GRAPH
        composable(Routes.CLINIC_MAP) {
            ClinicMapScreen(
                userLatitude = userLatitude,
                userLongitude = userLongitude,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(
            route = Routes.CLINIC_DETAIL,
            arguments = listOf(navArgument("clinicId") { type = NavType.StringType })
        ) { backStackEntry ->
            val clinicId = backStackEntry.arguments?.getString("clinicId") ?: ""
            val clinic = ClinicSamples.find { it.id == clinicId }

            clinic?.let {
                ClinicDetailScreen(
                    rootNav = navController,
                    clinic = it,
                    userLatitude = userLatitude,
                    userLongitude = userLongitude,
                    onBackClick = { navController.popBackStack() }
                )
            }
        }

        composable(
            route = "${Routes.SET_APPOINTMENT}/{clinicId}",
            arguments = listOf(navArgument("clinicId") { type = NavType.StringType })
        ) { backStackEntry ->
            val clinicId = backStackEntry.arguments?.getString("clinicId") ?: ""
            val clinic = ClinicSamples.find { it.id == clinicId } ?: return@composable

            SetAppointmentScreen(
                user = UserSample,
                clinic = clinic,
                selectedDate = LocalDate.now(),
                onBackClick = { navController.popBackStack() },
                onContinueClick = { appointment ->
                    val appointmentJson = Uri.encode(Json.encodeToString(appointment))
                    navController.navigate(Routes.appointmentSummaryRoute(appointmentJson))
                }
            )
        }

        composable(
            route = "${Routes.APPOINTMENT_SUMMARY}/{appointment}",
            arguments = listOf(navArgument("appointment") { type = NavType.StringType })
        ) { entry ->
            val appointmentJson = entry.arguments?.getString("appointment") ?: ""
            val appointment = Json.decodeFromString<AppointmentData>(appointmentJson)

            AppointmentSummaryScreen(
                appointment = appointment,
                onBackClick = { navController.popBackStack() },
                onConfirmClick = {
                    navController.navigate(Routes.APPOINTMENT_SUCCESS)
                }
            )
        }

        composable(Routes.APPOINTMENT_SUCCESS) {
            AppointmentSuccessScreen(
                onBackToHome = {
                    navController.navigate(Routes.MAIN_GRAPH) {
                        popUpTo(Routes.MAIN_GRAPH) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        // INSIGHT
        composable(Routes.INSIGHTS) {
            InsightScreen()
        }

        composable(
            route = Routes.INSIGHT_DETAIL,
            arguments = listOf(navArgument("insightId") { type = NavType.StringType })
        ) { backStackEntry ->
            val insightId = backStackEntry.arguments?.getString("insightId") ?: ""
            InsightDetail(navController = navController, insightId = insightId)
        }
    }
}
