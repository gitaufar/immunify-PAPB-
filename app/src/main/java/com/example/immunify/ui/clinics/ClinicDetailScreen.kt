package com.example.immunify.ui.clinics

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.immunify.data.model.*
import com.example.immunify.ui.auth.AuthViewModel
import com.example.immunify.ui.component.*
import com.example.immunify.ui.profile.viewmodel.ChildUiState
import com.example.immunify.ui.profile.viewmodel.ChildViewModel
import com.example.immunify.ui.theme.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ClinicDetailScreen(
    rootNav: NavController,
    clinic: ClinicData,
    onBackClick: () -> Unit = {},
    childViewModel: ChildViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel()

) {
    var isBookmarked by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabTitles = listOf("Information", "Reviews")

    // State untuk children dan sheet
    val userChildrenState by childViewModel.userChildrenState.collectAsState()
    var childrenList by remember { mutableStateOf<List<ChildData>>(emptyList()) }
    var showSelectProfileSheet by remember { mutableStateOf(false) }
    var showAddProfileSheet by remember { mutableStateOf(false) }


    // Ambil data user dari state global
    val currentUser by authViewModel.user.collectAsState()

    // Ambil data anak dari Firestore
    LaunchedEffect(currentUser?.id) {
        currentUser?.id?.let { uid ->
            childViewModel.getUserChildren(uid)
        }
    }

    // Konversi state anak
    LaunchedEffect(userChildrenState) {
        when (val state = userChildrenState) {
            is ChildUiState.ChildrenLoaded -> {
                childrenList = state.children.map { child ->
                    ChildData(
                        id = child.id,
                        name = child.name,
                        birthDate = child.birthDate,
                        gender = if (child.gender == "Male") Gender.MALE else Gender.FEMALE
                    )
                }
            }

            else -> {}
        }
    }

    Scaffold(
        bottomBar = {
            BottomAppBar(
                text = "Set Appointment",
                onMainClick = {
                    // Cek apakah user punya anak di Firestore
                    if (childrenList.isEmpty()) {
                        // Tidak navigasi, buka SelectProfileSheet
                        showSelectProfileSheet = true
                    } else {
                        // Kalau sudah ada anak, navigasi ke SetAppointmentScreen
                        rootNav.navigate(Routes.setAppointmentRoute(clinic.id))
                    }
                },
                onCallClick = { }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // AppBar
            AppBar(
                title = clinic.name,
                onBackClick = onBackClick,
                showIcon = true,
                isBookmarked = isBookmarked,
                onBookmarkClick = { isBookmarked = !isBookmarked },
                onShareClick = { }
            )

            // Konten utama
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 4.dp)
            ) {
                item {
                    ClinicDetailHeader(clinic = clinic)
                }

                // Tab bar
                item {
                    TabRow(
                        selectedTabIndex = selectedTab,
                        containerColor = White10,
                        contentColor = PrimaryMain,
                        divider = {},
                        indicator = { tabPositions ->
                            val indicatorLeft by animateDpAsState(
                                targetValue = tabPositions[selectedTab].left,
                                label = "indicatorLeft"
                            )
                            val indicatorRight by animateDpAsState(
                                targetValue = tabPositions[selectedTab].right,
                                label = "indicatorRight"
                            )

                            Box(
                                Modifier
                                    .fillMaxWidth()
                                    .wrapContentSize(Alignment.BottomStart)
                                    .offset(x = indicatorLeft)
                                    .width(indicatorRight - indicatorLeft)
                                    .height(2.dp)
                                    .background(PrimaryMain)
                            )
                        }
                    ) {
                        tabTitles.forEachIndexed { index, title ->
                            val selected = selectedTab == index
                            val textColor by animateColorAsState(
                                targetValue = if (selected) Black100 else Grey60,
                                label = "tabColor"
                            )

                            Tab(
                                selected = selected,
                                onClick = { selectedTab = index },
                                text = {
                                    Text(
                                        text = title,
                                        style = MaterialTheme.typography.labelLarge.copy(color = textColor)
                                    )
                                }
                            )
                        }
                    }
                }

                // Konten tab
                when (selectedTab) {
                    0 -> item { ClinicInformationTab(rootNav, clinic) }
                    1 -> item { ClinicReviewsTab() }
                }
            }
        }

        // Tampilkan SelectProfileSheet jika belum ada anak
        if (showSelectProfileSheet) {
            SelectProfileSheet(
                children = childrenList,
                selectedChild = null,
                onDismiss = { showSelectProfileSheet = false },
                onSelect = { },
                showAddNewProfile = true,
                onAddNewProfile = {
                    showSelectProfileSheet = false
                    showAddProfileSheet = true
                }
            )
        }

        if (showAddProfileSheet) {
            AddProfileSheet(
                onDismiss = { showAddProfileSheet = false },
                onSuccess = {
                    showAddProfileSheet = false
                }
            )
        }
    }
}

@Composable
fun ClinicInformationTab(rootNav: NavController, clinic: ClinicData) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Alamat -> buka map dan fokus ke klinik
        ClinicDetailCard(
            type = ClinicDetailCardType.ADDRESS,
            title = clinic.address,
            subtitle = "${clinic.district}, ${clinic.city}",
            onClick = {
                rootNav.navigate(Routes.clinicMapRouteWithFocus(clinic.id))
            }
        )

        // Jam buka
        ClinicDetailCard(
            type = ClinicDetailCardType.TIME,
            title = "Open",
            subtitle = clinic.openingHours ?: "24 Hours"
        )

        // Website
        ClinicDetailCard(
            type = ClinicDetailCardType.WEBSITE,
            title = clinic.website ?: "-",
            subtitle = null
        )

        Spacer(modifier = Modifier.height(2.dp))

        SectionHeader(title = "List of Available Vaccines")

        Spacer(modifier = Modifier.height(2.dp))

        VaccineDropdown(clinic)
    }
}

@Composable
fun ClinicReviewsTab() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 160.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "No reviews available yet.",
            style = MaterialTheme.typography.bodyMedium.copy(color = Grey70),
            textAlign = TextAlign.Center
        )
    }
}
