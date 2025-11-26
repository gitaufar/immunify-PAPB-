package com.example.immunify.ui.tracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.immunify.domain.model.VaccinationRecord
import com.example.immunify.domain.usecase.CreateVaccinationRecordUseCase
import com.example.immunify.domain.usecase.GetUserVaccinationRecordsUseCase
import com.example.immunify.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel untuk manage vaccination record operations
 */
@HiltViewModel
class VaccinationRecordViewModel @Inject constructor(
    private val createVaccinationRecordUseCase: CreateVaccinationRecordUseCase,
    private val getUserVaccinationRecordsUseCase: GetUserVaccinationRecordsUseCase
) : ViewModel() {

    private val _createRecordState = MutableStateFlow<VaccinationRecordUiState>(VaccinationRecordUiState.Idle)
    val createRecordState: StateFlow<VaccinationRecordUiState> = _createRecordState.asStateFlow()

    private val _userRecordsState = MutableStateFlow<VaccinationRecordUiState>(VaccinationRecordUiState.Idle)
    val userRecordsState: StateFlow<VaccinationRecordUiState> = _userRecordsState.asStateFlow()

    /**
     * Create new vaccination record
     */
    fun createVaccinationRecord(record: VaccinationRecord) {
        viewModelScope.launch {
            _createRecordState.value = VaccinationRecordUiState.Loading

            when (val result = createVaccinationRecordUseCase(record)) {
                is Result.Success -> {
                    _createRecordState.value = VaccinationRecordUiState.Success(
                        message = "Vaccination record created successfully"
                    )
                    // Auto refresh records list
                    getUserVaccinationRecords(record.userId)
                }
                is Result.Error -> {
                    _createRecordState.value = VaccinationRecordUiState.Error(
                        message = result.exception.message ?: "Failed to create vaccination record"
                    )
                }
                is Result.Loading -> {
                    _createRecordState.value = VaccinationRecordUiState.Loading
                }
            }
        }
    }

    /**
     * Get user's vaccination records
     */
    fun getUserVaccinationRecords(userId: String) {
        viewModelScope.launch {
            _userRecordsState.value = VaccinationRecordUiState.Loading

            when (val result = getUserVaccinationRecordsUseCase(userId)) {
                is Result.Success -> {
                    _userRecordsState.value = VaccinationRecordUiState.RecordsLoaded(
                        records = result.data
                    )
                }
                is Result.Error -> {
                    _userRecordsState.value = VaccinationRecordUiState.Error(
                        message = result.exception.message ?: "Failed to load vaccination records"
                    )
                }
                is Result.Loading -> {
                    _userRecordsState.value = VaccinationRecordUiState.Loading
                }
            }
        }
    }

    fun resetCreateState() {
        _createRecordState.value = VaccinationRecordUiState.Idle
    }

    fun resetRecordsState() {
        _userRecordsState.value = VaccinationRecordUiState.Idle
    }
}
