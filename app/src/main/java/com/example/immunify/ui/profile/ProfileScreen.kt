package com.example.immunify.ui.profile

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.immunify.data.model.AppointmentStatus
import com.example.immunify.data.model.ChildData
import com.example.immunify.data.model.Gender
import com.example.immunify.ui.auth.AuthViewModel
import com.example.immunify.ui.clinics.viewmodel.AppointmentUiState
import com.example.immunify.ui.clinics.viewmodel.AppointmentViewModel
import com.example.immunify.ui.component.AddProfileSheet
import com.example.immunify.ui.component.AppBar
import com.example.immunify.ui.component.EmptyState
import com.example.immunify.ui.component.ProfileHeader
import com.example.immunify.ui.component.SelectProfileSheet
import com.example.immunify.ui.component.UpcomingVaccineCardFromAppointment
import com.example.immunify.ui.profile.viewmodel.ChildUiState
import com.example.immunify.ui.profile.viewmodel.ChildViewModel
import com.example.immunify.ui.theme.*
import com.example.immunify.util.getAvatarColorForChild
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
    childViewModel: ChildViewModel = hiltViewModel(),
    appointmentViewModel: AppointmentViewModel = hiltViewModel()
) {
    val currentUser by authViewModel.user.collectAsState()
    val userId = currentUser?.id

    val childrenState by childViewModel.userChildrenState.collectAsState()
    val appointmentsState by appointmentViewModel.userAppointmentsState.collectAsState()

    // Convert Child domain to ChildData for UI
    val children = when (val state = childrenState) {
        is ChildUiState.ChildrenLoaded -> state.children.map { child ->
            ChildData(
                id = child.id,
                name = child.name,
                birthDate = child.birthDate,
                gender = if (child.gender.equals(
                        "Male",
                        ignoreCase = true
                    )
                ) Gender.MALE else Gender.FEMALE
            )
        }

        else -> emptyList()
    }

    var selectedChild by remember { mutableStateOf<ChildData?>(null) }

    // Update selected child when children load
    LaunchedEffect(children) {
        if (selectedChild == null && children.isNotEmpty()) {
            selectedChild = children.firstOrNull()
        }
    }

    // Load children and appointments
    LaunchedEffect(userId) {
        userId?.let {
            childViewModel.getUserChildren(it)
            appointmentViewModel.getUserAppointments(it)
        }
    }

    val appointments = when (val state = appointmentsState) {
        is AppointmentUiState.AppointmentsLoaded -> state.appointments
        else -> emptyList()
    }

    // Filter appointments for selected child
    val childAppointments = selectedChild?.let { child ->
        appointments.filter { appointment ->
            appointment.vaccinantIds.contains(child.id)
        }
    } ?: emptyList()

    var showSelectProfileSheet by remember { mutableStateOf(false) }
    var showAddProfileSheet by remember { mutableStateOf(false) }

    val avatarColor = remember(selectedChild, children) {
        selectedChild?.let { getAvatarColorForChild(it, children) } ?: PrimaryMain
    }

    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Upcoming", "Completed")

    // Filter upcoming appointments
    val today = LocalDate.now()
    val upcomingAppointments = childAppointments
        .filter { appt ->
            val appointmentDate = try {
                LocalDate.parse(appt.date)
            } catch (e: Exception) {
                null
            }
            appointmentDate != null &&
                    !appointmentDate.isBefore(today) &&
                    appt.status != AppointmentStatus.COMPLETED
        }
        .sortedBy { appt -> LocalDate.parse(appt.date) }

    // Filter completed appointments
    val completedAppointments = childAppointments
        .filter { appt ->
            appt.status == AppointmentStatus.COMPLETED
        }
        .sortedByDescending { appt -> LocalDate.parse(appt.date) }

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

        // Show loading or profile header
        when {
            childrenState is ChildUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(32.dp))
                }
            }

            selectedChild != null -> {
                ProfileHeader(
                    child = selectedChild!!,
                    avatarColor = avatarColor,
                    onClick = { showSelectProfileSheet = true }
                )
            }

            children.isEmpty() -> {
                EmptyState("No child profiles yet. Add a new profile to get started.")
            }
        }

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
                    if (appointmentsState is AppointmentUiState.Loading) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(40.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(modifier = Modifier.size(24.dp))
                            }
                        }
                    } else if (upcomingAppointments.isEmpty()) {
                        item { EmptyState("No upcoming appointments scheduled.") }
                    } else {
                        items(upcomingAppointments) { appointment ->
                            UpcomingVaccineCardFromAppointment(
                                appointment = appointment
                            )
                        }
                    }
                }

                1 -> { // COMPLETED
                    if (appointmentsState is AppointmentUiState.Loading) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(40.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(modifier = Modifier.size(24.dp))
                            }
                        }
                    } else if (completedAppointments.isEmpty()) {
                        item { EmptyState("No completed appointments found.") }
                    } else {
                        items(completedAppointments) { appointment ->
                            UpcomingVaccineCardFromAppointment(
                                appointment = appointment
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
                showAddNewProfile = true,
                onAddNewProfile = {
                    showSelectProfileSheet = false
                    showAddProfileSheet = true
                }
            )
        }

        // Add profile sheet
        if (showAddProfileSheet) {
            AddProfileSheet(
                onDismiss = { showAddProfileSheet = false },
                onSuccess = {
                    showAddProfileSheet = false
                    // Reload children after adding new profile
                    userId?.let { childViewModel.getUserChildren(it) }
                }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewProfileScreen() {
    ImmunifyTheme {
        ProfileScreen()
    }
}
