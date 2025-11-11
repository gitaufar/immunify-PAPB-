package com.example.immunify.ui.clinics

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.immunify.R
import com.example.immunify.data.local.ClinicSamples
import com.example.immunify.ui.component.ClinicNearbyCard
import com.example.immunify.ui.component.SearchAppBar
import com.example.immunify.ui.component.calculateDistanceKm
import com.example.immunify.ui.navigation.Routes
import com.example.immunify.ui.theme.Typography

@Composable
fun ClinicsScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    userLatitude: Double,
    userLongitude: Double,
    onMapClick: () -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    // mengurutkan berdasarkan jarak
    val sortedClinics = ClinicSamples.sortedBy { clinic ->
        calculateDistanceKm(
            userLatitude,
            userLongitude,
            clinic.latitude,
            clinic.longitude
        )
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Search App Bar
        SearchAppBar(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = "Find Nearby Clinics",
            showBackButton = false,
            showFilterIcon = true,
            onFilterClick = { /* buka bottom sheet filter */ }
        )

        // Konten scrollable
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            item {
                Image(
                    painter = painterResource(id = R.drawable.image_map),
                    contentDescription = "Map",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .clickable { onMapClick() }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Clinics Nearby",
                    style = Typography.titleSmall,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            // ganti fetch data dari Supabase
            items(sortedClinics) { clinic ->
                ClinicNearbyCard(
                    clinic = clinic,
                    userLatitude = userLatitude,
                    userLongitude = userLongitude,
                    onClick = {
                        navController.navigate(Routes.clinicDetailRoute(clinic.id))
                    }
                )
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewClinicsScreen() {
//    ImmunifyTheme {
//        ClinicsScreen()
//    }
//}
