package com.example.immunify.ui.clinics.viewmodel

import com.example.immunify.domain.model.Appointment

/**
 * UI State untuk Appointment operations
 */
sealed class AppointmentUiState {
    object Idle : AppointmentUiState()
    object Loading : AppointmentUiState()
    data class Success(val message: String) : AppointmentUiState()
    data class Error(val message: String) : AppointmentUiState()
    data class AppointmentsLoaded(val appointments: List<Appointment>) : AppointmentUiState()
}
