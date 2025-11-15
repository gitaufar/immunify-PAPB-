package com.example.immunify.ui.clinics

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.immunify.data.model.ChildData
import com.example.immunify.ui.component.*
import com.example.immunify.data.model.ClinicData
import com.example.immunify.data.model.ClinicDetailCardType
import com.example.immunify.data.model.UserData
import com.example.immunify.data.model.VaccineData
import com.example.immunify.ui.theme.ImmunifyTheme

@Composable
fun SetAppointmentScreen(
    user: UserData,
    clinic: ClinicData,
    selectedDate: String,
    onBackClick: () -> Unit,
    onContinueClick: (String) -> Unit = {}
) {
    var selectedTime by remember { mutableStateOf("") }

    Scaffold(
        bottomBar = {
            BottomAppBar(
                text = "Continue",
                onMainClick = { onContinueClick(selectedTime) }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            AppBar(
                title = "Set Appointment",
                onBackClick = onBackClick,
                showIcon = false
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {

                // Parent / Guardian Info
                item {
                    SectionHeader(
                        title = "Parent/Guardian Information",
                        subtitle = "Used to schedule and confirm the appointment."
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    ClinicDetailCard(
                        type = ClinicDetailCardType.PARENT,
                        title = user.name,
                        subtitle = user.phoneNumber
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Location
                item {
                    SectionHeader(title = "Location")

                    Spacer(modifier = Modifier.height(12.dp))

                    ClinicDetailCard(
                        type = ClinicDetailCardType.CLINIC,
                        title = clinic.name,
                        subtitle = "${clinic.address}\n${clinic.district}, ${clinic.city}",
                        onClick = { }
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Date & Time
                item {
                    SectionHeader(title = "Date & Time")

                    Spacer(modifier = Modifier.height(12.dp))

                    ClinicDetailCard(
                        type = ClinicDetailCardType.DATE,
                        title = selectedDate,
                        onClick = { }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    ScheduleDropdown(
                        clinic = clinic,
                        onTimeSelected = { time ->
                            selectedTime = time
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Vaccine
                item {
                    SectionHeader(title = "Vaccine")

                    Spacer(modifier = Modifier.height(12.dp))

                    ClinicDetailCard(
                        type = ClinicDetailCardType.VACCINE,
                        title = clinic.availableVaccines.firstOrNull()?.name ?: "-",
                        onClick = { }
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Vaccinant
                item {
                    SectionHeader(title = "Vaccinant")
                    Spacer(modifier = Modifier.height(12.dp))
                    VaccinantSection(children = user.children)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSetAppointmentScreen() {
    ImmunifyTheme {
        val mockChildren = listOf(
            ChildData(
                id = "1",
                name = "Aisha Putri",
                birthDate = "12 Jan 2020",
                gender = "Female"
            ),
            ChildData(
                id = "2",
                name = "Bima Aditya",
                birthDate = "3 Mar 2018",
                gender = "Male"
            )
        )

        val mockUser = UserData(
            id = "u1",
            name = "Sarah Rahma",
            email = "sarah@mail.com",
            password = "pass123",
            phoneNumber = "08123456789",
            children = mockChildren
        )

        val mockClinic = ClinicData(
            id = "1",
            name = "RS EMC Pulomas",
            imageUrl = "https://example.com/hospital.jpg",
            address = "Jl. Pulo Mas Bar. VI No.20",
            district = "Kec. Pulo Gadung",
            city = "DKI Jakarta",
            latitude = -6.188,
            longitude = 106.88,
            rating = 4.9,
            website = "www.emc.id",
            openingHours = "24 Hours",
            availableVaccines = listOf(
                VaccineData(
                    id = "v1",
                    name = "HPV vaccine",
                    description = listOf(
                        "HPV vaccine protects against the sexually transmitted human papillomavirus.",
                        "Recommended for both genders, typically at ages 11–12 or as early as 9.",
                        "Administered in a series of 2–3 doses.",
                        "Highly effective in preventing certain cancers and genital warts.",
                        "Generally safe with mild side effects such as pain, redness, or swelling at the injection site."
                    ),
                    brand = listOf("Gardasil", "Cervarix")
                ),
                VaccineData(
                    id = "v2",
                    name = "Hepatitis B vaccine",
                    description = listOf("Protects against hepatitis B virus infection."),
                    brand = listOf("Engerix-B")
                )
            )
        )

        SetAppointmentScreen(
            user = mockUser,
            clinic = mockClinic,
            selectedDate = "Sat, 22nd April 2023",
            onBackClick = { }
        )
    }
}
