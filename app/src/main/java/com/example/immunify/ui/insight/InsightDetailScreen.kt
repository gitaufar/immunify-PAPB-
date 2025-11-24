package com.example.immunify.ui.insight

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.immunify.R
import com.example.immunify.data.local.InsightSamples
import com.example.immunify.data.model.InsightData
import com.example.immunify.ui.component.AppBar
import com.example.immunify.ui.component.SectionHeader
import com.example.immunify.ui.theme.Black100
import com.example.immunify.ui.theme.Grey30
import com.example.immunify.ui.theme.Grey80
import com.example.immunify.ui.theme.ImmunifyTheme


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun InsightDetailScreen(
    rootNav: NavController,
    insight: InsightData,
    onBackClick: () -> Unit = {}
) {
    var isBookmarked by remember { mutableStateOf(false) }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            // AppBar
            AppBar(
                title = "Read Insight",
                onBackClick = onBackClick,
                showIcon = true,
                isBookmarked = isBookmarked,
                onBookmarkClick = { isBookmarked = !isBookmarked },
                onShareClick = {}
            )

            // Main Scrollable Content
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(bottom = 8.dp)
            ) {

                // Title
                item {
                    Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)) {
                        Text(
                            text = insight.title,
                            style = MaterialTheme.typography.titleLarge.copy(color = Black100)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = insight.date,
                            style = MaterialTheme.typography.labelMedium.copy(color = Grey80)
                        )
                    }
                }

                /// Image
                item {
                    AsyncImage(
                        model = insight.imageUrl,
                        contentDescription = insight.title,
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(R.drawable.image_the_first_ever),
                        error = painterResource(R.drawable.image_the_first_ever),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                    )
                }

                // Short Description Box
                item {
                    Column(
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
                    ) {
                        SectionHeader(title = "Summary")
                        Spacer(modifier = Modifier.height(8.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(1.dp, Grey30, MaterialTheme.shapes.medium)
                                .padding(12.dp)
                        ) {
                            Text(
                                text = insight.description,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = Black100,
                                    fontSize = 15.sp
                                )
                            )
                        }
                    }
                }

                // Full Content
                item {
                    Column(
                        modifier = Modifier.padding(horizontal = 20.dp)
                    ) {
                        SectionHeader(title = "Contents")
                        Spacer(modifier = Modifier.height(8.dp))

                        insight.content.forEach { paragraph ->
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(1.dp, Grey30, MaterialTheme.shapes.medium)
                                    .padding(12.dp)
                            ) {
                                Text(
                                    text = paragraph,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = Black100,
                                        fontSize = 15.sp
                                    )
                                )
                            }
                            Spacer(modifier = Modifier.height(12.dp))
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
fun PreviewInsightDetailScreen() {
    ImmunifyTheme {
        InsightDetailScreen(
            rootNav = rememberNavController(),
            insight = InsightSamples.first(),
            onBackClick = {}
        )
    }
}
