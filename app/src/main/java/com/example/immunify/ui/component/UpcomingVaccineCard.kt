package com.example.immunify.ui.component

import android.os.Build
import androidx.annotation.RequiresApi
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
import com.example.immunify.data.model.VaccineData
import com.example.immunify.domain.model.Appointment
import com.example.immunify.ui.theme.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlin.math.abs
import kotlin.math.ceil

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UpcomingVaccineCard(
    modifier: Modifier = Modifier,
    vaccine: VaccineData,
    displayDate: LocalDate? = null,
    isCompleted: Boolean = false
) {
    val shape = RoundedCornerShape(8.dp)

    // Gunakan displayDate jika diberikan, jika tidak pakai jadwal pertama
    val effectiveDate: LocalDate? =
        displayDate ?: vaccine.scheduledDates.firstOrNull()?.let { LocalDate.parse(it) }

    val dueDays = effectiveDate?.let { date ->
        ChronoUnit.DAYS.between(LocalDate.now(), date).toInt()
    }

    val dueIn = when {
        isCompleted && effectiveDate != null -> {
            effectiveDate.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"))
        }

        dueDays == null -> "-"
        dueDays < 0 -> {
            val lateDays = abs(dueDays)
            when {
                lateDays == 1 -> "Overdue by 1 day"
                lateDays < 7 -> "Overdue by $lateDays days"
                lateDays in 7..30 -> "Overdue by ${ceil(lateDays / 7.0).toInt()} weeks"
                else -> "Overdue by ${lateDays / 30} months"
            }
        }

        dueDays < 1 -> "Today"
        dueDays == 1 -> "1 day"
        dueDays in 2..7 -> "$dueDays days"
        dueDays in 8..31 -> "${ceil(dueDays / 7.0).toInt()} weeks"
        else -> "${dueDays / 30} months"
    }

    val isWarning = !isCompleted && dueDays != null && dueDays < 7

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .border(width = 1.dp, color = Grey30, shape = shape)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(
                id = if (isCompleted) R.drawable.ic_certificate else R.drawable.ic_vaccine_color
            ),
            contentDescription = null,
            modifier = Modifier
                .size(45.dp)
                .padding(end = 12.dp)
        )

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = vaccine.name,
                style = MaterialTheme.typography.labelMedium,
                color = Black100
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = if (isCompleted) "Completed in" else "Next dose due in",
                    style = MaterialTheme.typography.bodySmall,
                    color = Grey70
                )

                Spacer(modifier = Modifier.width(8.dp))

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(if (isWarning) WarningSurface else PrimarySurface)
                        .border(
                            width = 1.dp,
                            color = if (isWarning) WarningBorder else PrimaryBorder,
                            shape = RoundedCornerShape(50)
                        )
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = dueIn,
                        style = MaterialTheme.typography.bodySmall,
                        color = if (isWarning) WarningMain else PrimaryMain
                    )
                }
            }
        }

        Image(
            painter = painterResource(id = R.drawable.ic_next),
            contentDescription = null,
            modifier = Modifier
                .size(32.dp)
                .padding(start = 8.dp),
            alignment = Alignment.CenterEnd
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun calculateDaysLeft(dateString: String): Int {
    return try {
        val targetDate = LocalDate.parse(dateString)
        val today = LocalDate.now()
        ChronoUnit.DAYS.between(today, targetDate).toInt()
    } catch (e: Exception) {
        -1
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatDateReadable(dateString: String): String {
    return try {
        val date = LocalDate.parse(dateString)
        val formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy")
        date.format(formatter)
    } catch (e: Exception) {
        "-"
    }
}

/**
 * Overload untuk UpcomingVaccineCard yang menerima Appointment dari Firestore
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UpcomingVaccineCardFromAppointment(
    modifier: Modifier = Modifier,
    appointment: Appointment
) {
    val shape = RoundedCornerShape(8.dp)
    val appointmentDate = try {
        LocalDate.parse(appointment.date)
    } catch (e: Exception) {
        LocalDate.now()
    }
    
    val dueDays = ChronoUnit.DAYS.between(LocalDate.now(), appointmentDate).toInt()
    
    val dueIn = when {
        dueDays < 0 -> {
            val lateDays = abs(dueDays)
            when {
                lateDays == 1 -> "Overdue by 1 day"
                lateDays < 7 -> "Overdue by $lateDays days"
                lateDays in 7..30 -> "Overdue by ${ceil(lateDays / 7.0).toInt()} weeks"
                else -> "Overdue by ${lateDays / 30} months"
            }
        }
        dueDays < 1 -> "Today"
        dueDays == 1 -> "1 day"
        dueDays in 2..7 -> "$dueDays days"
        dueDays in 8..31 -> "${ceil(dueDays / 7.0).toInt()} weeks"
        else -> "${dueDays / 30} months"
    }
    
    val isWarning = dueDays < 7

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .border(width = 1.dp, color = Grey30, shape = shape)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_vaccine_color),
            contentDescription = null,
            modifier = Modifier
                .size(45.dp)
                .padding(end = 12.dp)
        )

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = appointment.vaccineName,
                style = MaterialTheme.typography.labelMedium,
                color = Black100
            )

            Spacer(modifier = Modifier.height(4.dp))
            
            if (appointment.vaccinantNames.isNotEmpty()) {
                Text(
                    text = appointment.vaccinantNames.joinToString(", "),
                    style = MaterialTheme.typography.bodySmall,
                    color = Grey60
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Due in",
                    style = MaterialTheme.typography.bodySmall,
                    color = Grey70
                )

                Spacer(modifier = Modifier.width(8.dp))

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(if (isWarning) WarningSurface else PrimarySurface)
                        .border(
                            width = 1.dp,
                            color = if (isWarning) WarningBorder else PrimaryBorder,
                            shape = RoundedCornerShape(50)
                        )
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = dueIn,
                        style = MaterialTheme.typography.bodySmall,
                        color = if (isWarning) WarningMain else PrimaryMain
                    )
                }
            }
        }

        Image(
            painter = painterResource(id = R.drawable.ic_next),
            contentDescription = null,
            modifier = Modifier
                .size(32.dp)
                .padding(start = 8.dp),
            alignment = Alignment.CenterEnd
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun UpcomingVaccineCardPreview() {
    ImmunifyTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val sampleUpcoming = VaccineData(
                id = "1",
                name = "HPV",
                scheduledDates = listOf("2025-12-07")
            )

            val sampleCompleted = VaccineData(
                id = "2",
                name = "MMR",
                scheduledDates = listOf("2025-05-10")
            )

            UpcomingVaccineCard(vaccine = sampleUpcoming, isCompleted = false)
            UpcomingVaccineCard(vaccine = sampleCompleted, isCompleted = true)
        }
    }
}
