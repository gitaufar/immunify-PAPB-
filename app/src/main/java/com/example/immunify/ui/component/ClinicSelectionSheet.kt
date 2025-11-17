package com.example.immunify.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.immunify.data.model.ClinicData
import com.example.immunify.data.model.ClinicDetailCardType
import com.example.immunify.ui.theme.Black100
import com.example.immunify.ui.theme.White10

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClinicSelectionSheet(
    clinics: List<ClinicData>,
    onSelect: (ClinicData) -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = White10
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 0.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
        ) {
            Text(
                text = "Select Location",
                style = MaterialTheme.typography.titleMedium.copy(color = Black100),
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(clinics) { clinic ->
                    ClinicDetailCard(
                        type = ClinicDetailCardType.CLINIC,
                        title = clinic.name,
                        subtitle = "${clinic.address}, ${clinic.city}",
                        showChange = false,
                        onClick = {
                            onSelect(clinic)
                            onDismiss()
                        }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}
