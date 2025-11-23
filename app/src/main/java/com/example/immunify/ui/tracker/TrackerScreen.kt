package com.example.immunify.ui.tracker

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.immunify.data.local.AppointmentSamples
import com.example.immunify.ui.component.AppBar
import com.example.immunify.ui.component.AppointmentCalendar
import com.example.immunify.ui.component.AppointmentDropdown
import com.example.immunify.ui.component.SectionHeader
import com.example.immunify.ui.component.YearMonthSelectionSheet
import com.example.immunify.ui.theme.ImmunifyTheme
import com.example.immunify.ui.theme.PrimaryMain
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackerScreen(
    modifier: Modifier = Modifier,
    yearMonth: YearMonth,
    onNextMonthClick: (YearMonth) -> Unit = {}
) {
    var showSheet by remember { mutableStateOf(false) }
    var selectedYM by remember { mutableStateOf(yearMonth) }

    Column(modifier = modifier.fillMaxSize()) {

        // AppBar Tracker
        AppBar(
            title = "",
            currentYM = selectedYM,
            onBackClick = {},
            isOnTracker = true,
            onNextClick = { showSheet = true },
            onCalendarClick = {},
            onAddClick = {},
        )

        // Konten Scrollable
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {

            // Calendar
            item {
                AppointmentCalendar(
                    appointments = AppointmentSamples,
                    yearMonth = selectedYM
                )
            }

            // Section header
            item {
                SectionHeader(title = "Appointments", onFilterClick = {})
            }

            // Group appointments by date
            val grouped = AppointmentSamples
                .groupBy { LocalDate.parse(it.date) }
                .toSortedMap()

            grouped.forEach { (date, listForDate) ->

                // Tanggal header warna PrimaryMain
                item {
                    Text(
                        text = date.format(
                            DateTimeFormatter.ofPattern("d MMMM", Locale.getDefault())
                        ),
                        color = PrimaryMain,
                        style = androidx.compose.material3.MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                // AppointmentDropdown per appointment
                items(listForDate) { appointment ->
                    AppointmentDropdown(appointment)
                }
            }
        }
    }

    // Bottom Sheet Year-Month Selection
    if (showSheet) {
        YearMonthSelectionSheet(
            onSelect = { ym ->
                selectedYM = ym
                onNextMonthClick(ym)
            },
            onDismiss = { showSheet = false }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewTrackerScreen() {
    ImmunifyTheme {
        TrackerScreen(
            yearMonth = YearMonth.now(),
            onNextMonthClick = {}
        )
    }
}
