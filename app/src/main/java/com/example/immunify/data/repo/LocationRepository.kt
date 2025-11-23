package com.example.immunify.data.repo

import android.annotation.SuppressLint
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val fusedLocationClient: FusedLocationProviderClient
) {
    @SuppressLint("MissingPermission")
    suspend fun getUserLocation(): Pair<Double, Double>? {
        val location = fusedLocationClient.lastLocation.await() ?: return null
        return Pair(location.latitude, location.longitude)
    }
}
