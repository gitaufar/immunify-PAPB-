package com.example.immunify.ui.tracker

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.immunify.data.local.AppointmentSamples
import com.example.immunify.data.local.ChildSamples
import com.example.immunify.data.model.ChildData
import com.example.immunify.domain.model.Appointment
import com.example.immunify.ui.auth.AuthViewModel
import com.example.immunify.ui.clinics.viewmodel.AppointmentUiState
import com.example.immunify.ui.clinics.viewmodel.AppointmentViewModel
import com.example.immunify.ui.component.AddProfileSheet
import com.example.immunify.ui.component.AddRecordSheet
import com.example.immunify.ui.component.AppBar
import com.example.immunify.ui.component.AppointmentCalendar
import com.example.immunify.ui.component.AppointmentDropdown
import com.example.immunify.ui.component.SectionHeader
import com.example.immunify.ui.component.SelectProfileSheet
import com.example.immunify.ui.component.YearMonthSelectionSheet
import com.example.immunify.ui.theme.ImmunifyTheme
import com.example.immunify.ui.theme.PrimaryMain
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackerScreen(
    modifier: Modifier = Modifier,
    yearMonth: YearMonth,
    onNextMonthClick: (YearMonth) -> Unit = {},
    appointmentViewModel: AppointmentViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel()
) {
    var showDateSheet by remember { mutableStateOf(false) }
    var showAddRecordSheet by remember { mutableStateOf(false) }
    var showAddProfileSheet by remember { mutableStateOf(false) }
    var showSelectProfileSheet by remember { mutableStateOf(false) }

    var selectedYM by remember { mutableStateOf(yearMonth) }
    var selectedVaccinant by remember { mutableStateOf<ChildData?>(null) }
    
    // Get current user from Firebase Auth
    val currentUser = authViewModel.getUser()
    val userId = currentUser?.uid // Use Firebase Auth UID
    
    // Collect appointments state
    val appointmentsState by appointmentViewModel.userAppointmentsState.collectAsState()
    val currentMonth by appointmentViewModel.currentMonth.collectAsState()
    
    // Fetch appointments when user changes (only if logged in)
    LaunchedEffect(userId) {
        userId?.let {
            appointmentViewModel.getUserAppointments(it)
        }
    }
    
    // Update selected year-month when currentMonth changes
    LaunchedEffect(currentMonth) {
        selectedYM = currentMonth
    }
    
    // Extract appointments list dari state
    val appointments = when (val state = appointmentsState) {
        is AppointmentUiState.AppointmentsLoaded -> state.appointments
        else -> emptyList()
    }

    Column(modifier = modifier.fillMaxSize()) {

        // AppBar Tracker
        AppBar(
            title = "",
            currentYM = selectedYM,
            onBackClick = {},
            isOnTracker = true,
            onNextClick = { showDateSheet = true },
            onCalendarClick = { showAddProfileSheet = true },
            onAddClick = { showAddRecordSheet = true },
        )

        // Loading indicator
        if (appointmentsState is AppointmentUiState.Loading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            // Konten Scrollable
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {

                // Calendar
                item {
                    AppointmentCalendar(
                        appointments = appointments,
                        yearMonth = selectedYM
                    )
                }

                // Section header
                item {
                    SectionHeader(title = "Appointments", onFilterClick = {})
                }

                // Show message if no appointments
                if (appointments.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No appointments yet",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                // Group appointments by date
                val grouped = appointments
                    .groupBy { LocalDate.parse(it.date) }
                    .toSortedMap()

                grouped.forEach { (date, listForDate) ->

                    // Tanggal header warna PrimaryMain
                    item {
                        Text(
                            text = date.format(
                                DateTimeFormatter.ofPattern("d MMMM", Locale.getDefault())
                            ),
                            color = PrimaryMain,
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }

                    // AppointmentDropdown per appointment
                    items(listForDate) { appointment ->
                        AppointmentDropdown(appointment)
                    }
                }
            }
        }
    }

    // Bottom Sheet Year-Month Selection
    if (showDateSheet) {
        YearMonthSelectionSheet(
            onSelect = { ym ->
                selectedYM = ym
                appointmentViewModel.setMonth(ym.monthValue, ym.year)
                onNextMonthClick(ym)
            },
            onDismiss = { showDateSheet = false }
        )
    }

    // Tes, ganti lagi
    if (showAddProfileSheet) {
        AddProfileSheet(
            onDismiss = { showAddProfileSheet = false },
            onSuccess = { showAddProfileSheet = false }
        )
    }

    // Add Record Sheet
    if (showAddRecordSheet) {
        AddRecordSheet(
            selectedVaccinant = selectedVaccinant,
            onSelectVaccinant = { child -> selectedVaccinant = child },
            onDismiss = { showAddRecordSheet = false },
            onDone = { showAddRecordSheet = false }
        )
    }

    // Select Profile Sheet (Vaccinant)
    if (showSelectProfileSheet) {
        SelectProfileSheet(
            children = ChildSamples,
            selectedChild = selectedVaccinant,
            onDismiss = { showSelectProfileSheet = false },
            onSelect = { child ->
                selectedVaccinant = child
                showSelectProfileSheet = false
            },
            onAddNewProfile = {
                showSelectProfileSheet = false
                showAddProfileSheet = true
            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewTrackerScreen() {
    ImmunifyTheme {
        TrackerScreen(
            yearMonth = YearMonth.now(),
            onNextMonthClick = {}
        )
    }
}
