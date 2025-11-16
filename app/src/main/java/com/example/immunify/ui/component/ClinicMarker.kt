package com.example.immunify.ui.component

import android.content.Context
import androidx.core.content.ContextCompat
import com.example.immunify.R
import com.example.immunify.data.model.ClinicData
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

fun ClinicMarker(
    map: MapView,
    clinic: ClinicData,
    context: Context,
    onClick: (ClinicData) -> Unit
) {
    val point = GeoPoint(clinic.latitude, clinic.longitude)

    val marker = Marker(map).apply {
        position = point
        title = clinic.name
        snippet = clinic.address
        setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        icon = ContextCompat.getDrawable(context, R.drawable.ic_clinic_marker)

        setOnMarkerClickListener { _, _ ->
            onClick(clinic)
            true
        }
    }

    map.overlays.add(marker)
}
