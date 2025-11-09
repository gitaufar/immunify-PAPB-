package com.example.immunify.ui.viewmodel

import android.app.Application
import android.location.Location
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.immunify.data.model.LocationData
import com.example.immunify.data.model.LocationState
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.launch

class LocationViewModel(application: Application) : AndroidViewModel(application) {

    private val _locationState = mutableStateOf<LocationState>(LocationState.Idle)
    val locationState: State<LocationState> = _locationState

    fun fetchUserLocation(fusedLocationClient: FusedLocationProviderClient) {
        _locationState.value = LocationState.Loading
        viewModelScope.launch { getLastKnownLocation(fusedLocationClient) }
    }

    @Suppress("MissingPermission")
    private fun getLastKnownLocation(fusedLocationClient: FusedLocationProviderClient) {
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                _locationState.value = LocationState.Success(
                    LocationData(location.latitude, location.longitude)
                )
            } else {
                _locationState.value = LocationState.Error("Lokasi tidak ditemukan.")
            }
        }.addOnFailureListener {
            _locationState.value = LocationState.Error("Gagal mengambil lokasi: ${it.message}")
        }
    }
}
