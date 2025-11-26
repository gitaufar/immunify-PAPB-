package com.example.immunify.ui.component

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.immunify.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.immunify.domain.model.Child
import com.example.immunify.ui.profile.viewmodel.ChildUiState
import com.example.immunify.ui.profile.viewmodel.ChildViewModel
import com.example.immunify.ui.theme.*
import androidx.compose.ui.text.input.TextFieldValue
import com.example.immunify.ui.auth.AuthViewModel
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProfileSheet(
    childViewModel: ChildViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel(),
    onDismiss: () -> Unit = {},
    onSuccess: () -> Unit = {}
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    
    val context = LocalContext.current
    val createChildState by childViewModel.createChildState.collectAsState()
    val currentUser by authViewModel.user.collectAsState()
    val userId = currentUser?.id

    // Handle state changes
    LaunchedEffect(createChildState) {
        when (createChildState) {
            is ChildUiState.Success -> {
                Toast.makeText(
                    context, 
                    (createChildState as ChildUiState.Success).message, 
                    Toast.LENGTH_SHORT
                ).show()
                childViewModel.resetCreateState()
                onSuccess()
                onDismiss()
            }
            is ChildUiState.Error -> {
                Toast.makeText(
                    context, 
                    (createChildState as ChildUiState.Error).message, 
                    Toast.LENGTH_SHORT
                ).show()
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
        if (userId == null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("Please login to add child profile")
            }
        } else {
            AddProfileSheetContent(
                userId = userId,
                childViewModel = childViewModel,
                isLoading = createChildState is ChildUiState.Loading,
                onDismiss = onDismiss
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun AddProfileSheetContent(
    userId: String,
    childViewModel: ChildViewModel,
    isLoading: Boolean,
    onDismiss: () -> Unit
) {
    var fullName by remember { mutableStateOf(TextFieldValue("")) }

    var day by remember { mutableStateOf(TextFieldValue("")) }
    var month by remember { mutableStateOf(TextFieldValue("")) }
    var year by remember { mutableStateOf(TextFieldValue("")) }
    var showDatePicker by remember { mutableStateOf(false) }

    var gender by remember { mutableStateOf("") }
    
    val onAddChild = {
        // Format date to yyyy-MM-dd
        val dayStr = day.text.padStart(2, '0')
        val monthStr = month.text.padStart(2, '0')
        val yearStr = year.text
        val birthDate = "$yearStr-$monthStr-$dayStr"
        
        val child = Child(
            userId = userId,
            name = fullName.text,
            birthDate = birthDate,
            gender = gender
        )
        
        childViewModel.createChild(child)
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
        // Title
        Text(
            text = "Add New Profile",
            style = MaterialTheme.typography.titleMedium.copy(color = Black100)
        )

        Spacer(Modifier.height(24.dp))

        // Full Name
        Text("Child Full Name", style = MaterialTheme.typography.titleSmall.copy(color = Black100))
        Spacer(Modifier.height(12.dp))

        RecordTextField(
            value = fullName,
            onValueChange = { fullName = it },
            placeholder = "Enter child full name"
        )

        Spacer(Modifier.height(24.dp))

        // Date of birth
        Text("Date of Birth", style = MaterialTheme.typography.titleSmall.copy(color = Black100))
        Spacer(Modifier.height(12.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            RecordTextField(
                value = day,
                onValueChange = {
                    if (it.text.length <= 2 && it.text.all { ch -> ch.isDigit() }) {
                        day = it
                    }
                },
                placeholder = "Day",
                keyboardType = KeyboardType.Number,
                modifier = Modifier.weight(1f)
            )
            RecordTextField(
                value = month,
                onValueChange = {
                    if (it.text.length <= 2 && it.text.all { ch -> ch.isDigit() }) {
                        month = it
                    }
                },
                placeholder = "Month",
                keyboardType = KeyboardType.Number,
                modifier = Modifier.weight(1.3f)
            )
            RecordTextField(
                value = year,
                onValueChange = {
                    if (it.text.length <= 4 && it.text.all { ch -> ch.isDigit() }) {
                        year = it
                    }
                },
                placeholder = "Year",
                keyboardType = KeyboardType.Number,
                modifier = Modifier.weight(1.1f)
            )

            // Pick Date Button
            Box(
                modifier = Modifier
                    .weight(2f)
                    .clip(RoundedCornerShape(10.dp))
                    .background(PrimarySurface)
                    .border(1.dp, PrimaryBorder, RoundedCornerShape(10.dp))
                    .clickable { showDatePicker = true }
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_date),
                        contentDescription = null,
                        tint = PrimaryMain,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "Pick Date",
                        style = MaterialTheme.typography.bodyMedium.copy(color = PrimaryMain)
                    )
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        if (showDatePicker) {
            AppDatePicker(
                initialDate = LocalDate.now(),
                onDateSelected = { selected ->
                    day = TextFieldValue(selected.dayOfMonth.toString().padStart(2, '0'))
                    month = TextFieldValue(selected.monthValue.toString().padStart(2, '0'))
                    year = TextFieldValue(selected.year.toString())
                    showDatePicker = false
                },
                onDismiss = { showDatePicker = false }
            )
        }

        // Gender
        Text("Gender", style = MaterialTheme.typography.titleSmall.copy(color = Black100))
        Spacer(Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { gender = "Male" }
            ) {
                RadioButton(
                    selected = gender == "Male",
                    onClick = { gender = "Male" },
                    colors = RadioButtonDefaults.colors(selectedColor = PrimaryMain),
                    modifier = Modifier.size(22.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text("Male", style = MaterialTheme.typography.bodyMedium)
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { gender = "Female" }
            ) {
                RadioButton(
                    selected = gender == "Female",
                    onClick = { gender = "Female" },
                    colors = RadioButtonDefaults.colors(selectedColor = PrimaryMain),
                    modifier = Modifier.size(22.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text("Female", style = MaterialTheme.typography.bodyMedium)
            }
        }

        Spacer(Modifier.height(40.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Cancel
            MainButton(
                text = "Cancel",
                onClick = { onDismiss() },
                modifier = Modifier.weight(1f),
                isOutline = true,
//                enabled = !isLoading
            )

            // Add
            MainButton(
                text = if (isLoading) "Adding..." else "Add",
                onClick = { onAddChild() },
                modifier = Modifier.weight(1f),
//                enabled = !isLoading && fullName.text.isNotEmpty() &&
//                         day.text.isNotEmpty() && month.text.isNotEmpty() &&
//                         year.text.isNotEmpty() && gender.isNotEmpty()
            )
        }
        
        if (isLoading) {
            Spacer(Modifier.height(16.dp))
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = PrimaryMain
                )
            }
        }
    }
}

// Preview removed - requires Hilt ViewModel injection
// To test, run the app and use AddProfileSheet in a screen
