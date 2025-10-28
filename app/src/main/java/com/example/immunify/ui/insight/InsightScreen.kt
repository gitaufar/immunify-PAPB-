package com.example.immunify.ui.insight

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.immunify.R
import com.example.immunify.ui.component.AppBar
import com.example.immunify.ui.component.DiseaseCard
import com.example.immunify.ui.component.InsightCard

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsightScreen(
    onBackClick: () -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    Scaffold(
        topBar = { AppBar(title = "Insights", onBackClick = onBackClick) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 🔍 Search Bar
            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    singleLine = true,
                    shape = MaterialTheme.shapes.medium
                )
            }

            // 📰 Latest Updates
            item {
                SectionTitle("Latest Updates")
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        InsightCard(
                            imageUrl = "https://storage.googleapis.com/gweb-uniblog-publish-prod/images/RSV_vaccine_news.max-1300x1300.jpg",
                            title = "The first ever vaccine against RSV could be approved in 2023",
                            description = "Although usually mild, RSV can be severe for infants and older adults.",
                            date = "24 January 2023"
                        )
                    }
                    item {
                        InsightCard(
                            imageUrl = "https://storage.googleapis.com/gweb-uniblog-publish-prod/images/Child_vaccine_news.max-1300x1300.jpg",
                            title = "Catching Up on Child Vaccines in Indonesia",
                            description = "Efforts are being made to improve childhood immunization coverage across the country.",
                            date = "26 January 2023"
                        )
                    }
                }
            }

            // 🧬 Know the Disease!
            item {
                SectionTitle("Know the Disease!")
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item { DiseaseCard(imageRes = R.drawable.image_hpv, diseaseName = "HPV") }
                    item { DiseaseCard(imageRes = R.drawable.image_typhoid, diseaseName = "Typhoid") }
                    item { DiseaseCard(imageRes = R.drawable.image_polio, diseaseName = "Polio") }
                }
            }

            // 🍎 Live Healthy
            item {
                SectionTitle("Live Healthy")
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        InsightCard(
                            imageUrl = "https://storage.googleapis.com/gweb-uniblog-publish-prod/images/Healthy_breakfast.max-1300x1300.jpg",
                            title = "5 Easy Healthy Breakfast Recipes for Busy Mornings",
                            description = "Healthy and delicious options for the perfect morning routine.",
                            date = "24 February 2023"
                        )
                    }
                    item {
                        InsightCard(
                            imageUrl = "https://storage.googleapis.com/gweb-uniblog-publish-prod/images/Running_health.max-1300x1300.jpg",
                            title = "7 Health Benefits of Casual Running",
                            description = "A simple yet powerful way to boost your physical and mental well-being.",
                            date = "27 February 2023"
                        )
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colorScheme.onSurface
    )
}
