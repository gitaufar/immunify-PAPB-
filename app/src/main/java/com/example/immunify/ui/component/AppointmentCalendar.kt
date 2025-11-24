package com.example.immunify.ui.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.immunify.data.local.ChildSamples
import com.example.immunify.data.local.ClinicSamples
import com.example.immunify.data.local.RSSA_Vaccines
import com.example.immunify.data.local.UserSample
import com.example.immunify.data.model.AppointmentData
import com.example.immunify.data.model.AppointmentStatus
import com.example.immunify.data.model.Gender
import com.example.immunify.ui.theme.Black100
import com.example.immunify.ui.theme.Grey80
import com.example.immunify.ui.theme.PrimaryMain
import com.example.immunify.ui.theme.getChildColor
import java.time.LocalDate
import java.time.YearMonth

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppointmentCalendar(
    appointments: List<com.example.immunify.domain.model.Appointment>,
    yearMonth: YearMonth
) {
    val today = LocalDate.now()

    val daysInMonth = yearMonth.lengthOfMonth()
    val firstDayOfWeek = yearMonth.atDay(1).dayOfWeek.value % 7

    // Hitung jumlah baris yang diperlukan
    val totalCells = firstDayOfWeek + daysInMonth
    val rowCount = kotlin.math.ceil(totalCells / 7f).toInt()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 0.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {

        // Header nama hari
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat").forEach {
                Text(
                    text = it,
                    style = MaterialTheme.typography.labelMedium.copy(color = Black100),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Grid kalender
        Column(Modifier.fillMaxWidth()) {

            var dayCounter = 1

            for (week in 0 until rowCount) {
                Row(Modifier.fillMaxWidth()) {

                    for (dow in 0..6) {

                        val dateNumber =
                            if (week == 0 && dow < firstDayOfWeek) null
                            else if (dayCounter > daysInMonth) null
                            else dayCounter

                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .heightIn(min = 36.dp)
                                .padding(vertical = 4.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            if (dateNumber != null) {

                                val date =
                                    LocalDate.of(yearMonth.year, yearMonth.month, dateNumber)

                                val dailyAppointments = appointments.filter {
                                    LocalDate.parse(it.date) == date
                                }

                                val isToday = date == today

                                // Nomor tanggal
                                Text(
                                    text = dateNumber.toString(),
                                    style = if (isToday)
                                        MaterialTheme.typography.labelLarge.copy(color = PrimaryMain)
                                    else
                                        MaterialTheme.typography.bodyMedium.copy(color = Grey80)
                                )

                                // Dot warna anak
                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    dailyAppointments.forEach { app ->
                                        app.vaccinantNames.forEachIndexed { i, _ ->
                                            Box(
                                                modifier = Modifier
                                                    .size(8.dp)
                                                    .background(
                                                        // Gunakan warna default jika tidak ada gender info
                                                        getChildColor(Gender.MALE, i),
                                                        CircleShape
                                                    )
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        if (dateNumber != null) dayCounter++
                    }
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewAppointmentCalendar() {
    val today = LocalDate.now()
    val ym = YearMonth.now()

    val exampleAppointments = listOf(
        com.example.immunify.domain.model.Appointment(
            id = "APT001",
            userId = "user001",
            userName = "John Doe",
            userPhone = "08123456789",
            clinicId = "clinic001",
            clinicName = "RS EMC Pulomas",
            clinicAddress = "Jl. Pulo Mas",
            date = today.toString(),
            timeSlot = "09:00",
            vaccineId = "v001",
            vaccineName = "HPV Vaccine",
            vaccinantIds = listOf("child001"),
            vaccinantNames = listOf("Alice"),
            status = AppointmentStatus.PENDING
        ),
        com.example.immunify.domain.model.Appointment(
            id = "APT002",
            userId = "user001",
            userName = "John Doe",
            userPhone = "08123456789",
            clinicId = "clinic001",
            clinicName = "RS EMC Pulomas",
            clinicAddress = "Jl. Pulo Mas",
            date = ym.atDay(5).toString(),
            timeSlot = "10:00",
            vaccineId = "v001",
            vaccineName = "HPV Vaccine",
            vaccinantIds = listOf("child002"),
            vaccinantNames = listOf("Bob"),
            status = AppointmentStatus.PENDING
        ),
        com.example.immunify.domain.model.Appointment(
            id = "APT003",
            userId = "user001",
            userName = "John Doe",
            userPhone = "08123456789",
            clinicId = "clinic001",
            clinicName = "RS EMC Pulomas",
            clinicAddress = "Jl. Pulo Mas",
            date = ym.atDay(12).toString(),
            timeSlot = "14:00",
            vaccineId = "v002",
            vaccineName = "Hepatitis B",
            vaccinantIds = listOf("child001", "child002"),
            vaccinantNames = listOf("Alice", "Bob"),
            status = AppointmentStatus.PENDING
        )
    )

    AppointmentCalendar(
        appointments = exampleAppointments,
        yearMonth = ym
    )
}