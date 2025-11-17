package com.example.immunify.ui.clinics

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.immunify.ui.component.*
import com.example.immunify.ui.theme.*
import com.example.immunify.data.model.ClinicData
import com.example.immunify.data.model.ClinicDetailCardType

@Composable
fun ClinicDetailScreen(
    rootNav: NavController,
    clinic: ClinicData,
    userLatitude: Double,
    userLongitude: Double,
    onBackClick: () -> Unit = {},
) {
    var isBookmarked by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabTitles = listOf("Information", "Reviews")

    Scaffold(
        bottomBar = {
            BottomAppBar(
                text = "Set Appointment",
                onMainClick = {
                    rootNav.navigate(Routes.setAppointmentRoute(clinic.id))
                },
                onCallClick = { }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AppBar(
                title = clinic.name,
                onBackClick = onBackClick,
                showIcon = true,
                isBookmarked = isBookmarked,
                onBookmarkClick = { isBookmarked = !isBookmarked },
                onShareClick = { }
            )

            // Konten utama pakai LazyColumn
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 4.dp)
            ) {
                item {
                    ClinicDetailHeader(
                        clinic = clinic,
                        userLatitude = userLatitude,
                        userLongitude = userLongitude
                    )
                }

                // TabRow
                item {
                    TabRow(
                        selectedTabIndex = selectedTab,
                        containerColor = White10,
                        contentColor = PrimaryMain,
                        divider = {},
                        indicator = { tabPositions ->
                            val indicatorLeft by animateDpAsState(
                                targetValue = tabPositions[selectedTab].left,
                                label = "indicatorLeft"
                            )
                            val indicatorRight by animateDpAsState(
                                targetValue = tabPositions[selectedTab].right,
                                label = "indicatorRight"
                            )

                            Box(
                                Modifier
                                    .fillMaxWidth()
                                    .wrapContentSize(Alignment.BottomStart)
                                    .offset(x = indicatorLeft)
                                    .width(indicatorRight - indicatorLeft)
                                    .height(2.dp)
                                    .background(PrimaryMain)
                            )
                        }
                    ) {
                        tabTitles.forEachIndexed { index, title ->
                            val selected = selectedTab == index
                            val textColor by animateColorAsState(
                                targetValue = if (selected) Black100 else Grey60,
                                label = "tabColor"
                            )

                            Tab(
                                selected = selected,
                                onClick = { selectedTab = index },
                                text = {
                                    Text(
                                        text = title,
                                        style = MaterialTheme.typography.labelLarge.copy(color = textColor)
                                    )
                                }
                            )
                        }
                    }
                }

                // Konten tiap tab
                when (selectedTab) {
                    0 -> item { ClinicInformationTab(clinic) }
                    1 -> item { ClinicReviewsTab() }
                }
            }
        }
    }
}


@Composable
fun ClinicInformationTab(clinic: ClinicData) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ClinicDetailCard(
            type = ClinicDetailCardType.ADDRESS,
            title = clinic.address,
            subtitle = "${clinic.district}, ${clinic.city}"
        )
        ClinicDetailCard(
            type = ClinicDetailCardType.TIME,
            title = "Open",
            subtitle = clinic.openingHours ?: "24 Hours"
        )
        ClinicDetailCard(
            type = ClinicDetailCardType.WEBSITE,
            title = clinic.website ?: "-",
            subtitle = null
        )

        Spacer(modifier = Modifier.height(2.dp))

        SectionHeader(title = "List of Available Vaccines")

        Spacer(modifier = Modifier.height(2.dp))

        VaccineDropdown(clinic)
    }
}

@Composable
fun ClinicReviewsTab() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 160.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "No reviews available yet.",
            style = MaterialTheme.typography.bodyMedium.copy(color = Grey70),
            textAlign = TextAlign.Center
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewClinicDetailScreen() {
//    val sampleClinic = ClinicData(
//        id = "1",
//        name = "RS EMC Pulomas",
//        imageUrl = "https://example.com/hospital.jpg",
//        address = "Jl. Pulo Mas Bar. VI No.20",
//        district = "Kec. Pulo Gadung",
//        city = "DKI Jakarta",
//        latitude = -6.188,
//        longitude = 106.88,
//        rating = 4.9,
//        website = "www.emc.id",
//        openingHours = "24 Hours",
//        availableVaccines = listOf(
//            VaccineData(
//                id = "v1",
//                name = "HPV vaccine",
//                description = listOf(
//                    "HPV vaccine protects against the sexually transmitted human papillomavirus.",
//                    "Recommended for both genders, typically at ages 11–12 or as early as 9.",
//                    "Administered in a series of 2–3 doses.",
//                    "Highly effective in preventing certain cancers and genital warts.",
//                    "Generally safe with mild side effects such as pain, redness, or swelling at the injection site."
//                ),
//                brand = listOf("Gardasil", "Cervarix")
//            ),
//            VaccineData(
//                id = "v2",
//                name = "Hepatitis B vaccine",
//                description = listOf("Protects against hepatitis B virus infection."),
//                brand = listOf("Engerix-B")
//            )
//        )
//    )
//
//    ImmunifyTheme {
//        ClinicDetailScreen(
//            clinic = sampleClinic,
//            userLatitude = -6.2,
//            userLongitude = 106.8
//        )
//    }
//}
