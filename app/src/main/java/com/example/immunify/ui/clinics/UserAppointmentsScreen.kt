package com.example.immunify.ui.clinics

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.immunify.domain.model.Appointment
import com.example.immunify.ui.clinics.viewmodel.AppointmentUiState
import com.example.immunify.ui.clinics.viewmodel.AppointmentViewModel
import com.example.immunify.ui.component.AppBar

/**
 * Screen untuk menampilkan list appointments user
 * Contoh penggunaan GetUserAppointmentsUseCase
 */
@Composable
fun UserAppointmentsScreen(
    userId: String,
    onBackClick: () -> Unit = {},
    viewModel: AppointmentViewModel = hiltViewModel()
) {
    val appointmentsState by viewModel.userAppointmentsState.collectAsState()

    LaunchedEffect(userId) {
        viewModel.getUserAppointments(userId)
    }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            AppBar(
                title = "My Appointments",
                onBackClick = onBackClick,
                showIcon = false
            )

            when (val state = appointmentsState) {
                is AppointmentUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is AppointmentUiState.AppointmentsLoaded -> {
                    if (state.appointments.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("No appointments yet")
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(state.appointments) { appointment ->
                                AppointmentCard(
                                    appointment = appointment,
                                    onCancelClick = {
                                        viewModel.cancelAppointment(appointment.id)
                                    }
                                )
                            }
                        }
                    }
                }

                is AppointmentUiState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = state.message,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }

                else -> {}
            }
        }
    }
}

@Composable
fun AppointmentCard(
    appointment: Appointment,
    onCancelClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = appointment.clinicName,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Vaccine: ${appointment.vaccineName}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Date: ${appointment.date} at ${appointment.timeSlot}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "For: ${appointment.vaccinantNames.joinToString(", ")}",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Status: ${appointment.status.name}",
                style = MaterialTheme.typography.bodySmall,
                color = when (appointment.status.name) {
                    "PENDING" -> MaterialTheme.colorScheme.primary
                    "COMPLETED" -> MaterialTheme.colorScheme.tertiary
                    "CANCELED" -> MaterialTheme.colorScheme.error
                    else -> MaterialTheme.colorScheme.onSurface
                }
            )

            if (appointment.status.name == "PENDING") {
                Button(
                    onClick = onCancelClick,
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Cancel")
                }
            }
        }
    }
}
