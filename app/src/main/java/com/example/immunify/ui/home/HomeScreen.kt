package com.example.immunify.ui.home


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.immunify.R
import com.example.immunify.core.LocalAppState
import com.example.immunify.data.local.ClinicSamples
import com.example.immunify.data.local.DiseaseSamples
import com.example.immunify.data.local.VaccineSamples
import com.example.immunify.data.model.ClinicData
import com.example.immunify.data.model.LocationState
import com.example.immunify.ui.auth.AuthViewModel
import com.example.immunify.ui.clinics.viewmodel.AppointmentUiState
import com.example.immunify.ui.clinics.viewmodel.AppointmentViewModel
import com.example.immunify.ui.component.*
import com.example.immunify.ui.theme.*
import com.example.immunify.ui.viewmodel.LocationViewModel
import java.time.LocalDate
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    rootNav: NavController,
    bottomNav: NavController,
    authViewModel: AuthViewModel = hiltViewModel(),
    appointmentViewModel: AppointmentViewModel = hiltViewModel(),
    locationViewModel: LocationViewModel = hiltViewModel()
) {
    val currentUser by authViewModel.user.collectAsState()
    val scrollState = rememberScrollState()
    val userId = currentUser?.id // Use Firebase Auth UID
    // Collect appointments state
    val appointmentsState by appointmentViewModel.userAppointmentsState.collectAsState()
    val locationState by locationViewModel.locationState


    LaunchedEffect(Unit) {
        userId?.let {
            appointmentViewModel.getUserAppointments(it)
        }
        locationViewModel.loadUserLocation()
    }


    val appointment = when (val state = appointmentsState) {
        is AppointmentUiState.AppointmentsLoaded -> state.appointments
        else -> emptyList()
    }


    // Function to calculate distance between two coordinates using Haversine formula
    fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val earthRadiusKm = 6371.0
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a =
            sin(dLat / 2) * sin(dLat / 2) + cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) * sin(
                dLon / 2
            ) * sin(dLon / 2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return earthRadiusKm * c
    }


    // Filter clinics within 5 km from user's location
    val nearbyClinics = when (val locState = locationState) {
        is LocationState.Success -> {
            val userLat = locState.location.latitude
            val userLon = locState.location.longitude


            ClinicSamples.map { clinic ->
                val distance =
                    calculateDistance(userLat, userLon, clinic.latitude, clinic.longitude)
                clinic to distance
            }.filter { (_, distance) -> distance <= 5.0 }.sortedBy { (_, distance) -> distance }
                .map { (clinic, _) -> clinic }
        }


        else -> emptyList()
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(vertical = 8.dp)
    ) {
        Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Hello,", style = MaterialTheme.typography.bodyMedium
                    )


                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable {
                            bottomNav.navigate(Routes.PROFILE) {
                                popUpTo(Routes.HOME) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }) {
                        Text(
                            text = currentUser?.name ?: "User",
                            style = MaterialTheme.typography.titleSmall.copy(color = PrimaryMain)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            painter = painterResource(id = R.drawable.ic_next),
                            contentDescription = "Go to profile",
                            tint = Grey60,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }


                Icon(
                    painter = painterResource(id = R.drawable.ic_notification),
                    contentDescription = "Notification",
                    tint = Grey70,
                    modifier = Modifier
                        .size(32.dp)
                        .clickable {
                            rootNav.navigate(Routes.NOTIFICATION)
                        })
            }


            Spacer(modifier = Modifier.height(20.dp))


            // Upcoming Vaccine Section
            SectionHeader(
                title = "Upcoming Vaccine",
                subtitle = "Don't forget to schedule your upcoming vaccine",
                onClickViewAll = {
                    bottomNav.navigate(Routes.TRACKER) {
                        popUpTo(Routes.HOME) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                })


            Spacer(modifier = Modifier.height(12.dp))


            // Filter upcoming appointments: PENDING/CONFIRMED dan tanggal >= today
            val today = LocalDate.now()
            val upcomingAppointments = appointment.filter { appt ->
                val appointmentDate = try {
                    LocalDate.parse(appt.date)
                } catch (e: Exception) {
                    null
                }
                appointmentDate != null && !appointmentDate.isBefore(today) && (appt.status == com.example.immunify.data.model.AppointmentStatus.PENDING || appt.status == com.example.immunify.data.model.AppointmentStatus.COMPLETED)
            }.sortedBy { appt -> LocalDate.parse(appt.date) }.take(3)


            when {
                appointmentsState is AppointmentUiState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp))
                    }
                }


                upcomingAppointments.isEmpty() -> {
                    EmptyState("No upcoming appointments yet. Set one to get started.")
                }


                else -> {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        upcomingAppointments.forEach { appt ->
                            UpcomingVaccineCardFromAppointment(appointment = appt)
                        }
                    }
                }
            }


            Spacer(modifier = Modifier.height(20.dp))


            // Clinics Nearby Section
            SectionHeader(
                title = "Clinics Nearby",
                subtitle = "Find the closest clinic to your location",
                onClickViewAll = {
                    bottomNav.navigate(Routes.CLINICS) {
                        popUpTo(Routes.HOME) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }


            )


            Spacer(modifier = Modifier.height(12.dp))
        }


        when {
            locationState is LocationState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                }
            }


            locationState is LocationState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = (locationState as LocationState.Error).message,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Grey60
                    )
                }
            }


            nearbyClinics.isEmpty() -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No clinics found within 5 km",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Grey60
                    )
                }
            }


            else -> {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(horizontal = 20.dp)
                ) {
                    items(nearbyClinics) { clinic ->
                        ClinicHomeCard(
                            clinic = clinic, onClick = {
                                rootNav.navigate(Routes.clinicDetailRoute(clinic.id))
                            })
                    }
                }
            }
        }


        Spacer(modifier = Modifier.height(20.dp))


        // Disease Knowledge Section
        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            SectionHeader(
                title = "Prevention Starts with Knowledge",
                subtitle = "Get insights about vaccination",
                onClickViewAll = { rootNav.navigate(Routes.INSIGHTS) })


            Spacer(modifier = Modifier.height(12.dp))
        }


        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 20.dp)
        ) {
            items(DiseaseSamples) { disease ->
                DiseaseCard(
                    disease = disease, onClick = {
                        rootNav.navigate(Routes.diseaseDetailRoute(disease.id))
                    })
            }
        }
    }
}
