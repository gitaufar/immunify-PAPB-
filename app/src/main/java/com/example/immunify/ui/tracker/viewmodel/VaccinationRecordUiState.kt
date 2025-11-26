package com.example.immunify.ui.tracker.viewmodel

import com.example.immunify.domain.model.VaccinationRecord

/**
 * UI State untuk Vaccination Record operations
 */
sealed class VaccinationRecordUiState {
    object Idle : VaccinationRecordUiState()
    object Loading : VaccinationRecordUiState()
    data class Success(val message: String) : VaccinationRecordUiState()
    data class Error(val message: String) : VaccinationRecordUiState()
    data class RecordsLoaded(val records: List<VaccinationRecord>) : VaccinationRecordUiState()
}
