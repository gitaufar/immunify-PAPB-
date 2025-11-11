package com.example.immunify.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.immunify.R
import com.example.immunify.data.model.ClinicData
import com.example.immunify.data.model.VaccineData
import com.example.immunify.ui.theme.*

@Composable
fun VaccineDropdown(clinic: ClinicData) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(White10, RoundedCornerShape(8.dp))
            .border(width = 1.dp, color = Grey30, shape = RoundedCornerShape(8.dp)),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        clinic.availableVaccines.forEachIndexed { index, vaccine ->
            VaccineDropdownItem(
                vaccine = vaccine,
                isLast = index == clinic.availableVaccines.lastIndex
            )
        }
    }
}

@Composable
private fun VaccineDropdownItem(vaccine: VaccineData, isLast: Boolean) {
    var expanded by remember { mutableStateOf(false) }
    val rotation by animateFloatAsState(if (expanded) 180f else 0f, label = "")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        // Header bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = vaccine.name,
                style = MaterialTheme.typography.labelMedium.copy(color = Black100)
            )

            Icon(
                painter = painterResource(id = R.drawable.ic_down),
                contentDescription = null,
                tint = Grey60,
                modifier = Modifier
                    .size(28.dp)
                    .rotate(rotation)
            )
        }

        // Expandable content
        AnimatedVisibility(
            visible = expanded,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Description",
                    style = MaterialTheme.typography.labelSmall.copy(color = Grey90)
                )

                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    vaccine.description.forEach { item ->
                        Text(
                            text = "• $item",
                            style = MaterialTheme.typography.bodySmall.copy(color = Black100)
                        )
                    }
                }

                if (vaccine.brand.isNotEmpty()) {
                    Text(
                        text = "Brand",
                        style = MaterialTheme.typography.labelSmall.copy(color = Grey90)
                    )
                    Text(
                        text = vaccine.brand.joinToString(", "),
                        style = MaterialTheme.typography.bodySmall.copy(color = Black100)
                    )
                }
            }
        }
    }

    // Divider tetap di luar padding supaya full width
    if (!isLast) {
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = Grey30
        )
    }
}

@Preview(showBackground = false)
@Composable
fun VaccineDropdownPreview() {
    val mockClinic = ClinicData(
        id = "1",
        name = "RS EMC Pulomas",
        imageUrl = "",
        address = "",
        district = "",
        city = "",
        latitude = 0.0,
        longitude = 0.0,
        rating = 4.5,
        website = null,
        openingHours = null,
        availableVaccines = listOf(
            VaccineData(
                id = "hpv",
                name = "HPV vaccine",
                description = listOf(
                    "HPV vaccine protects against the sexually transmitted human papillomavirus.",
                    "Recommended for both genders, typically at ages 11–12 or as early as 9.",
                    "Administered in a series of 2–3 doses.",
                    "Highly effective in preventing certain cancers and genital warts.",
                    "Generally safe with mild side effects such as pain, redness, or swelling at the injection site."
                ),
                brand = listOf("Gardasil", "Cervarix")
            ),
            VaccineData(
                id = "hepb",
                name = "Hepatitis B vaccine"
            ),
            VaccineData(
                id = "varicella",
                name = "Varicella vaccine"
            )
        )
    )

    ImmunifyTheme {
        VaccineDropdown(clinic = mockClinic)
    }
}
