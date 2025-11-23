package com.example.immunify.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.immunify.R
import com.example.immunify.data.local.ClinicSamples
import com.example.immunify.data.local.UserSample
import com.example.immunify.data.model.AppointmentData
import com.example.immunify.data.model.ChildData
import com.example.immunify.data.model.Gender
import com.example.immunify.ui.theme.*

@Composable
fun AppointmentDropdown(appointment: AppointmentData) {

    var maleIndex = 0
    var femaleIndex = 0

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        appointment.vaccinants.forEachIndexed { globalIndex, child ->

            val color = when (child.gender) {
                Gender.MALE -> {
                    val c = getChildColor(Gender.MALE, maleIndex)
                    maleIndex++
                    c
                }

                Gender.FEMALE -> {
                    val c = getChildColor(Gender.FEMALE, femaleIndex)
                    femaleIndex++
                    c
                }
            }

            AppointmentDropdownItem(
                appointment = appointment,
                child = child,
                color = color,
                isLast = globalIndex == appointment.vaccinants.lastIndex
            )
        }
    }
}

@Composable
private fun AppointmentDropdownItem(
    appointment: AppointmentData,
    child: ChildData,
    color: Color,
    isLast: Boolean
) {
    var expanded by remember { mutableStateOf(false) }
    val rotation by animateFloatAsState(if (expanded) 180f else 0f, label = "")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(White10, RoundedCornerShape(8.dp))
            .border(1.dp, Grey30, RoundedCornerShape(8.dp))
    ) {
        // Header bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = appointment.vaccine.name,
                    style = MaterialTheme.typography.labelLarge.copy(color = Black100),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Row(verticalAlignment = Alignment.CenterVertically) {

                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .background(color, CircleShape)
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        text = child.name,
                        style = MaterialTheme.typography.bodySmall.copy(color = Grey90),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = appointment.timeSlot,
                    style = MaterialTheme.typography.bodySmall.copy(color = Black100)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    painter = painterResource(id = R.drawable.ic_down),
                    contentDescription = null,
                    tint = Grey80,
                    modifier = Modifier
                        .size(32.dp)
                        .rotate(rotation)
                )
            }
        }

        AnimatedVisibility(
            visible = expanded,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Divider(
                color = Grey30,
                thickness = 1.dp,
                modifier = Modifier.fillMaxWidth()
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Location",
                    style = MaterialTheme.typography.labelSmall.copy(color = Grey70)
                )
                Text(
                    text = appointment.clinic.name,
                    style = MaterialTheme.typography.labelMedium.copy(color = Black100)
                )

                Text(
                    text = "Dose",
                    style = MaterialTheme.typography.labelSmall.copy(color = Grey70)
                )
                Text(
                    text = "${appointment.vaccine.remainingDoses} left",
                    style = MaterialTheme.typography.labelMedium.copy(color = Black100)
                )
            }
        }

        if (!isLast) {
            Spacer(modifier = Modifier.height(1.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAppointmentDropdown() {
    val clinic = ClinicSamples.first()
    val user = UserSample
    val vaccine = clinic.availableVaccines.first()

    val appointment = AppointmentData(
        id = "appoint_001",
        parent = user,
        clinic = clinic,
        date = "2025-11-25",
        timeSlot = "13:00 - 14:30",
        vaccine = vaccine,
        vaccinants = user.children
    )

    ImmunifyTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            AppointmentDropdown(appointment = appointment)
        }
    }
}
