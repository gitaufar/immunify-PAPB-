package com.example.immunify.ui.clinics

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.immunify.core.LocalAppState
import com.example.immunify.data.local.ClinicSamples
import com.example.immunify.data.model.*
import com.example.immunify.domain.model.Child
import com.example.immunify.ui.component.*
import com.example.immunify.ui.profile.viewmodel.ChildUiState
import com.example.immunify.ui.profile.viewmodel.ChildViewModel
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
    childViewModel: ChildViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onContinueClick: (AppointmentData) -> Unit
) {
    val appState = LocalAppState.current
    val userLatitude = appState.userLatitude
    val userLongitude = appState.userLongitude
    
    // Fetch children from Firestore
    val userChildrenState by childViewModel.userChildrenState.collectAsState()
    var childrenList by remember { mutableStateOf<List<ChildData>>(emptyList()) }
    
    LaunchedEffect(user.id) {
        if (user.id.isNotEmpty()) {
            childViewModel.getUserChildren(user.id)
        }
    }
    
    // Convert Child domain model to ChildData for UI
    LaunchedEffect(userChildrenState) {
        when (val state = userChildrenState) {
            is ChildUiState.ChildrenLoaded -> {
                childrenList = state.children.map { child ->
                    ChildData(
                        id = child.id,
                        name = child.name,
                        birthDate = child.birthDate,
                        gender = if (child.gender == "Male") Gender.MALE else Gender.FEMALE
                    )
                }
            }
            else -> {}
        }
    }

    // Sorting klinik berdasarkan jarak
    val sortedClinics = remember(userLatitude, userLongitude) {
        ClinicSamples.sortedBy { c ->
            calculateDistanceKm(
                userLatitude, userLongitude, c.latitude, c.longitude
            )
        }
    }

    var currentClinic by remember { mutableStateOf(clinic) }
    var selectedVaccine by remember { mutableStateOf(clinic.availableVaccines.firstOrNull()) }

    var showClinicSheet by remember { mutableStateOf(false) }
    var showVaccineSheet by remember { mutableStateOf(false) }

    var dateRaw by remember { mutableStateOf(selectedDate) }
    var dateFormatted by remember { mutableStateOf(formatFullDate(selectedDate)) }

    var selectedTime by remember { mutableStateOf("") }
    var selectedVaccinants by remember { mutableStateOf<List<ChildData>>(emptyList()) }

    var showDatePicker by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            BottomAppBar(
                text = "Continue", onMainClick = {
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
                })
        }) { padding ->
        // Bottom Sheets
        if (showClinicSheet) {
            ClinicSelectionSheet(clinics = sortedClinics, onSelect = { selected ->
                currentClinic = selected
                selectedVaccine = selected.availableVaccines.firstOrNull()
                showClinicSheet = false
            }, onDismiss = { showClinicSheet = false })
        }

        if (showDatePicker) {
            AppDatePicker(initialDate = dateRaw, onDateSelected = { newDate ->
                dateRaw = newDate
                dateFormatted = formatFullDate(newDate)
                showDatePicker = false
            }, onDismiss = { showDatePicker = false })
        }

        if (showVaccineSheet) {
            VaccineSelectionSheet(vaccines = currentClinic.availableVaccines, onSelect = { v ->
                selectedVaccine = v
                showVaccineSheet = false
            }, onDismiss = { showVaccineSheet = false })
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            AppBar(
                title = "Set Appointment", onBackClick = onBackClick, showIcon = false
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(16.dp)
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

                // Location Section
                item {
                    SectionHeader(title = "Location")
                    Spacer(Modifier.height(12.dp))

                    ClinicDetailCard(
                        type = ClinicDetailCardType.CLINIC,
                        title = currentClinic.name,
                        subtitle = "${currentClinic.address}\n${currentClinic.district}, ${currentClinic.city}",
                        onClick = { showClinicSheet = true })

                    Spacer(Modifier.height(16.dp))
                }

                // Date & Time Section
                item {
                    SectionHeader(title = "Date & Time")
                    Spacer(Modifier.height(12.dp))

                    ClinicDetailCard(
                        type = ClinicDetailCardType.DATE,
                        title = dateFormatted,
                        onClick = { showDatePicker = true })

                    Spacer(Modifier.height(16.dp))

                    ScheduleDropdown(
                        clinic = currentClinic, onTimeSelected = { selectedTime = it })

                    Spacer(Modifier.height(16.dp))
                }

                // Vaccine Section
                item {
                    SectionHeader(title = "Vaccine")
                    Spacer(Modifier.height(12.dp))

                    ClinicDetailCard(
                        type = ClinicDetailCardType.VACCINE,
                        title = selectedVaccine?.name ?: "-",
                        onClick = { showVaccineSheet = true })

                    Spacer(Modifier.height(16.dp))
                }

                // Child/vaccinant section
                item {
                    SectionHeader(title = "Vaccinant")
                    Spacer(Modifier.height(12.dp))

                    when (userChildrenState) {
                        is ChildUiState.Loading -> {
                            Box(
                                modifier = Modifier.fillMaxWidth().padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                        is ChildUiState.Error -> {
                            Text(
                                text = "Failed to load children: ${(userChildrenState as ChildUiState.Error).message}",
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                        is ChildUiState.ChildrenLoaded, ChildUiState.Idle -> {
                            VaccinantSection(
                                children = childrenList,
                                onUpdate = { selectedVaccinants = it }
                            )
                        }
                        else -> {
                            VaccinantSection(
                                children = childrenList,
                                onUpdate = { selectedVaccinants = it }
                            )
                        }
                    }
                }
            }
        }
    }
}
