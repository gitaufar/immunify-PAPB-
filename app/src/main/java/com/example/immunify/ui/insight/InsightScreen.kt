package com.example.immunify.ui.insight

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.immunify.data.local.DiseaseSamples
import com.example.immunify.data.local.InsightSamples
import com.example.immunify.ui.component.AppBar
import com.example.immunify.ui.component.DiseaseCard
import com.example.immunify.ui.component.InsightCard
import com.example.immunify.ui.component.SearchBar
import com.example.immunify.ui.component.SectionHeader

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun InsightScreen(
    rootNav: NavController,
    onBackClick: () -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }

    Scaffold { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            // AppBar fixed, tidak ikut scroll
            AppBar(
                title = "Insights",
                onBackClick = onBackClick
            )

            // Konten Scrollable
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 40.dp)
            ) {

                // Search Bar
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                        SearchBar(
                            value = TextFieldValue(searchQuery),
                            onValueChange = { searchQuery = it.text },
                            placeholder = "Search"
                        )
                    }
                }

                // Latest Updates
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                        SectionHeader(title = "Latest Updates")
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(horizontal = 20.dp)
                    ) {
                        items(InsightSamples.subList(3, 6)) { insight ->
                            InsightCard(
                                insight = insight,
                                onClick = {
                                    rootNav.navigate(Routes.insightDetailRoute(insight.id))
                                }
                            )
                        }
                    }
                }

                // Know The Disease
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                        SectionHeader(title = "Know the Disease!")
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(horizontal = 20.dp)
                    ) {
                        items(DiseaseSamples) { disease ->
                            DiseaseCard(
                                disease = disease,
                                onClick = {
                                    rootNav.navigate(Routes.diseaseDetailRoute(disease.id))
                                }
                            )
                        }
                    }
                }

                // Live Healthy
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                        SectionHeader(title = "Live Healthy")
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(horizontal = 20.dp)
                    ) {
                        items(InsightSamples.subList(0, 3)) { insight ->
                            InsightCard(
                                insight = insight,
                                onClick = {
                                    rootNav.navigate(Routes.insightDetailRoute(insight.id))
                                }
                            )
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
fun InsightScreenPreview() {
    val nav = rememberNavController()
    InsightScreen(rootNav = nav)
}
