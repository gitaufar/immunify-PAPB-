package com.example.immunify.ui.component

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import androidx.core.content.ContextCompat
import com.example.immunify.R
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import android.graphics.*
import androidx.core.graphics.createBitmap
import androidx.core.graphics.drawable.toDrawable

// --- Fungsi pembuat icon lingkaran biru ---
private fun createCircleIcon(
    context: Context,
    color: Int,
    radius: Int = 18
): BitmapDrawable {
    val diameter = radius * 2
    val bitmap = createBitmap(diameter, diameter)
    val canvas = Canvas(bitmap)

    val paint = Paint().apply {
        isAntiAlias = true
        this.color = color
        style = Paint.Style.FILL
    }

    canvas.drawCircle(radius.toFloat(), radius.toFloat(), radius.toFloat(), paint)

    return bitmap.toDrawable(context.resources)
}

// --- User Marker Component ---
fun UserMarker(
    map: MapView,
    latitude: Double,
    longitude: Double,
    context: Context
) {
    val userIcon = createCircleIcon(
        context = context,
        color = ContextCompat.getColor(context, R.color.blue_500), // ganti dengan warna kamu
        radius = 18
    )

    val marker = Marker(map).apply {
        position = GeoPoint(latitude, longitude)
        title = "Your Location"
        icon = userIcon
        setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
    }

    map.overlays.add(marker)
}
