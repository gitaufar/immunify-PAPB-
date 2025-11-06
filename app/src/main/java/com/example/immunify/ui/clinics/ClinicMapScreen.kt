package com.example.immunify.ui.clinics

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.immunify.R
import com.example.immunify.ui.component.ClinicNearbyCard
import com.example.immunify.ui.component.SearchAppBar
import com.example.immunify.ui.theme.ImmunifyTheme
import com.example.immunify.ui.theme.White10

@Composable
fun ClinicMapScreen(
    onBackClick: () -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Background map (full screen)
            Image(
                painter = painterResource(id = R.drawable.image_map_full),
                contentDescription = "Map",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // SearchAppBar di bagian atas
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                SearchAppBar(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = "Find nearby clinics",
                    showBackButton = true,
                    onBackClick = onBackClick,
                    showFilterIcon = true,
                    onFilterClick = { /* buka filter */ }
                )
            }

            // Clinic info card di bawah
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(vertical = 40.dp, horizontal = 16.dp)
                    .clip(RoundedCornerShape(12.dp)),
                shadowElevation = 8.dp,
                color = White10
            ) {
                Box(
                    modifier = Modifier
                        .background(White10)
                        .padding(8.dp)
                ) {
                    ClinicNearbyCard(
                        imageRes = R.drawable.image_hospital,
                        hospitalName = "RS EMC Pulomas",
                        address = "Jl. Pulo Mas Bar. VI No.20, Kec. Pulo Gadung",
                        distance = "2 km",
                        rating = 4.9
                    )
                }
            }
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
