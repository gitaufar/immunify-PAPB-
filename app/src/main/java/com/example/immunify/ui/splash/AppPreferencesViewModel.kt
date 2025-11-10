package com.example.immunify.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.immunify.data.local.AppPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppPreferencesViewModel @Inject constructor(
    private val appPreferences: AppPreferences
) : ViewModel() {

    // StateFlow for observing isFirstTime
    val isFirstTime: StateFlow<Boolean> = appPreferences.isFirstTime.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        true
    )

    fun setNotFirstTime() {
        viewModelScope.launch {
            appPreferences.setNotFirstTime()
        }
    }
}
