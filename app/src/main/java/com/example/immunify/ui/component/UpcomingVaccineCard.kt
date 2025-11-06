package com.example.immunify.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.immunify.R
import com.example.immunify.ui.theme.*

@Composable
fun UpcomingVaccineCard(
    modifier: Modifier = Modifier,
    vaccineName: String,
    dueIn: String,
    isWarning: Boolean = true,
) {
    val shape = RoundedCornerShape(8.dp)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .border(width = 1.dp, color = Grey30, shape = shape)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icon vaksin
        Image(
            painter = painterResource(id = R.drawable.ic_vaccine_color),
            contentDescription = null,
            modifier = Modifier
                .size(45.dp)
                .padding(end = 12.dp)
        )

        Column(modifier = Modifier.weight(1f)) {
            // Judul vaksin
            Text(
                text = vaccineName,
                style = MaterialTheme.typography.labelMedium,
                color = Black100
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Baris: "Next dose due in" + badge
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Next dose due in",
                    style = MaterialTheme.typography.bodySmall,
                    color = Grey70
                )
                Spacer(modifier = Modifier.width(6.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(if (isWarning) WarningSurface else PrimarySurface)
                        .border(
                            width = 1.dp,
                            color = if (isWarning) WarningBorder else PrimaryBorder,
                            shape = RoundedCornerShape(50)
                        )
                        .padding(horizontal = 10.dp, vertical = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = dueIn,
                        style = MaterialTheme.typography.labelSmall,
                        color = if (isWarning) WarningMain else PrimaryMain
                    )
                }
            }
        }

        // Icon panah (Next)
        Image(
            painter = painterResource(id = R.drawable.ic_next),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .padding(start = 8.dp),
            alignment = Alignment.CenterEnd
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun UpcomingVaccineCardPreview() {
    ImmunifyTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            UpcomingVaccineCard(modifier = Modifier, "HPV", "3 days", isWarning = true)
            UpcomingVaccineCard(modifier = Modifier, "Influenza", "5 days", isWarning = true)
            UpcomingVaccineCard(
                modifier = Modifier,
                "Varicella (Chicken Pox)",
                "6 months",
                isWarning = false
            )
        }
    }
}
