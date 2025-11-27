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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.immunify.core.LocalAppState
import com.example.immunify.data.model.ClinicData
import com.example.immunify.ui.component.*
import com.example.immunify.ui.theme.White10
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView

@Composable
fun ClinicMapScreen(
    navController: NavController,
    clinics: List<ClinicData>,
    focusId: String? = null,
    onBackClick: () -> Unit = {}
) {
    val appState = LocalAppState.current
    val userLatitude = appState.userLatitude
    val userLongitude = appState.userLongitude

    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    var selectedClinic by remember { mutableStateOf<ClinicData?>(null) }

    var mapViewRef by remember { mutableStateOf<MapView?>(null) }
    val coroutineScope = rememberCoroutineScope()

    val keyboard = LocalSoftwareKeyboardController.current

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // MapView
            AndroidView(
                factory = { context ->
                    MapView(context).apply {
                        mapViewRef = this
                        setTileSource(TileSourceFactory.MAPNIK)
                        setMultiTouchControls(true)
                        zoomController.setVisibility(
                            org.osmdroid.views.CustomZoomButtonsController.Visibility.NEVER
                        )

                        controller.setZoom(13.0)
                        controller.setCenter(GeoPoint(userLatitude, userLongitude))

                        // Marker user
                        UserMarker(
                            map = this,
                            context = context,
                            latitude = userLatitude,
                            longitude = userLongitude
                        )

                        // Marker semua klinik
                        clinics.forEach { clinic ->
                            ClinicMarker(
                                map = this,
                                context = context,
                                clinic = clinic
                            ) { clicked ->
                                selectedClinic = clicked
                                controller.animateTo(
                                    GeoPoint(clicked.latitude, clicked.longitude)
                                )
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxSize()
            )

            // Zoom awal saat screen pertama kali terbuka
            LaunchedEffect(mapViewRef) {
                mapViewRef?.let { map ->
                    smoothZoomTo(map, 15.0, 650L)
                }
            }

            // Fokus otomatis jika datang dari ClinicDetailScreen
            LaunchedEffect(focusId, mapViewRef) {
                if (focusId != null && mapViewRef != null) {
                    val targetClinic = clinics.find { it.id == focusId }
                    targetClinic?.let { clinic ->
                        selectedClinic = clinic
                        smoothPanAndZoomTo(
                            mapViewRef!!,
                            GeoPoint(clinic.latitude, clinic.longitude),
                            16.0
                        )
                    }
                }
            }

            // Search bar di atas
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(White10)
            ) {
                SearchAppBar(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = "Find nearby clinics",
                    showBackButton = true,
                    onBackClick = onBackClick,
                    showFilterIcon = true,
                    onFilterClick = {},
                    modifier = Modifier.background(White10) // kalau SearchAppBar punya parameter modifier
                )
            }

            // Dropdown saran hasil pencarian
            SearchSuggestionDropdown(
                query = searchQuery.text,
                clinics = clinics,
                onSelectClinic = { clinic ->
                    searchQuery = TextFieldValue("")
                    selectedClinic = clinic

                    coroutineScope.launch {
                        mapViewRef?.let { map ->
                            val target = GeoPoint(clinic.latitude, clinic.longitude)
                            smoothPanAndZoomTo(map, target, 16.0)
                        }
                    }
                },
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 60.dp)
            )

            // Card klinik terpilih di bagian bawah
            selectedClinic?.let { clinic ->
                Surface(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(vertical = 40.dp, horizontal = 16.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable {
                            navController.navigate(Routes.clinicDetailRoute(clinic.id))
                        },
                    shadowElevation = 8.dp,
                    color = White10
                ) {
                    Box(
                        modifier = Modifier
                            .background(White10)
                            .padding(8.dp)
                    ) {
                        ClinicNearbyCard(
                            clinic = clinic,
                            onClick = {
                                navController.navigate(Routes.clinicDetailRoute(clinic.id))
                            }
                        )
                    }
                }
            }
        }
    }
}

// Fungsi zoom halus
suspend fun smoothZoomTo(mapView: MapView, targetZoom: Double, durationMs: Long = 700L) {
    val startZoom = mapView.zoomLevelDouble
    val steps = 35
    val zoomStep = (targetZoom - startZoom) / steps
    val frameTime = durationMs / steps

    for (i in 1..steps) {
        val newZoom = startZoom + zoomStep * i
        mapView.controller.setZoom(newZoom)
        delay(frameTime)
    }
}

// Fungsi pan dan zoom halus ke titik target
suspend fun smoothPanAndZoomTo(mapView: MapView, target: GeoPoint, targetZoom: Double) {
    val steps = 35
    val startZoom = mapView.zoomLevelDouble
    val startPoint = mapView.mapCenter as GeoPoint
    val latStep = (target.latitude - startPoint.latitude) / steps
    val lonStep = (target.longitude - startPoint.longitude) / steps
    val zoomStep = (targetZoom - startZoom) / steps
    val frameTime = 15L

    for (i in 1..steps) {
        val newLat = startPoint.latitude + latStep * i
        val newLon = startPoint.longitude + lonStep * i
        val newZoom = startZoom + zoomStep * i
        mapView.controller.setCenter(GeoPoint(newLat, newLon))
        mapView.controller.setZoom(newZoom)
        delay(frameTime)
    }
}
