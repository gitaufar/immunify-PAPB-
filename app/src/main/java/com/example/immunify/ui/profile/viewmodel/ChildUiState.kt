package com.example.immunify.ui.profile.viewmodel

import com.example.immunify.domain.model.Child

/**
 * UI State untuk Child operations
 */
sealed class ChildUiState {
    object Idle : ChildUiState()
    object Loading : ChildUiState()
    data class Success(val message: String) : ChildUiState()
    data class Error(val message: String) : ChildUiState()
    data class ChildrenLoaded(val children: List<Child>) : ChildUiState()
}
