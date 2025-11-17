package com.example.immunify.ui.component

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.immunify.R
import com.example.immunify.data.model.ClinicData
import com.example.immunify.ui.theme.*

@SuppressLint("DefaultLocale")
@Composable
fun ClinicDetailHeader(
    clinic: ClinicData,
    userLatitude: Double,
    userLongitude: Double
) {
    val distanceKm = calculateDistanceKm(
        userLatitude,
        userLongitude,
        clinic.latitude,
        clinic.longitude
    )
    val formattedDistance = String.format("%.1f km", distanceKm)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(White10)
    ) {
        // Gambar header full width dengan shadow lembut di bawahnya
        AsyncImage(
            model = clinic.imageUrl,
            contentDescription = clinic.name,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.image_hospital),
            error = painterResource(R.drawable.image_hospital),
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .drawWithContent {
                    drawContent()
                    // Shadow halus bawah gambar agar teks tampak menonjol
                    drawRoundRect(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Black.copy(alpha = 0.1f),
                                Color.Transparent
                            ),
                            startY = size.height - 50f,
                            endY = size.height
                        ),
                        cornerRadius = CornerRadius(0f, 0f),
                        blendMode = BlendMode.Multiply
                    )
                }
        )

        // Informasi Klinik
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = clinic.name,
                style = MaterialTheme.typography.titleMedium.copy(color = Black100),
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
                        .background(Grey60, RoundedCornerShape(50))
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

@Preview(showBackground = true)
@Composable
fun ClinicDetailHeaderPreview() {
    ImmunifyTheme {
        ClinicDetailHeader(
            clinic = ClinicData(
                id = "emc_pulomas",
                name = "RS EMC Pulomas",
                imageUrl = "https://emc.id/storage/files/shares/foto%20rs/emc-pulomas.jpg",
                address = "Jl. Pulomas Barat VI No.20, Kayu Putih, Jakarta Timur",
                district = "Kayu Putih",
                city = "Jakarta Timur",
                latitude = -6.1849,
                longitude = 106.8821,
                rating = 4.9,
                website = "https://emc.id",
                openingHours = "24 Hours",
                availableVaccines = emptyList(),
                contact = "08123456890"
            ),
            userLatitude = -6.2,
            userLongitude = 106.8
        )
    }
}
