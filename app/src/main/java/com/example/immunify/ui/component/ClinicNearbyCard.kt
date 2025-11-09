package com.example.immunify.ui.component

import android.annotation.SuppressLint
import androidx.compose.foundation.background
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
import com.example.immunify.data.model.ClinicData
import com.example.immunify.ui.theme.*

@SuppressLint("DefaultLocale")
@Composable
fun ClinicNearbyCard(
    modifier: Modifier = Modifier,
    clinic: ClinicData,
    userLatitude: Double,
    userLongitude: Double
) {
    val shape = RoundedCornerShape(10.dp)

    // Hitung jarak
    val distanceKm = calculateDistanceKm(
        userLatitude,
        userLongitude,
        clinic.latitude,
        clinic.longitude
    )
    val formattedDistance = String.format("%.1f km", distanceKm)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(90.dp),
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = White10),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Load image dari URL dengan Coil
            AsyncImage(
                model = clinic.imageUrl,
                contentDescription = clinic.name,
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.image_hospital),
                error = painterResource(R.drawable.image_hospital),
                modifier = Modifier
                    .size(90.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .weight(1f),
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
                        modifier = Modifier.size(18.dp)
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
                            .background(Grey70)
                    )

                    Icon(
                        painter = painterResource(id = R.drawable.ic_rating),
                        contentDescription = null,
                        tint = Yellow,
                        modifier = Modifier.size(18.dp)
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