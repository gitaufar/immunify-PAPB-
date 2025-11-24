package com.example.immunify.ui.insight

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.immunify.data.local.DiseaseSamples
import com.example.immunify.data.model.DiseaseData
import com.example.immunify.ui.component.AppBar
import com.example.immunify.ui.component.BottomAppBar
import com.example.immunify.ui.component.SectionHeader
import com.example.immunify.ui.theme.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DiseaseDetailScreen(
    rootNav: NavController,
    disease: DiseaseData,
    onBackClick: () -> Unit = {}
) {
    var isBookmarked by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            BottomAppBar(
                text = "Set Appointment",
                onMainClick = { }
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
                title = disease.name,
                onBackClick = onBackClick,
                showIcon = true,
                isBookmarked = isBookmarked,
                onBookmarkClick = { isBookmarked = !isBookmarked },
                onShareClick = {}
            )

            // Scroll Content
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 12.dp)
            ) {

                // Image
                item {
                    Image(
                        painter = painterResource(id = disease.imageRes),
                        contentDescription = disease.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(8.dp))
                }

                // Key Facts Section
                item {
                    Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)) {

                        SectionHeader(title = "Key Facts")
                        Spacer(modifier = Modifier.height(8.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(1.dp, Grey30, MaterialTheme.shapes.medium)
                                .padding(12.dp)
                        ) {
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                disease.keyFacts.forEach {
                                    Text(
                                        text = "• $it",
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            fontSize = 15.sp,
                                            color = Black100
                                        )
                                    )
                                }
                            }
                        }
                    }
                }

                // Overview Section
                item {
                    Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)) {

                        SectionHeader(title = "Overview")
                        Spacer(modifier = Modifier.height(8.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(1.dp, Grey30, MaterialTheme.shapes.medium)
                                .padding(12.dp)
                        ) {
                            Text(
                                text = disease.overview,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 15.sp,
                                    color = Black100
                                )
                            )
                        }
                    }
                }

                // Symptoms Section
                item {
                    Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)) {

                        SectionHeader(title = "Symptoms")
                        Spacer(modifier = Modifier.height(8.dp))

                        disease.symptoms.forEach { section ->

                            // First Box
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(1.dp, Grey30, MaterialTheme.shapes.medium)
                                    .padding(12.dp)
                            ) {
                                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Text(
                                        text = section.summaryTitle,
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            color = PrimaryMain
                                        )
                                    )
                                    Text(
                                        text = section.summaryDescription,
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            fontSize = 15.sp,
                                            color = Grey80
                                        )
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // Second Box
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(1.dp, Grey30, MaterialTheme.shapes.medium)
                                    .padding(12.dp)
                            ) {
                                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                    section.categories.forEach { category ->
                                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                            Text(
                                                text = category.name,
                                                style = MaterialTheme.typography.labelSmall.copy(
                                                    color = Grey70,
                                                    fontSize = 15.sp
                                                )
                                            )
                                            Text(
                                                text = category.description,
                                                style = MaterialTheme.typography.bodySmall.copy(
                                                    fontSize = 15.sp,
                                                    color = Black100
                                                )
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // Treatment Section
                item {
                    Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)) {

                        SectionHeader(title = "Treatments")
                        Spacer(modifier = Modifier.height(8.dp))

                        disease.treatments.forEach { treatment ->

                            // Overview
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(1.dp, Grey30, MaterialTheme.shapes.medium)
                                    .padding(12.dp)
                            ) {
                                Text(
                                    text = treatment.overview,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontSize = 15.sp,
                                        color = Grey80
                                    )
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // Categories
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(1.dp, Grey30, MaterialTheme.shapes.medium)
                                    .padding(12.dp)
                            ) {
                                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                    treatment.categories.forEach { category ->
                                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                            Text(
                                                text = category.name,
                                                style = MaterialTheme.typography.labelSmall.copy(
                                                    color = Grey70,
                                                    fontSize = 15.sp
                                                )
                                            )
                                            Text(
                                                text = category.description,
                                                style = MaterialTheme.typography.bodySmall.copy(
                                                    fontSize = 15.sp,
                                                    color = Black100
                                                )
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewDiseaseDetailScreen() {
    ImmunifyTheme {
        DiseaseDetailScreen(
            rootNav = rememberNavController(),
            disease = remember { DiseaseSamples.first() },
            onBackClick = { })
    }
}
