package com.example.immunify.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.immunify.R
import com.example.immunify.data.model.ClinicData
import com.example.immunify.data.model.ScheduleSection
import com.example.immunify.data.model.TimeSlot
import com.example.immunify.ui.theme.*

@Composable
fun ScheduleDropdown(
    clinic: ClinicData,
    onTimeSelected: (String) -> Unit
) {

    var selectedTime by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(White10, RoundedCornerShape(8.dp))
            .border(1.dp, Grey30, RoundedCornerShape(8.dp)),
    ) {

        val schedules = listOf(
            ScheduleSection(
                title = "Morning",
                icon = R.drawable.ic_morning,
                times = listOf(
                    TimeSlot("08:00 - 09:30"),
                    TimeSlot("10:00 - 11:30")
                )
            ),
            ScheduleSection(
                title = "Afternoon",
                icon = R.drawable.ic_afternoon,
                times = listOf(
                    TimeSlot("13:00 - 14:30"),
                    TimeSlot("15:00 - 16:30")
                )
            )
        )

        schedules.forEachIndexed { index, section ->
            ScheduleDropdownItem(
                section = section,
                isLast = index == schedules.lastIndex,
                selectedTime = selectedTime,
                onTimeSelected = { time ->
                    selectedTime = time
                    onTimeSelected(time)
                }
            )
        }
    }
}

@Composable
private fun ScheduleDropdownItem(
    section: ScheduleSection,
    isLast: Boolean,
    selectedTime: String,
    onTimeSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val rotation = if (expanded) 180f else 0f

    Column(modifier = Modifier.fillMaxWidth()) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    painter = painterResource(id = section.icon),
                    contentDescription = null,
                    tint = Grey90,
                    modifier = Modifier.size(20.dp)
                )

                Text(
                    text = section.title,
                    style = MaterialTheme.typography.labelMedium.copy(color = Black100)
                )
            }

            Icon(
                painter = painterResource(id = R.drawable.ic_down),
                contentDescription = null,
                tint = Grey60,
                modifier = Modifier
                    .size(28.dp)
                    .rotate(rotation)
            )
        }

        AnimatedVisibility(
            visible = expanded,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 4.dp, end = 16.dp, bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                section.times.forEach { slot ->
                    TimeSlotButton(
                        slot = slot,
                        isSelected = (selectedTime == slot.time),
                        onSelect = { time ->
                            onTimeSelected(time)
                        }
                    )
                }
            }
        }
    }

    if (!isLast) {
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = Grey30
        )
    }
}

@Composable
private fun TimeSlotButton(
    slot: TimeSlot,
    isSelected: Boolean,
    onSelect: (String) -> Unit
) {

    val bg = when {
        slot.isBooked -> Grey30
        isSelected -> PrimarySurface
        else -> Transparent
    }

    val borderColor = when {
        slot.isBooked -> Transparent
        isSelected -> PrimaryBorder
        else -> Grey30
    }

    val textColor = if (slot.isBooked) Grey60 else Black100

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(bg)
            .border(1.dp, borderColor, RoundedCornerShape(6.dp))
            .clickable(enabled = !slot.isBooked) {
                onSelect(slot.time)
            }
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(
            text = slot.time,
            style = MaterialTheme.typography.bodySmall.copy(color = textColor)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewScheduleDropdown() {
    ImmunifyTheme {
        val sampleClinic = ClinicData(
            id = "1",
            name = "RS EMC Pulomas",
            imageUrl = "",
            address = "Jl. Pulo Mas Bar. VI No.20",
            district = "Kec. Pulo Gadung",
            city = "DKI Jakarta",
            latitude = -6.188,
            longitude = 106.88,
            rating = 4.9,
            website = "www.emc.id",
            openingHours = "24 Hours",
            availableVaccines = emptyList()
        )

        Column(Modifier.padding(16.dp)) {
            ScheduleDropdown(clinic = sampleClinic, onTimeSelected = {})
        }
    }
}
