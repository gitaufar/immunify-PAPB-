package com.example.immunify.ui.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.immunify.domain.model.Child
import com.example.immunify.domain.usecase.CreateChildUseCase
import com.example.immunify.domain.usecase.GetUserChildrenUseCase
import com.example.immunify.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel untuk manage child operations
 */
@HiltViewModel
class ChildViewModel @Inject constructor(
    private val createChildUseCase: CreateChildUseCase,
    private val getUserChildrenUseCase: GetUserChildrenUseCase
) : ViewModel() {

    private val _createChildState = MutableStateFlow<ChildUiState>(ChildUiState.Idle)
    val createChildState: StateFlow<ChildUiState> = _createChildState.asStateFlow()

    private val _userChildrenState = MutableStateFlow<ChildUiState>(ChildUiState.Idle)
    val userChildrenState: StateFlow<ChildUiState> = _userChildrenState.asStateFlow()

    /**
     * Create new child profile
     */
    fun createChild(child: Child) {
        viewModelScope.launch {
            _createChildState.value = ChildUiState.Loading

            when (val result = createChildUseCase(child)) {
                is Result.Success -> {
                    _createChildState.value = ChildUiState.Success(
                        message = "Child profile created successfully"
                    )
                    // Auto refresh children list
                    getUserChildren(child.userId)
                }
                is Result.Error -> {
                    _createChildState.value = ChildUiState.Error(
                        message = result.exception.message ?: "Failed to create child profile"
                    )
                }
                is Result.Loading -> {
                    _createChildState.value = ChildUiState.Loading
                }
            }
        }
    }

    /**
     * Get user's children
     */
    fun getUserChildren(userId: String) {
        viewModelScope.launch {
            _userChildrenState.value = ChildUiState.Loading

            when (val result = getUserChildrenUseCase(userId)) {
                is Result.Success -> {
                    _userChildrenState.value = ChildUiState.ChildrenLoaded(
                        children = result.data
                    )
                }
                is Result.Error -> {
                    _userChildrenState.value = ChildUiState.Error(
                        message = result.exception.message ?: "Failed to load children"
                    )
                }
                is Result.Loading -> {
                    _userChildrenState.value = ChildUiState.Loading
                }
            }
        }
    }

    fun resetCreateState() {
        _createChildState.value = ChildUiState.Idle
    }

    fun resetChildrenState() {
        _userChildrenState.value = ChildUiState.Idle
    }
}
