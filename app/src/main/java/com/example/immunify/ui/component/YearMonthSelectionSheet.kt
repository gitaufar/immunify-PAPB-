package com.example.immunify.ui.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.immunify.R
import com.example.immunify.ui.theme.Black100
import com.example.immunify.ui.theme.Grey50
import com.example.immunify.ui.theme.White10
import java.time.LocalDate
import java.time.YearMonth

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YearMonthSelectionSheet(
    onSelect: (YearMonth) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedYear by remember { mutableStateOf(LocalDate.now().year) }

    val currentYear = LocalDate.now().year
    val months = listOf(
        "Jan", "Feb", "Mar", "Apr", "May", "Jun",
        "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    )

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = White10
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, top = 0.dp, end = 32.dp, bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Header Navigation
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val backEnabled = selectedYear > currentYear

                IconButton(
                    onClick = { if (backEnabled) selectedYear-- },
                    enabled = backEnabled
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_next),
                        contentDescription = "Previous",
                        tint = if (backEnabled) Black100 else Grey50,
                        modifier = Modifier.graphicsLayer {
                            rotationZ = 180f
                        }
                    )
                }

                Text(
                    text = selectedYear.toString(),
                    style = MaterialTheme.typography.titleSmall.copy(color = Black100),
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                IconButton(
                    onClick = { selectedYear++ }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_next),
                        contentDescription = "Next",
                        tint = Black100
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Month Grid
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                months.chunked(4).forEach { row ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        row.forEach { monthLabel ->
                            Box(
                                modifier = Modifier
                                    .clickable {
                                        val monthIndex = months.indexOf(monthLabel) + 1
                                        val result = YearMonth.of(selectedYear, monthIndex)
                                        onSelect(result)
                                        onDismiss()
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = monthLabel,
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        color = Black100,
                                        textAlign = TextAlign.Center
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

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewYearMonthSelectionSheet() {
    YearMonthSelectionSheet(
        onSelect = {},
        onDismiss = {}
    )
}
