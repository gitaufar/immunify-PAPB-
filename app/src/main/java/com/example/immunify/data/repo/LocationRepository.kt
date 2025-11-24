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
        // Coba ambil cached lokasi
        val lastLocation = fusedLocationClient.lastLocation.await()
        if (lastLocation != null) {
            return Pair(lastLocation.latitude, lastLocation.longitude)
        }

        // Kalau null, pakai request realtime
        val currentLocation = fusedLocationClient.getCurrentLocation(
            com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY,
            null
        ).await()

        return currentLocation?.let {
            Pair(it.latitude, it.longitude)
        }
    }
}
