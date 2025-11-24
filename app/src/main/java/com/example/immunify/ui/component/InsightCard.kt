package com.example.immunify.ui.component

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.immunify.R
import com.example.immunify.data.local.InsightSamples
import com.example.immunify.data.model.InsightData
import com.example.immunify.ui.theme.*

@SuppressLint("DefaultLocale")
@Composable
fun InsightCard(
    modifier: Modifier = Modifier,
    insight: InsightData,
    onClick: () -> Unit = {}
) {
    val shape = RoundedCornerShape(10.dp)

    Card(
        modifier = modifier
            .width(260.dp)
            .border(BorderStroke(1.dp, Grey30), shape)
            .clickable(onClick = onClick),
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = White10),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column {

            // Image
            AsyncImage(
                model = insight.imageUrl,
                contentDescription = insight.title,
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.image_the_first_ever),
                error = painterResource(R.drawable.image_the_first_ever),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
            )

            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                // Title
                Text(
                    text = insight.title,
                    style = MaterialTheme.typography.labelMedium.copy(color = Black100),
                    maxLines = 2,
                    minLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                // Description
                Text(
                    text = insight.description,
                    style = MaterialTheme.typography.bodySmall.copy(color = Grey90),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = insight.date,
                    style = MaterialTheme.typography.bodySmall.copy(color = Grey70, fontSize = 13.sp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewInsightCard() {
    InsightCard(insight = InsightSamples[0])
}

