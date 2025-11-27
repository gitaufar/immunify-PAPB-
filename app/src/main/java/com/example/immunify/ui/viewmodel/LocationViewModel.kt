package com.example.immunify.ui.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.immunify.data.model.LocationState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.Tasks
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val fusedLocationClient: FusedLocationProviderClient
) : ViewModel() {

    private val _locationState = MutableStateFlow<LocationState>(LocationState.Idle)
    val locationState: StateFlow<LocationState> = _locationState

    @SuppressLint("MissingPermission") // Permission handled in MainActivity
    fun loadUserLocation() {
        _locationState.value = LocationState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val task = fusedLocationClient.lastLocation
                val location = Tasks.await(task)

                if (location != null) {
                    _locationState.value = LocationState.Success(location)
                } else {
                    _locationState.value = LocationState.Error("Location unavailable.")
                }
            } catch (e: Exception) {
                _locationState.value = LocationState.Error(e.message ?: "Location error.")
            }
        }
    }
}
