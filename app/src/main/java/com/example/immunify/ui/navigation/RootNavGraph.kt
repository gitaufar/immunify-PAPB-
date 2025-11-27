package com.example.immunify.ui.navigation

import Routes
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.immunify.data.local.ClinicSamples
import com.example.immunify.ui.clinics.ClinicDetailScreen
import com.example.immunify.ui.clinics.ClinicMapScreen
import com.example.immunify.ui.onboarding.*
import com.example.immunify.ui.splash.SplashScreen
import androidx.navigation.NavHostController
import com.example.immunify.core.LocalAppState
import com.example.immunify.data.local.DiseaseSamples
import com.example.immunify.data.local.InsightSamples
import com.example.immunify.data.model.AppointmentData
import com.example.immunify.ui.auth.AuthViewModel
import com.example.immunify.ui.auth.LoginScreen
import com.example.immunify.ui.auth.RegisterScreen
import com.example.immunify.ui.clinics.AppointmentSuccessScreen
import com.example.immunify.ui.clinics.AppointmentSummaryScreen
import com.example.immunify.ui.clinics.SetAppointmentScreen
import com.example.immunify.ui.home.NotificationScreen
import com.example.immunify.ui.insight.DiseaseDetailScreen
import com.example.immunify.ui.insight.InsightDetailScreen
import com.example.immunify.ui.insight.InsightScreen
import com.example.immunify.ui.splash.AppPreferencesViewModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RootNavGraph(
    navController: NavHostController = rememberNavController(),
) {
    val appState = LocalAppState.current

    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH
    ) {
        // SPLASH
        composable(Routes.SPLASH) {

            val prefsViewModel: AppPreferencesViewModel = hiltViewModel()
            val authViewModel: AuthViewModel = hiltViewModel()

            val user by authViewModel.user.collectAsState()
            val isFirstTime by prefsViewModel.isFirstTime.collectAsState()
            val isLoading by authViewModel.isLoading.collectAsState()

            LaunchedEffect(Unit) {
                // Pastikan SplashScreen tampil dulu
                kotlinx.coroutines.delay(1500)
            }

            // Wait until loading is done before routing
            LaunchedEffect(isLoading) {
                if (!isLoading) {
                    android.util.Log.d("SplashScreen", "User: $user, isFirstTime: $isFirstTime")

                    if (user != null) {
                        // User sudah login → ke main graph (home with bottom nav)
                        android.util.Log.d(
                            "SplashScreen",
                            "User logged in, navigating to MAIN_GRAPH"
                        )
                        navController.navigate(Routes.MAIN_GRAPH) {
                            popUpTo(Routes.SPLASH) { inclusive = true }
                        }
                    } else {
                        // User belum login → cek onboarding
                        if (isFirstTime) {
                            android.util.Log.d(
                                "SplashScreen",
                                "First time user, navigating to ONBOARDING"
                            )
                            prefsViewModel.setNotFirstTime()
                            navController.navigate(Routes.ONBOARDING1) {
                                popUpTo(Routes.SPLASH) { inclusive = true }
                            }
                        } else {
                            android.util.Log.d(
                                "SplashScreen",
                                "Returning user not logged in, navigating to LOGIN"
                            )
                            navController.navigate(Routes.LOGIN) {
                                popUpTo(Routes.SPLASH) { inclusive = true }
                            }
                        }
                    }
                }
            }

            SplashScreen(
                onFinished = {}
            )
        }

        // ini buat testing supaya langsung ke home
//        composable(Routes.SPLASH) {
//            SplashScreen(
//                onFinished = {
//                    navController.navigate(Routes.MAIN_GRAPH) {
//                        popUpTo(Routes.SPLASH) { inclusive = true }
//                    }
//                }
//            )
//        }

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

        // MAIN GRAPH SCREEN (BOTTOM NAV HOST)
        composable(Routes.MAIN_GRAPH) {
            MainScaffold(
                rootNavController = navController,
                onMapClick = { navController.navigate(Routes.CLINIC_MAP) },
            )
        }

        // CLINIC MAP SCREEN
        composable(
            route = "${Routes.CLINIC_MAP}?focusId={focusId}",
            arguments = listOf(navArgument("focusId") { nullable = true })
        ) { backStackEntry ->
            val focusId = backStackEntry.arguments?.getString("focusId")
            ClinicMapScreen(
                navController = navController,
                clinics = ClinicSamples,
                focusId = focusId,
                onBackClick = { navController.popBackStack() }
            )
        }

        // CLINIC DETAIL SCREEN
        composable(Routes.CLINIC_DETAIL) { backStackEntry ->
            val clinicId = backStackEntry.arguments?.getString("clinicId") ?: ""
            val clinic = ClinicSamples.find { it.id == clinicId }

            clinic?.let {
                ClinicDetailScreen(
                    rootNav = navController,
                    clinic = clinic,
                    onBackClick = { navController.popBackStack() }
                )
            }
        }

        composable(
            route = "${Routes.SET_APPOINTMENT}/{clinicId}?vaccineId={vaccineId}",
            arguments = listOf(
                navArgument("clinicId") { type = NavType.StringType },
                navArgument("vaccineId") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) { backStackEntry ->
            val authViewModel: com.example.immunify.ui.auth.AuthViewModel = hiltViewModel()
            val currentUser by authViewModel.user.collectAsState()

            val clinicId = backStackEntry.arguments?.getString("clinicId") ?: ""
            val vaccineId = backStackEntry.arguments?.getString("vaccineId") ?: ""

            val clinic = ClinicSamples.find { it.id == clinicId } ?: return@composable
            val preselectedVaccine = clinic.availableVaccines.find { it.id == vaccineId }

            // Create UserData dengan Firebase UID
            // Children will be loaded inside SetAppointmentScreen via ChildViewModel
            val user = currentUser?.let {
                com.example.immunify.data.model.UserData(
                    id = it.id,
                    name = it.name,
                    email = it.email,
                    password = "",
                    phoneNumber = it.phoneNumber ?: "",
                    children = emptyList()
                )
            } ?: return@composable

            SetAppointmentScreen(
                user = user,
                clinic = clinic,
                selectedDate = LocalDate.now(),
                preselectedVaccine = preselectedVaccine,
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

        composable(Routes.INSIGHTS) {
            InsightScreen(
                rootNav = navController,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(
            route = Routes.INSIGHT_DETAIL,
            arguments = listOf(navArgument("insightId") { type = NavType.StringType })
        ) { backStackEntry ->
            val insightId = backStackEntry.arguments?.getString("insightId") ?: ""
            val insight = InsightSamples.find { it.id == insightId }
            if (insight != null) {
                InsightDetailScreen(
                    rootNav = navController,
                    insight = insight,
                    onBackClick = { navController.popBackStack() }
                )
            }
        }

        composable(
            route = Routes.DISEASE_DETAIL,
            arguments = listOf(navArgument("diseaseId") { type = NavType.StringType })
        ) { backStackEntry ->
            val diseaseId = backStackEntry.arguments?.getString("diseaseId") ?: ""
            val disease = DiseaseSamples.find { it.id == diseaseId }

            if (disease != null) {
                DiseaseDetailScreen(
                    rootNav = navController,
                    disease = disease,
                    onBackClick = { navController.popBackStack() }
                )
            }
        }

        composable(Routes.NOTIFICATION) {
            NotificationScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
