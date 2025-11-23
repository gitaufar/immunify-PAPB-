package com.example.immunify.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.immunify.data.model.LocationData
import com.example.immunify.data.model.LocationState
import com.example.immunify.data.repo.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val repository: LocationRepository
) : ViewModel() {

    private val _locationState = mutableStateOf<LocationState>(LocationState.Idle)
    val locationState: State<LocationState> = _locationState

    fun loadUserLocation() {
        if (_locationState.value is LocationState.Loading) return
        _locationState.value = LocationState.Loading

        viewModelScope.launch {
            try {
                val location = repository.getUserLocation()
                _locationState.value = if (location != null) {
                    LocationState.Success(LocationData(location.first, location.second))
                } else LocationState.Error("Lokasi tidak ditemukan.")
            } catch (e: Exception) {
                _locationState.value = LocationState.Error("Error: ${e.message}")
            }
        }
    }
}
