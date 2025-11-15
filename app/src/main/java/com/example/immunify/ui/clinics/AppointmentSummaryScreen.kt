package com.example.immunify.ui.clinics


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.immunify.data.model.AppointmentCardType
import com.example.immunify.data.model.ChildData
import com.example.immunify.data.model.ClinicData
import com.example.immunify.data.model.ClinicDetailCardType
import com.example.immunify.data.model.UserData
import com.example.immunify.data.model.VaccineData
import com.example.immunify.ui.component.AppBar
import com.example.immunify.ui.component.AppointmentSummaryCard
import com.example.immunify.ui.component.BottomAppBar
import com.example.immunify.ui.component.ClinicDetailCard
import com.example.immunify.ui.component.SectionHeader
import com.example.immunify.ui.theme.ImmunifyTheme

@Composable
fun AppointmentSummaryScreen(
    user: UserData,
    clinic: ClinicData,
    selectedDate: String,
    selectedTime: String,
    onBackClick: () -> Unit,
    onConfirmClick: () -> Unit,
) {
    Scaffold(
        bottomBar = {
            BottomAppBar(
                text = "Confirm",
                onMainClick = onConfirmClick
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            AppBar(
                title = "Appointment Summary",
                onBackClick = onBackClick,
                showIcon = false
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {

                // PARENT / GUARDIAN
                item {
                    SectionHeader(title = "Parent/Guardian Information")

                    Spacer(modifier = Modifier.height(12.dp))

                    ClinicDetailCard(
                        type = ClinicDetailCardType.PARENT,
                        title = user.name,
                        subtitle = user.phoneNumber
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }

                // LOCATION
                item {
                    SectionHeader(title = "Location")

                    Spacer(modifier = Modifier.height(12.dp))

                    ClinicDetailCard(
                        type = ClinicDetailCardType.CLINIC,
                        title = clinic.name,
                        subtitle = "${clinic.address}\n${clinic.district}, ${clinic.city}",
                        showChange = false
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }

                // DATE & TIME
                item {
                    SectionHeader(title = "Date & Time")

                    Spacer(modifier = Modifier.height(12.dp))

                    AppointmentSummaryCard(
                        type = AppointmentCardType.DATE_TIME,
                        date = selectedDate,
                        time = selectedTime
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }

                // VACCINE
                item {
                    SectionHeader(title = "Vaccine")

                    Spacer(modifier = Modifier.height(12.dp))

                    ClinicDetailCard(
                        type = ClinicDetailCardType.VACCINE,
                        title = clinic.availableVaccines.firstOrNull()?.name ?: "-",
                        showChange = false
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }

                // VACCINANT
                item {
                    SectionHeader(title = "Vaccinant")

                    Spacer(modifier = Modifier.height(12.dp))

                    AppointmentSummaryCard(
                        type = AppointmentCardType.VACCINANTS,
                        vaccinants = user.children
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }

                // CLINIC POLICY
                item {
                    SectionHeader(title = "Clinic Policy")

                    Spacer(modifier = Modifier.height(12.dp))

                    ClinicDetailCard(
                        type = ClinicDetailCardType.POLICY,
                        title = "Cancellation",
                        subtitle = "Our clinic requires a 24-hour notice for appointment cancellations. If you need to cancel your appointment, please call us at least 24 hours in advance. Failure to provide adequate notice may result in a cancellation fee."
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun PreviewAppointmentSummaryScreen() {
    ImmunifyTheme {

        val mockChildren = listOf(
            ChildData(
                id = "1",
                name = "Jane Doe",
                birthDate = "2020-01-01",
                gender = "Female"
            ),
            ChildData(
                id = "2",
                name = "John Doe",
                birthDate = "2019-05-10",
                gender = "Male"
            )
        )

        val mockUser = UserData(
            id = "u1",
            name = "Dona Doe",
            email = "dona@mail.com",
            password = "",
            phoneNumber = "0218374920328",
            children = mockChildren
        )

        val mockClinic = ClinicData(
            id = "1",
            name = "RS EMC Pulomas",
            imageUrl = "",
            address = "Jl. Pulo Mas Bar. VI No.20",
            district = "Kec. Pulo Gadung",
            city = "DKI Jakarta",
            latitude = 0.0,
            longitude = 0.0,
            rating = 4.8,
            website = "",
            openingHours = "24 Hours",
            availableVaccines = listOf(
                VaccineData(
                    id = "v1",
                    name = "Varicella (Chicken Pox)",
                    description = listOf("Vaccine for chickenpox."),
                    brand = listOf("Varivax")
                )
            )
        )

        AppointmentSummaryScreen(
            user = mockUser,
            clinic = mockClinic,
            selectedDate = "Sat, 22nd April 2023",
            selectedTime = "13:00 - 14:30",
            onBackClick = {},
            onConfirmClick = {}
        )
    }
}
