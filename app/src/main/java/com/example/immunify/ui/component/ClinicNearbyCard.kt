package com.example.immunify.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.immunify.R
import com.example.immunify.ui.theme.*

@Composable
fun ClinicNearbyCard(
    modifier: Modifier = Modifier,
    hospitalName: String,
    address: String,
    distance: String,
    rating: Double,
    imageRes: Int
) {
    val shape = RoundedCornerShape(10.dp)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .border(1.dp, Grey30, shape),
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = White10),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Gambar di kiri
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = hospitalName,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            // Teks di kanan
            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = hospitalName,
                    style = MaterialTheme.typography.bodyMedium.copy(color = Black100),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = address,
                    style = MaterialTheme.typography.bodySmall.copy(color = Grey70),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                // Baris bawah
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_distance),
                        contentDescription = null,
                        tint = Grey70,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "$distance away",
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
                        text = rating.toString(),
                        style = MaterialTheme.typography.bodySmall.copy(color = Grey90)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
fun PreviewClinicNearbyCard() {
    ImmunifyTheme {
        ClinicNearbyCard(
            hospitalName = "RS EMC Pulomas",
            address = "Jl. Pulo Mas Bar. VI No.20, Kec. Pulo Gadung",
            distance = "2 km",
            rating = 4.9,
            imageRes = R.drawable.image_hospital
        )
    }
}
