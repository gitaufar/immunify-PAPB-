package com.example.immunify.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.immunify.R
import com.example.immunify.ui.component.*
import com.example.immunify.ui.navigation.Routes
import com.example.immunify.ui.theme.*

@Composable
fun HomeScreen(
    navController: NavController
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(vertical = 8.dp)
    ) {
        // Header dibungkus padding horizontal
        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            // HEADER: Greeting dan Notifikasi
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
                            modifier = Modifier.size(24.dp)
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

            Spacer(modifier = Modifier.height(24.dp))

            // SECTION 1: Upcoming Vaccine
            SectionHeader(
                title = "Upcoming Vaccine",
                subtitle = "Don't forget to schedule your upcoming vaccine"
            )

            Spacer(modifier = Modifier.height(12.dp))

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                UpcomingVaccineCard(modifier = Modifier, "HPV", "3 days", isWarning = true)
                UpcomingVaccineCard(modifier = Modifier, "Influenza", "5 days", isWarning = true)
                UpcomingVaccineCard(
                    modifier = Modifier,
                    "Varicella (Chicken Pox)",
                    "6 months",
                    isWarning = false
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // SECTION 2 HEADER: Clinics Nearby
            SectionHeader(
                title = "Clinics Nearby",
                subtitle = "Find the closest clinic to your location"
            )

            Spacer(modifier = Modifier.height(12.dp))
        }

        // LazyRow keluar dari padding horizontal utama
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 20.dp)
        ) {
            items(3) {
                ClinicsHomeCard(
                    hospitalName = "RS EMC Pulomas",
                    address = "Jl. Pulo Mas Bar. VI No.20, Kec. Pulo Gadung",
                    distance = "2 km",
                    rating = 4.9,
                    imageRes = R.drawable.image_hospital
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // SECTION 3 HEADER: Disease Knowledge
        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            SectionHeader(
                title = "Prevention Starts with Knowledge",
                subtitle = "Get insights about vaccination",
                onClickViewAll = {navController.navigate(Routes.INSIGHTS)}
            )

            Spacer(modifier = Modifier.height(12.dp))
        }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 20.dp)
        ) {
            item {
                DiseaseCard(
                    imageRes = R.drawable.image_hpv,
                    diseaseName = "HPV"
                )
            }
            item {
                DiseaseCard(
                    imageRes = R.drawable.image_polio,
                    diseaseName = "Poliovirus"
                )
            }
            item {
                DiseaseCard(
                    imageRes = R.drawable.image_typhoid,
                    diseaseName = "Typhoid"
                )
            }
            item {
                DiseaseCard(
                    imageRes = R.drawable.image_covid,
                    diseaseName = "Covid"
                )
            }
        }
    }
}

// Komponen untuk judul section
@Composable
fun SectionHeader(title: String, subtitle: String, onClickViewAll: () -> Unit = {}) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall.copy(color = Grey70),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Text(
            text = "View All",
            style = MaterialTheme.typography.bodySmall.copy(color = Grey60),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}


//@Preview(showBackground = true)
//@Composable
//fun PreviewHomeScreen() {
//    ImmunifyTheme {
//        HomeScreen()
//    }
//}
