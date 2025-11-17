package com.example.immunify.ui.clinics

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.immunify.data.model.AppointmentCardType
import com.example.immunify.data.model.AppointmentData
import com.example.immunify.data.model.ClinicDetailCardType
import com.example.immunify.ui.component.AppBar
import com.example.immunify.ui.component.AppointmentSummaryCard
import com.example.immunify.ui.component.BottomAppBar
import com.example.immunify.ui.component.ClinicDetailCard
import com.example.immunify.ui.component.SectionHeader

@Composable
fun AppointmentSummaryScreen(
    appointment: AppointmentData,
    onBackClick: () -> Unit,
    onConfirmClick: () -> Unit
) {
    val user = appointment.parent
    val clinic = appointment.clinic

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
                // PARENT
                item {
                    SectionHeader(title = "Parent/Guardian Information")
                    Spacer(Modifier.height(12.dp))
                    ClinicDetailCard(
                        type = ClinicDetailCardType.PARENT,
                        title = user.name,
                        subtitle = user.phoneNumber
                    )
                    Spacer(Modifier.height(16.dp))
                }

                // LOCATION
                item {
                    SectionHeader(title = "Location")
                    Spacer(Modifier.height(12.dp))
                    ClinicDetailCard(
                        type = ClinicDetailCardType.CLINIC,
                        title = clinic.name,
                        subtitle = "${clinic.address}\n${clinic.district}, ${clinic.city}",
                        showChange = false
                    )
                    Spacer(Modifier.height(16.dp))
                }

                // DATE & TIME
                item {
                    SectionHeader(title = "Date & Time")
                    Spacer(Modifier.height(12.dp))
                    AppointmentSummaryCard(
                        type = AppointmentCardType.DATE_TIME,
                        date = appointment.date,
                        time = appointment.timeSlot
                    )
                    Spacer(Modifier.height(16.dp))
                }

                // VACCINE
                item {
                    SectionHeader(title = "Vaccine")
                    Spacer(Modifier.height(12.dp))
                    ClinicDetailCard(
                        type = ClinicDetailCardType.VACCINE,
                        title = appointment.vaccine.name,
                        showChange = false
                    )
                    Spacer(Modifier.height(16.dp))
                }

                // VACCINANT
                item {
                    SectionHeader(title = "Vaccinant")
                    Spacer(Modifier.height(12.dp))
                    AppointmentSummaryCard(
                        type = AppointmentCardType.VACCINANTS,
                        vaccinants = appointment.vaccinants
                    )
                    Spacer(Modifier.height(16.dp))
                }

                // POLICY
                item {
                    SectionHeader(title = "Clinic Policy")
                    Spacer(Modifier.height(12.dp))
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