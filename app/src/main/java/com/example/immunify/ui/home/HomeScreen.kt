package com.example.immunify.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.immunify.R
import com.example.immunify.core.LocalAppState
import com.example.immunify.data.local.ClinicSamples
import com.example.immunify.data.local.DiseaseSamples
import com.example.immunify.data.local.VaccineSamples
import com.example.immunify.ui.component.*
import com.example.immunify.ui.theme.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    rootNav: NavController,
    bottomNav: NavController,
) {
    val appState = LocalAppState.current
    val userLatitude = appState.userLatitude
    val userLongitude = appState.userLongitude

    val scrollState = rememberScrollState()

    // mengurutkan berdasarkan jarak
    val sortedClinics = ClinicSamples.sortedBy { clinic ->
        calculateDistanceKm(
            userLatitude,
            userLongitude,
            clinic.latitude,
            clinic.longitude
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(vertical = 8.dp)
    ) {
        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Hello,",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Jane Doe",
                            style = MaterialTheme.typography.titleSmall.copy(color = PrimaryMain)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            painter = painterResource(id = R.drawable.ic_next),
                            contentDescription = null,
                            tint = Grey60,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Icon(
                    painter = painterResource(id = R.drawable.ic_notification),
                    contentDescription = "Notification",
                    tint = Grey70,
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Upcoming Vaccine Section
            SectionHeader(
                title = "Upcoming Vaccine",
                subtitle = "Don't forget to schedule your upcoming vaccine",
                onClickViewAll = {}
            )

            Spacer(modifier = Modifier.height(12.dp))

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                VaccineSamples.forEach { vaccine ->
                    UpcomingVaccineCard(vaccine = vaccine)
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

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 20.dp)
        ) {
            items(sortedClinics) { clinic ->
                ClinicHomeCard(
                    clinic = clinic,
                    onClick = {
                        rootNav.navigate(Routes.clinicDetailRoute(clinic.id))
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Disease Knowledge Section
        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            SectionHeader(
                title = "Prevention Starts with Knowledge",
                subtitle = "Get insights about vaccination",
                onClickViewAll = { rootNav.navigate(Routes.INSIGHTS) }
            )

            Spacer(modifier = Modifier.height(12.dp))
        }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 20.dp)
        ) {
            items(DiseaseSamples) { disease ->
                DiseaseCard(
                    disease = disease,
                    onClick = {
                        rootNav.navigate(Routes.diseaseDetailRoute(disease.id))
                    }
                )
            }
        }
    }
}
