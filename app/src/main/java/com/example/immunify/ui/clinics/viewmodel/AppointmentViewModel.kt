package com.example.immunify.ui.clinics.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.immunify.data.mapper.AppointmentMapper.toDomain
import com.example.immunify.data.model.AppointmentData
import com.example.immunify.domain.model.Appointment
import com.example.immunify.domain.usecase.CancelAppointmentUseCase
import com.example.immunify.domain.usecase.CreateAppointmentUseCase
import com.example.immunify.domain.usecase.GetUserAppointmentsUseCase
import com.example.immunify.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

/**
 * ViewModel untuk manage appointment operations
 */
@HiltViewModel
@RequiresApi(Build.VERSION_CODES.O)
class AppointmentViewModel @Inject constructor(
    private val createAppointmentUseCase: CreateAppointmentUseCase,
    private val getUserAppointmentsUseCase: GetUserAppointmentsUseCase,
    private val cancelAppointmentUseCase: CancelAppointmentUseCase,
    private val completeAppointmentUseCase: com.example.immunify.domain.usecase.CompleteAppointmentUseCase
) : ViewModel() {

    // State untuk create appointment
    private val _createAppointmentState =
        MutableStateFlow<AppointmentUiState>(AppointmentUiState.Idle)
    val createAppointmentState: StateFlow<AppointmentUiState> =
        _createAppointmentState.asStateFlow()

    // State untuk user appointments
    private val _userAppointmentsState =
        MutableStateFlow<AppointmentUiState>(AppointmentUiState.Idle)
    val userAppointmentsState: StateFlow<AppointmentUiState> = _userAppointmentsState.asStateFlow()

    // State untuk appointment calendar
    private val _currentMonth = MutableStateFlow(YearMonth.now())
    val currentMonth: StateFlow<YearMonth> = _currentMonth.asStateFlow()

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
     * Requires userId untuk nested collection structure
     */
    fun cancelAppointment(userId: String, appointmentId: String) {
        viewModelScope.launch {
            when (val result = cancelAppointmentUseCase(userId, appointmentId)) {
                is Result.Success -> {
                    _createAppointmentState.value = AppointmentUiState.Success(
                        message = "Appointment cancelled successfully"
                    )
                    // Refresh appointments after cancel
                    getUserAppointments(userId)
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
     * Complete appointment (mark as COMPLETED with vaccination details)
     */
    fun completeAppointment(
        userId: String,
        appointmentId: String,
        lotNumber: String = "",
        dose: String = "",
        administrator: String = ""
    ) {
        viewModelScope.launch {
            _createAppointmentState.value = AppointmentUiState.Loading
            
            when (val result = completeAppointmentUseCase(
                userId = userId,
                appointmentId = appointmentId,
                lotNumber = lotNumber,
                dose = dose,
                administrator = administrator
            )) {
                is Result.Success -> {
                    _createAppointmentState.value = AppointmentUiState.Success(
                        message = "Appointment completed successfully"
                    )
                    // Refresh appointments after complete
                    getUserAppointments(userId)
                }

                is Result.Error -> {
                    _createAppointmentState.value = AppointmentUiState.Error(
                        message = result.exception.message ?: "Failed to complete appointment"
                    )
                }

                is Result.Loading -> {
                    _createAppointmentState.value = AppointmentUiState.Loading
                }
            }
        }
    }

    /**
     * Ganti bulan kalender (+1)
     */
    fun nextMonth() {
        _currentMonth.value = _currentMonth.value.plusMonths(1)
    }

    /**
     * Ganti bulan kalender (-1)
     */
    fun prevMonth() {
        _currentMonth.value = _currentMonth.value.minusMonths(1)
    }

    /**
     * Set bulan & tahun tertentu
     */
    fun setMonth(month: Int, year: Int) {
        _currentMonth.value = YearMonth.of(year, month)
    }


    /**
     * Filter appointment berdasarkan tanggal tertentu (dot indicator)
     */
    fun appointmentsOnDate(
        appointments: List<Appointment>,
        date: LocalDate
    ): List<Appointment> {
        return appointments.filter {
            LocalDate.parse(it.date) == date
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
