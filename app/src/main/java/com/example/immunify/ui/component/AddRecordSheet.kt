package com.example.immunify.ui.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.immunify.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.immunify.ui.theme.*
import androidx.compose.ui.text.input.TextFieldValue
import com.example.immunify.data.model.ChildData
import com.example.immunify.data.model.Gender
import com.example.immunify.domain.model.VaccinationRecord
import com.example.immunify.ui.auth.AuthViewModel
import com.example.immunify.ui.clinics.viewmodel.AppointmentUiState
import com.example.immunify.ui.clinics.viewmodel.AppointmentViewModel
import com.example.immunify.ui.profile.viewmodel.ChildUiState
import com.example.immunify.ui.profile.viewmodel.ChildViewModel
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecordSheet(
    selectedVaccinant: ChildData?,
    onSelectVaccinant: (ChildData) -> Unit,
    onDismiss: () -> Unit = {},
    onDone: () -> Unit = {},
    authViewModel: AuthViewModel = hiltViewModel(),
    childViewModel: ChildViewModel = hiltViewModel(),
    appointmentViewModel: AppointmentViewModel = hiltViewModel()
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showSelectProfile by remember { mutableStateOf(false) }
    
    val currentUser by authViewModel.user.collectAsState()
    val userId = currentUser?.id
    
    val childrenState by childViewModel.userChildrenState.collectAsState()
    val appointmentsState by appointmentViewModel.userAppointmentsState.collectAsState()
    val completeAppointmentState by appointmentViewModel.createAppointmentState.collectAsState()
    
    // Convert Child domain to ChildData for UI
    val children = when (val state = childrenState) {
        is ChildUiState.ChildrenLoaded -> state.children.map { child ->
            ChildData(
                id = child.id,
                name = child.name,
                birthDate = child.birthDate,
                gender = if (child.gender.equals("Male", ignoreCase = true)) Gender.MALE else Gender.FEMALE
            )
        }
        else -> emptyList()
    }
    
    // Get appointments for vaccine info
    val appointments = when (val state = appointmentsState) {
        is AppointmentUiState.AppointmentsLoaded -> state.appointments
        else -> emptyList()
    }
    
    // Load data
    LaunchedEffect(userId) {
        userId?.let {
            childViewModel.getUserChildren(it)
            appointmentViewModel.getUserAppointments(it)
        }
    }
    
    val context = LocalContext.current
    
    // Handle complete appointment state
    LaunchedEffect(completeAppointmentState) {
        when (val state = completeAppointmentState) {
            is AppointmentUiState.Success -> {
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
                appointmentViewModel.resetCreateState()
                onDone()
            }
            is AppointmentUiState.Error -> {
                Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
                appointmentViewModel.resetCreateState()
            }
            else -> {}
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = White10,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        modifier = Modifier.imePadding()
    ) {
        AddRecordSheetContent(
            selectedVaccinant = selectedVaccinant,
            onChooseVaccinant = { showSelectProfile = true },
            userId = userId,
            appointments = appointments,
            isLoading = completeAppointmentState is AppointmentUiState.Loading,
            onCreateRecord = { record ->
                // record.vaccineId contains appointmentId
                // record.vaccineName contains lotNumber
                userId?.let {
                    appointmentViewModel.completeAppointment(
                        userId = it,
                        appointmentId = record.vaccineId,
                        lotNumber = record.lotNumber,
                        dose = record.dose,
                        administrator = record.administrator
                    )
                }
            }
        )
    }

    if (showSelectProfile) {
        SelectProfileSheet(
            children = children,
            selectedChild = selectedVaccinant,
            onDismiss = { showSelectProfile = false },
            onSelect = { child ->
                onSelectVaccinant(child)
                showSelectProfile = false
            },
            onAddNewProfile = {
                showSelectProfile = false
            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun AddRecordSheetContent(
    selectedVaccinant: ChildData?,
    onChooseVaccinant: () -> Unit,
    userId: String?,
    appointments: List<com.example.immunify.domain.model.Appointment>,
    isLoading: Boolean,
    onCreateRecord: (VaccinationRecord) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedAppointmentId by remember { mutableStateOf("") }
    var selectedVaccineName by remember { mutableStateOf(TextFieldValue("")) }
    var lotNumber by remember { mutableStateOf(TextFieldValue("")) }
    var dose by remember { mutableStateOf(TextFieldValue("")) }
    var admin by remember { mutableStateOf(TextFieldValue("")) }
    var showAppointmentDropdown by remember { mutableStateOf(false) }
    
    // Filter pending appointments for selected vaccinant
    val pendingAppointments = remember(appointments, selectedVaccinant) {
        if (selectedVaccinant == null) emptyList()
        else appointments.filter { appointment ->
            appointment.vaccinantIds.contains(selectedVaccinant.id) &&
            appointment.status == com.example.immunify.data.model.AppointmentStatus.PENDING
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .navigationBarsPadding()
            .imePadding()
            .padding(bottom = 24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Add Record",
            style = MaterialTheme.typography.titleMedium.copy(color = Black100)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Vaccinant",
            style = MaterialTheme.typography.titleSmall.copy(color = Black100)
        )
        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onChooseVaccinant() }
        ) {
            SelectVaccinantField(selectedVaccinant = selectedVaccinant)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            "Pending Appointment",
            style = MaterialTheme.typography.titleSmall.copy(color = Black100)
        )
        Spacer(modifier = Modifier.height(12.dp))
        
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { 
                    if (selectedVaccinant != null && pendingAppointments.isNotEmpty()) {
                        showAppointmentDropdown = !showAppointmentDropdown
                    }
                }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(White10, RoundedCornerShape(8.dp))
                    .border(1.dp, Grey30, RoundedCornerShape(8.dp))
                    .padding(horizontal = 12.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = when {
                        selectedVaccinant == null -> "Select vaccinant first"
                        pendingAppointments.isEmpty() -> "No pending appointments for this child"
                        selectedVaccineName.text.isEmpty() -> "Select appointment to complete"
                        else -> selectedVaccineName.text
                    },
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = if (selectedVaccineName.text.isEmpty()) Grey60 else Black100
                    ),
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_next),
                    contentDescription = null,
                    tint = Grey60,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
        
        if (showAppointmentDropdown && pendingAppointments.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(White10, RoundedCornerShape(8.dp))
                    .border(1.dp, Grey30, RoundedCornerShape(8.dp))
            ) {
                pendingAppointments.forEach { appointment ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedAppointmentId = appointment.id
                                selectedVaccineName = TextFieldValue("${appointment.vaccineName} - ${appointment.date}")
                                showAppointmentDropdown = false
                            }
                            .padding(horizontal = 12.dp, vertical = 12.dp)
                    ) {
                        Text(
                            text = appointment.vaccineName,
                            style = MaterialTheme.typography.labelMedium.copy(color = Black100)
                        )
                        Text(
                            text = "${appointment.clinicName} - ${appointment.date}",
                            style = MaterialTheme.typography.bodySmall.copy(color = Grey60)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    "Lot Number",
                    style = MaterialTheme.typography.titleSmall.copy(color = Black100)
                )
                Spacer(modifier = Modifier.height(12.dp))
                RecordTextField(
                    value = lotNumber,
                    onValueChange = { lotNumber = it },
                    placeholder = "Enter the lot number"
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text("Dose", style = MaterialTheme.typography.titleSmall.copy(color = Black100))
                Spacer(modifier = Modifier.height(12.dp))
                RecordTextField(
                    value = dose,
                    onValueChange = { dose = it },
                    placeholder = "Dose"
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text("Administrator", style = MaterialTheme.typography.titleSmall.copy(color = Black100))
        Spacer(modifier = Modifier.height(12.dp))
        RecordTextField(
            value = admin,
            onValueChange = { admin = it },
            placeholder = "Enter the vaccinator’s name"
        )

        Spacer(modifier = Modifier.height(24.dp))

        MainButton(
            text = if (isLoading) "Saving..." else "Complete",
            onClick = {
                if (userId == null) return@MainButton
                if (selectedVaccinant == null) return@MainButton
                if (selectedAppointmentId.isEmpty()) return@MainButton

                // This will be handled in parent composable
                val record = VaccinationRecord(
                    userId = userId,
                    childId = selectedVaccinant.id,
                    childName = selectedVaccinant.name,
                    vaccineId = selectedAppointmentId, // Using appointmentId here
                    vaccineName = lotNumber.text,
                    lotNumber = lotNumber.text,
                    dose = dose.text,
                    administrator = admin.text,
                    vaccinationDate = LocalDate.now().toString(),
                    notes = ""
                )

                onCreateRecord(record)
            },
        )
    }
}

@Composable
fun SelectVaccinantField(
    selectedVaccinant: ChildData?,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(White10, RoundedCornerShape(8.dp))
                .border(1.dp, Grey30, RoundedCornerShape(8.dp))
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = selectedVaccinant?.name ?: "Choose vaccinant",
                style = MaterialTheme.typography.labelMedium.copy(
                    color = if (selectedVaccinant == null) Grey60 else Black100
                ),
                modifier = Modifier.weight(1f)
            )

            Icon(
                painter = painterResource(id = R.drawable.ic_next_bold),
                contentDescription = null,
                tint = Grey60,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

//@RequiresApi(Build.VERSION_CODES.O)
//@Preview(showBackground = true)
//@Composable
//fun PreviewAddRecordSheet() {
//    ImmunifyTheme {
//        AddRecordSheet()
//    }
//}
