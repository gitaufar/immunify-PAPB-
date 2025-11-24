package com.example.immunify.ui.clinics

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.immunify.data.model.ClinicData
import com.example.immunify.ui.component.ClinicMarker
import com.example.immunify.ui.component.ClinicNearbyCard
import com.example.immunify.ui.component.SearchAppBar
import com.example.immunify.ui.component.UserMarker
import com.example.immunify.ui.theme.White10
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.MapView
import org.osmdroid.util.GeoPoint


import com.example.immunify.core.LocalAppState

@Composable
fun ClinicMapScreen(
    navController: NavController,
    clinics: List<ClinicData>,
    onBackClick: () -> Unit = {}
) {
    val appState = LocalAppState.current
    val userLatitude = appState.userLatitude
    val userLongitude = appState.userLongitude

    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    var selectedClinic by remember { mutableStateOf<ClinicData?>(null) }

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AndroidView(
                factory = { context ->
                    MapView(context).apply {
                        setTileSource(TileSourceFactory.MAPNIK)
                        setMultiTouchControls(true)
                        zoomController.setVisibility(
                            org.osmdroid.views.CustomZoomButtonsController.Visibility.NEVER
                        )

                        controller.setZoom(13.0)
                        controller.setCenter(GeoPoint(userLatitude, userLongitude))

                        // MARKER USER
                        UserMarker(
                            map = this,
                            context = context,
                            latitude = userLatitude,
                            longitude = userLongitude
                        )

                        // MARKER SEMUA KLINIK
                        clinics.forEach { clinic ->
                            ClinicMarker(
                                map = this, context = context, clinic = clinic
                            ) { clicked ->
                                selectedClinic = clicked
                                controller.animateTo(
                                    GeoPoint(clicked.latitude, clicked.longitude)
                                )
                            }
                        }
                    }
                }, modifier = Modifier.fillMaxSize()
            )

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                SearchAppBar(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = "Find nearby clinics",
                    showBackButton = true,
                    onBackClick = onBackClick,
                    showFilterIcon = true,
                    onFilterClick = {})
            }

            // Card Klinik di bawah
            selectedClinic?.let { clinic ->
                Surface(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(vertical = 40.dp, horizontal = 16.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable(onClick = {
                            navController.navigate(Routes.clinicDetailRoute(clinic.id))
                        }), shadowElevation = 8.dp, color = White10
                ) {
                    Box(
                        modifier = Modifier
                            .background(White10)
                            .padding(8.dp)
                    ) {
                        ClinicNearbyCard(
                            clinic = clinic,
                            onClick = { navController.navigate(Routes.clinicDetailRoute(clinic.id)) }
                        )
                    }
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewClinicMapScreen() {
//    ImmunifyTheme {
//        ClinicMapScreen()
//    }
//}
