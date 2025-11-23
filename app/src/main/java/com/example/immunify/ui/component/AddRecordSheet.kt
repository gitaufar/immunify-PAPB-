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
import com.example.immunify.ui.theme.*
import androidx.compose.ui.text.input.TextFieldValue
import com.example.immunify.data.local.ChildSamples
import com.example.immunify.data.model.ChildData

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecordSheet(
    selectedVaccinant: ChildData?,
    onSelectVaccinant: (ChildData) -> Unit,
    onDismiss: () -> Unit = {},
    onDone: () -> Unit = {}
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showSelectProfile by remember { mutableStateOf(false) }

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
            onDone = onDone
        )
    }

    if (showSelectProfile) {
        SelectProfileSheet(
            children = ChildSamples,
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

@Composable
private fun AddRecordSheetContent(
    selectedVaccinant: ChildData?,
    onChooseVaccinant: () -> Unit,
    onDone: () -> Unit,
    modifier: Modifier = Modifier
) {
    var immunizationType by remember { mutableStateOf(TextFieldValue("")) }
    var lotNumber by remember { mutableStateOf(TextFieldValue("")) }
    var dose by remember { mutableStateOf(TextFieldValue("")) }
    var admin by remember { mutableStateOf(TextFieldValue("")) }

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
            "Immunization Type",
            style = MaterialTheme.typography.titleSmall.copy(color = Black100)
        )
        Spacer(modifier = Modifier.height(12.dp))
        RecordTextField(
            value = immunizationType,
            onValueChange = { immunizationType = it },
            placeholder = "Enter the name of vaccine"
        )

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
            text = "Done",
            onClick = onDone
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
