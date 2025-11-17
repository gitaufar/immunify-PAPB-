package com.example.immunify.ui.clinics.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.immunify.data.mapper.AppointmentMapper.toDomain
import com.example.immunify.data.model.AppointmentData
import com.example.immunify.domain.usecase.CancelAppointmentUseCase
import com.example.immunify.domain.usecase.CreateAppointmentUseCase
import com.example.immunify.domain.usecase.GetUserAppointmentsUseCase
import com.example.immunify.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel untuk manage appointment operations
 */
@HiltViewModel
class AppointmentViewModel @Inject constructor(
    private val createAppointmentUseCase: CreateAppointmentUseCase,
    private val getUserAppointmentsUseCase: GetUserAppointmentsUseCase,
    private val cancelAppointmentUseCase: CancelAppointmentUseCase
) : ViewModel() {

    // State untuk create appointment
    private val _createAppointmentState = MutableStateFlow<AppointmentUiState>(AppointmentUiState.Idle)
    val createAppointmentState: StateFlow<AppointmentUiState> = _createAppointmentState.asStateFlow()

    // State untuk user appointments
    private val _userAppointmentsState = MutableStateFlow<AppointmentUiState>(AppointmentUiState.Idle)
    val userAppointmentsState: StateFlow<AppointmentUiState> = _userAppointmentsState.asStateFlow()

    /**
     * Create new appointment
     */
    fun createAppointment(appointmentData: AppointmentData) {
        viewModelScope.launch {
            _createAppointmentState.value = AppointmentUiState.Loading
            
            // Convert AppointmentData (UI) ke Appointment (Domain)
            val appointment = appointmentData.toDomain()
            
            when (val result = createAppointmentUseCase(appointment)) {
                is Result.Success -> {
                    _createAppointmentState.value = AppointmentUiState.Success(
                        message = "Appointment created successfully with ID: ${result.data}"
                    )
                }
                is Result.Error -> {
                    _createAppointmentState.value = AppointmentUiState.Error(
                        message = result.exception.message ?: "Failed to create appointment"
                    )
                }
                is Result.Loading -> {
                    _createAppointmentState.value = AppointmentUiState.Loading
                }
            }
        }
    }

    /**
     * Get user appointments
     */
    fun getUserAppointments(userId: String) {
        viewModelScope.launch {
            _userAppointmentsState.value = AppointmentUiState.Loading
            
            when (val result = getUserAppointmentsUseCase(userId)) {
                is Result.Success -> {
                    _userAppointmentsState.value = AppointmentUiState.AppointmentsLoaded(
                        appointments = result.data
                    )
                }
                is Result.Error -> {
                    _userAppointmentsState.value = AppointmentUiState.Error(
                        message = result.exception.message ?: "Failed to load appointments"
                    )
                }
                is Result.Loading -> {
                    _userAppointmentsState.value = AppointmentUiState.Loading
                }
            }
        }
    }

    /**
     * Cancel appointment
     */
    fun cancelAppointment(appointmentId: String) {
        viewModelScope.launch {
            when (val result = cancelAppointmentUseCase(appointmentId)) {
                is Result.Success -> {
                    _createAppointmentState.value = AppointmentUiState.Success(
                        message = "Appointment cancelled successfully"
                    )
                }
                is Result.Error -> {
                    _createAppointmentState.value = AppointmentUiState.Error(
                        message = result.exception.message ?: "Failed to cancel appointment"
                    )
                }
                is Result.Loading -> {
                    _createAppointmentState.value = AppointmentUiState.Loading
                }
            }
        }
    }

    /**
     * Reset state
     */
    fun resetCreateState() {
        _createAppointmentState.value = AppointmentUiState.Idle
    }

    fun resetUserAppointmentsState() {
        _userAppointmentsState.value = AppointmentUiState.Idle
    }
}
