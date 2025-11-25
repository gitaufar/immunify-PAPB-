package com.example.immunify.ui.profile

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.immunify.data.local.ChildSamples
import com.example.immunify.data.local.VaccineSamples
import com.example.immunify.data.model.CompletedEntry
import com.example.immunify.data.model.UpcomingEntry
import com.example.immunify.ui.component.AddProfileSheet
import com.example.immunify.ui.component.AppBar
import com.example.immunify.ui.component.ProfileHeader
import com.example.immunify.ui.component.SelectProfileSheet
import com.example.immunify.ui.component.UpcomingVaccineCard
import com.example.immunify.ui.theme.*
import com.example.immunify.util.getAvatarColorForChild
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileScreen() {
    val children = ChildSamples
    var selectedChild by remember { mutableStateOf(children.firstOrNull()) }   // <-- state
    val vaccines = VaccineSamples

    var showSelectProfileSheet by remember { mutableStateOf(false) }
    var showAddProfileSheet by remember { mutableStateOf(false) }

    val avatarColor = remember(selectedChild, children) {
        getAvatarColorForChild(selectedChild!!, children)
    }

    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Upcoming", "Completed")

    val upcomingList =
        vaccines
            .flatMap { vaccine ->
                vaccine.scheduledDates.map { dateString ->
                    UpcomingEntry(
                        vaccine = vaccine,
                        date = LocalDate.parse(dateString)
                    )
                }
            }
            .filter { it.date >= LocalDate.now() }
            .sortedBy { it.date }

    val completedList = remember(vaccines) {
        vaccines.flatMap { vaccine ->
            vaccine.completedDates.map { dateString ->
                val date = LocalDate.parse(dateString)
                CompletedEntry(
                    vaccine = vaccine,
                    date = date
                )
            }
        }.sortedByDescending { it.date }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White10)
    ) {
        AppBar(
            title = "Profile",
            onBackClick = null,
            onSettingsClick = { }
        )

        ProfileHeader(
            child = selectedChild!!,
            avatarColor = avatarColor,
            onClick = { showSelectProfileSheet = true }
        )

        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = White10,
            contentColor = PrimaryMain,
            divider = {},
            indicator = { tabPositions ->
                val indicatorLeft by animateDpAsState(
                    targetValue = tabPositions[selectedTab].left
                )
                val indicatorRight by animateDpAsState(
                    targetValue = tabPositions[selectedTab].right
                )

                Box(
                    Modifier
                        .wrapContentSize(Alignment.BottomStart)
                        .offset(x = indicatorLeft)
                        .width(indicatorRight - indicatorLeft)
                        .height(2.dp)
                        .background(PrimaryMain)
                )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                val isSelected = selectedTab == index
                val textColor by animateColorAsState(
                    targetValue = if (isSelected) Black100 else Grey60
                )

                Tab(
                    selected = isSelected,
                    onClick = { selectedTab = index },
                    text = {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.labelLarge.copy(color = textColor)
                        )
                    }
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            when (selectedTab) {
                0 -> { // UPCOMING
                    if (upcomingList.isEmpty()) {
                        item { EmptyState("No upcoming vaccinations scheduled.") }
                    } else {
                        items(upcomingList) { entry ->
                            UpcomingVaccineCard(
                                vaccine = entry.vaccine,
                                displayDate = entry.date,
                            )
                        }
                    }
                }

                1 -> { // COMPLETED
                    if (completedList.isEmpty()) {
                        item { EmptyState("No completed vaccinations found.") }
                    } else {
                        items(completedList) { entry ->
                            UpcomingVaccineCard(
                                vaccine = entry.vaccine,
                                displayDate = entry.date,
                                isCompleted = true
                            )
                        }
                    }
                }
            }
        }

        // Select profile sheet
        if (showSelectProfileSheet) {
            SelectProfileSheet(
                children = children,
                selectedChild = selectedChild,
                onDismiss = { showSelectProfileSheet = false },
                onSelect = {
                    selectedChild = it
                    showSelectProfileSheet = false
                },
                onAddNewProfile = {
                    showSelectProfileSheet = false      // tutup sheet sebelumnya
                    showAddProfileSheet = true          // buka AddProfileSheet
                }
            )
        }

        // Add profile sheet
        if (showAddProfileSheet) {
            AddProfileSheet(
                onDismiss = { showAddProfileSheet = false },
                onSuccess = { showAddProfileSheet = false }
            )
        }
    }
}

@Composable
private fun EmptyState(text: String) {
    Text(
        text = text,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodyMedium.copy(color = Grey70),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 80.dp)
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewProfileScreen() {
    ImmunifyTheme {
        ProfileScreen()
    }
}
