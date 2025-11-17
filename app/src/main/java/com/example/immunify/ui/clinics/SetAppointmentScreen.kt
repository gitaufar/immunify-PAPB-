package com.example.immunify.ui.clinics

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.immunify.data.local.ClinicSamples
import com.example.immunify.data.model.AppointmentData
import com.example.immunify.data.model.ChildData
import com.example.immunify.ui.component.*
import com.example.immunify.data.model.ClinicData
import com.example.immunify.data.model.ClinicDetailCardType
import com.example.immunify.data.model.UserData
import com.example.immunify.util.formatFullDate
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@SuppressLint("MutableCollectionMutableState")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SetAppointmentScreen(
    user: UserData,
    clinic: ClinicData,
    selectedDate: LocalDate,
    onBackClick: () -> Unit,
    onContinueClick: (AppointmentData) -> Unit
) {
    var currentClinic by remember { mutableStateOf(clinic) }
    var selectedVaccine by remember { mutableStateOf(clinic.availableVaccines.firstOrNull()) }

    var showClinicSheet by remember { mutableStateOf(false) }
    var showVaccineSheet by remember { mutableStateOf(false) }

    var dateRaw by remember { mutableStateOf(selectedDate) }
    var dateFormatted by remember { mutableStateOf(formatFullDate(dateRaw)) }

    var showDatePicker by remember { mutableStateOf(false) }
    var selectedTime by remember { mutableStateOf("") }

    var selectedVaccinants by remember { mutableStateOf<List<ChildData>>(emptyList()) }

    Scaffold(
        bottomBar = {
            BottomAppBar(
                text = "Continue",
                onMainClick = {
                    val appointment = AppointmentData(
                        id = System.currentTimeMillis().toString(),
                        parent = user,
                        clinic = currentClinic,
                        date = dateRaw.format(DateTimeFormatter.ISO_DATE),
                        timeSlot = selectedTime,
                        vaccine = selectedVaccine!!,
                        vaccinants = selectedVaccinants
                    )
                    onContinueClick(appointment)
                }
            )
        }
    ) { padding ->

        // Clinic Selection Bottom Sheet
        if (showClinicSheet) {
            ClinicSelectionSheet(
                clinics = ClinicSamples,
                onSelect = { selected ->
                    currentClinic = selected
                    selectedVaccine = selected.availableVaccines.firstOrNull() // reset vaccine
                    showClinicSheet = false
                },
                onDismiss = { showClinicSheet = false }
            )
        }

        // Date Picker
        if (showDatePicker) {
            AppDatePicker(
                initialDate = dateRaw,
                onDateSelected = { newDate ->
                    dateRaw = newDate
                    dateFormatted = formatFullDate(newDate)
                    showDatePicker = false
                },
                onDismiss = { showDatePicker = false }
            )
        }

        // Vaccine Selection Bottom Sheet
        if (showVaccineSheet) {
            VaccineSelectionSheet(
                vaccines = currentClinic.availableVaccines,
                onSelect = { v ->
                    selectedVaccine = v
                    showVaccineSheet = false
                },
                onDismiss = { showVaccineSheet = false }
            )
        }

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

                // Parent Section
                item {
                    SectionHeader(
                        title = "Parent/Guardian Information",
                        subtitle = "Used to schedule and confirm the appointment."
                    )

                    Spacer(Modifier.height(12.dp))

                    ClinicDetailCard(
                        type = ClinicDetailCardType.PARENT,
                        title = user.name,
                        subtitle = user.phoneNumber
                    )

                    Spacer(Modifier.height(16.dp))
                }

                // Location
                item {
                    SectionHeader(title = "Location")

                    Spacer(Modifier.height(12.dp))

                    ClinicDetailCard(
                        type = ClinicDetailCardType.CLINIC,
                        title = currentClinic.name,
                        subtitle = "${currentClinic.address}\n${currentClinic.district}, ${currentClinic.city}",
                        onClick = { showClinicSheet = true }
                    )

                    Spacer(Modifier.height(16.dp))
                }

                // Date & Time
                item {
                    SectionHeader(title = "Date & Time")

                    Spacer(Modifier.height(12.dp))

                    ClinicDetailCard(
                        type = ClinicDetailCardType.DATE,
                        title = dateFormatted,
                        onClick = { showDatePicker = true }
                    )

                    Spacer(Modifier.height(16.dp))

                    ScheduleDropdown(
                        clinic = currentClinic,
                        onTimeSelected = { time -> selectedTime = time }
                    )

                    Spacer(Modifier.height(16.dp))
                }

                // Vaccine
                item {
                    SectionHeader(title = "Vaccine")

                    Spacer(Modifier.height(12.dp))

                    ClinicDetailCard(
                        type = ClinicDetailCardType.VACCINE,
                        title = selectedVaccine?.name ?: "-",
                        onClick = { showVaccineSheet = true }
                    )

                    Spacer(Modifier.height(16.dp))
                }

                // Vaccinants
                item {
                    SectionHeader(title = "Vaccinant")
                    Spacer(Modifier.height(12.dp))
                    VaccinantSection(
                        children = user.children,
                        onUpdate = { updatedVaccinants ->
                            selectedVaccinants = updatedVaccinants
                        }
                    )
                }
            }
        }
    }
}
