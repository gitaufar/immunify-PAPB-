package com.example.immunify.ui.component

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.immunify.R
import com.example.immunify.core.LocalAppState
import com.example.immunify.data.model.ClinicData
import com.example.immunify.ui.theme.*
import kotlin.math.*

@SuppressLint("DefaultLocale")
@Composable
fun ClinicHomeCard(
    modifier: Modifier = Modifier,
    clinic: ClinicData,
    onClick: () -> Unit = {}
) {
    val appState = LocalAppState.current

    val distanceKm = calculateDistanceKm(
        appState.userLatitude,
        appState.userLongitude,
        clinic.latitude,
        clinic.longitude
    )
    val formattedDistance = String.format("%.1f km", distanceKm)

    val shape = RoundedCornerShape(8.dp)

    Card(
        modifier = modifier
            .width(250.dp)
            .border(1.dp, Grey30, shape)
            .clickable(onClick = onClick),
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = White10),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column {

            // Load Gambar dari URL menggunakan Coil
            AsyncImage(
                model = clinic.imageUrl,
                contentDescription = clinic.name,
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.image_hospital),
                error = painterResource(R.drawable.image_hospital),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
            )

            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = clinic.name,
                    style = MaterialTheme.typography.labelMedium.copy(color = Black100),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = clinic.address,
                    style = MaterialTheme.typography.bodySmall.copy(color = Grey70),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_distance),
                        contentDescription = null,
                        tint = Grey70,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "$formattedDistance away",
                        style = MaterialTheme.typography.bodySmall.copy(color = Grey90)
                    )

                    Box(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .size(4.dp)
                            .clip(RoundedCornerShape(50))
                            .background(Grey60)
                    )

                    Icon(
                        painter = painterResource(id = R.drawable.ic_rating),
                        contentDescription = null,
                        tint = Yellow,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = clinic.rating.toString(),
                        style = MaterialTheme.typography.bodySmall.copy(color = Grey90)
                    )
                }
            }
        }
    }
}

fun calculateDistanceKm(
    lat1: Double,
    lon1: Double,
    lat2: Double,
    lon2: Double
): Double {
    val earthRadius = 6371
    val dLat = Math.toRadians(lat2 - lat1)
    val dLon = Math.toRadians(lon2 - lon1)

    val a = sin(dLat / 2).pow(2.0) +
            cos(Math.toRadians(lat1)) *
            cos(Math.toRadians(lat2)) *
            sin(dLon / 2).pow(2.0)

    val c = 2 * atan2(sqrt(a), sqrt(1 - a))
    return (earthRadius * c)
}
