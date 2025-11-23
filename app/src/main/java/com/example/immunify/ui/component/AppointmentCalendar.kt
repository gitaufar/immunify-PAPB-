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
import com.example.immunify.ui.theme.Black100
import com.example.immunify.ui.theme.Grey80
import com.example.immunify.ui.theme.PrimaryMain
import com.example.immunify.ui.theme.getChildColor
import java.time.LocalDate
import java.time.YearMonth

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppointmentCalendar(
    appointments: List<AppointmentData>,
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
                                        app.vaccinants.forEachIndexed { i, child ->
                                            Box(
                                                modifier = Modifier
                                                    .size(8.dp)
                                                    .background(
                                                        getChildColor(child.gender, i),
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
    val parent = UserSample
    val clinic = ClinicSamples.first()
    val child1 = ChildSamples[0]
    val child2 = ChildSamples[1]
    val child3 = ChildSamples[2]
    val vaccine = RSSA_Vaccines.first()

    val today = LocalDate.now()
    val ym = YearMonth.now()

    val exampleAppointments = listOf(
        AppointmentData(
            id = "APT001",
            parent = parent,
            clinic = clinic,
            date = today.toString(),
            timeSlot = "09:00",
            vaccine = vaccine,
            vaccinants = listOf(child1),
            status = AppointmentStatus.PENDING
        ),
        AppointmentData(
            id = "APT002",
            parent = parent,
            clinic = clinic,
            date = ym.atDay(5).toString(),
            timeSlot = "10:00",
            vaccine = vaccine,
            vaccinants = listOf(child2),
            status = AppointmentStatus.PENDING
        ),
        AppointmentData(
            id = "APT003",
            parent = parent,
            clinic = clinic,
            date = ym.atDay(5).toString(),
            timeSlot = "14:00",
            vaccine = vaccine,
            vaccinants = listOf(child3),
            status = AppointmentStatus.PENDING
        ),
        AppointmentData(
            id = "APT004",
            parent = parent,
            clinic = clinic,
            date = ym.atDay(12).toString(),
            timeSlot = "08:30",
            vaccine = vaccine,
            vaccinants = listOf(child1, child2),
            status = AppointmentStatus.PENDING
        ),
        AppointmentData(
            id = "APT005",
            parent = parent,
            clinic = clinic,
            date = ym.atDay(20).toString(),
            timeSlot = "11:00",
            vaccine = vaccine,
            vaccinants = listOf(child3),
            status = AppointmentStatus.PENDING
        )
    )

    AppointmentCalendar(
        appointments = exampleAppointments,
        yearMonth = ym
    )
}