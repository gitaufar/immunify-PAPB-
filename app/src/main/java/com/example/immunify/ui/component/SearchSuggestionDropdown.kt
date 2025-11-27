package com.example.immunify.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.example.immunify.data.model.ClinicData
import com.example.immunify.data.model.ClinicDetailCardType
import com.example.immunify.ui.theme.White10

@Composable
fun SearchSuggestionDropdown(
    query: String,
    clinics: List<ClinicData>,
    onSelectClinic: (ClinicData) -> Unit,
    modifier: Modifier = Modifier
) {
    if (query.isNotBlank()) {
        // Filter hasil pencarian
        val filteredClinics = clinics.filter {
            it.name.contains(query, ignoreCase = true) ||
                    it.address.contains(query, ignoreCase = true) ||
                    it.city.contains(query, ignoreCase = true)
        }

        if (filteredClinics.isNotEmpty()) {
            // Batasi jumlah maksimal 4 hasil
            val limitedResults = filteredClinics.take(4)

            Popup(alignment = Alignment.TopCenter) {
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .background(White10)
                        .padding(16.dp)
                ) {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(
                                max = (limitedResults.size * 90).dp
                            )
                    ) {
                        items(limitedResults) { clinic ->
                            ClinicDetailCard(
                                type = ClinicDetailCardType.ADDRESS,
                                title = clinic.name,
                                subtitle = "${clinic.address}, ${clinic.city}",
                                showChange = false,
                                onClick = { onSelectClinic(clinic) }
                            )
                        }
                    }
                }
            }
        }
    }
}
