package com.example.immunify.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.immunify.R
import com.example.immunify.ui.theme.*

@Composable
fun DiseaseCard(
    modifier: Modifier = Modifier,
    imageRes: Int,
    diseaseName: String
) {
    val shape = RoundedCornerShape(6.dp)

    Card(
        modifier = modifier
            .width(170.dp)
            .height(230.dp)
            .clip(shape),
        shape = shape,
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = White10)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Gambar penyakit
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = diseaseName,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Gradasi dari bawah ke atas
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                PrimaryMain.copy(alpha = 0.7f),
                                PrimaryMain.copy(alpha = 0.0f)
                            ),
                            startY = 600f,
                            endY = 0f
                        )
                    )
            )

            // Teks nama penyakit
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(12.dp)
            ) {
                Text(
                    text = diseaseName,
                    style = MaterialTheme.typography.bodyMedium.copy(color = White10)
                )
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
fun PreviewDiseaseCard() {
    ImmunifyTheme {
        DiseaseCard(
            imageRes = R.drawable.image_hpv,
            diseaseName = "HPV"
        )
    }
}
