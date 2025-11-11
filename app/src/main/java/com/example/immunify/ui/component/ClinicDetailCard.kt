package com.example.immunify.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
fun ClinicDetailCard(
    type: ClinicDetailType,
    title: String,
    subtitle: String? = null,
    onClick: (() -> Unit)? = null
) {
    val (iconStart, iconEnd) = when (type) {
        ClinicDetailType.ADDRESS -> Pair(R.drawable.ic_location, R.drawable.ic_directions)
        ClinicDetailType.TIME -> Pair(R.drawable.ic_clock, R.drawable.ic_next)
        ClinicDetailType.WEBSITE -> Pair(R.drawable.ic_website, null)
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .border(width = 1.dp, color = Grey30, shape = RoundedCornerShape(8.dp))
            .clickable(enabled = onClick != null) { onClick?.invoke() },
        color = White10,
        tonalElevation = 1.dp,
        shadowElevation = 1.dp
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Kiri: icon dan teks
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    painter = painterResource(id = iconStart),
                    contentDescription = null,
                    tint = Grey90,
                    modifier = Modifier.size(24.dp)
                )

                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = if (type == ClinicDetailType.TIME && title.equals("Open", true)) PrimaryMain else Black100
                        )
                    )
                    if (!subtitle.isNullOrBlank()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = subtitle,
                            style = MaterialTheme.typography.bodySmall.copy(color = Grey70)
                        )
                    }
                }
            }

            // Kanan: optional icon
            if (iconEnd != null) {
                Icon(
                    painter = painterResource(id = iconEnd),
                    contentDescription = null,
                    tint = if (type == ClinicDetailType.ADDRESS) PrimaryMain else Grey60,
                    modifier = Modifier.size(26.dp)
                )
            }
        }
    }
}

enum class ClinicDetailType {
    ADDRESS, TIME, WEBSITE
}

@Preview(showBackground = true)
@Composable
fun PreviewClinicDetailCard() {
    ImmunifyTheme {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(16.dp)) {
            ClinicDetailCard(
                type = ClinicDetailType.ADDRESS,
                title = "Jl. Pulo Mas Bar. VI No.20",
                subtitle = "Kec. Pulo Gadung, DKI Jakarta"
            )
            ClinicDetailCard(
                type = ClinicDetailType.TIME,
                title = "Open",
                subtitle = "24 Hours"
            )
            ClinicDetailCard(
                type = ClinicDetailType.WEBSITE,
                title = "www.emc.id"
            )
        }
    }
}
