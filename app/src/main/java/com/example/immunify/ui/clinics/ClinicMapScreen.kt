package com.example.immunify.ui.clinics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.immunify.ui.component.SearchBar
import com.example.immunify.ui.theme.*

@Composable
fun ClinicMapScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Grey30)
    ) {
        // area peta placeholder
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Grey40),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Map Area (Google Map Placeholder)",
                color = Grey70,
                style = Typography.bodyMedium
            )
        }

        // search bar
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 20.dp)
        ) {
            SearchBar(
                placeholder = "Cari klinik terdekat",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewClinicMapScreen() {
    ImmunifyTheme {
        ClinicMapScreen()
    }
}
