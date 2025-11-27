package com.example.immunify.data.model

import android.location.Location

sealed class LocationState {
    object Idle : LocationState()
    object Loading : LocationState()
    data class Success(val location: Location) : LocationState()
    data class Error(val message: String) : LocationState()
}
