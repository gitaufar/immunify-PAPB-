package com.example.immunify.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.immunify.R
import com.example.immunify.data.model.AppointmentCardType
import com.example.immunify.data.model.ChildData
import com.example.immunify.data.model.Gender
import com.example.immunify.ui.theme.*

@Composable
fun AppointmentSummaryCard(
    type: AppointmentCardType,
    date: String? = null,
    time: String? = null,
    vaccinants: List<ChildData> = emptyList(),
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(White10, RoundedCornerShape(8.dp))
            .border(1.dp, Grey30, RoundedCornerShape(8.dp))
    ) {

        when (type) {
            AppointmentCardType.DATE_TIME -> {
                SummaryItem(
                    icon = R.drawable.ic_date,
                    title = date!!,
                    isLast = false
                )

                SummaryItem(
                    icon = R.drawable.ic_clock,
                    title = time!!,
                    isLast = true
                )
            }

            AppointmentCardType.VACCINANTS -> {
                vaccinants.forEachIndexed { index, child ->
                    VaccinantSummaryItem(
                        number = index + 1,
                        child = child,
                        isLast = index == vaccinants.lastIndex
                    )
                }
            }
        }
    }
}

@Composable
private fun SummaryItem(
    icon: Int,
    title: String,
    isLast: Boolean
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = Grey90,
                modifier = Modifier.size(20.dp)
            )

            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall.copy(color = Black100)
            )
        }

        if (!isLast) {
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = Grey30
            )
        }
    }
}

@Composable
private fun VaccinantSummaryItem(
    number: Int,
    child: ChildData,
    isLast: Boolean
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "$number.",
                style = MaterialTheme.typography.bodyMedium.copy(color = Black100)
            )

            Column {
                Text(
                    text = child.name,
                    style = MaterialTheme.typography.labelSmall.copy(color = Black100)
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = "Child $number",
                    style = MaterialTheme.typography.bodySmall.copy(color = Grey70)
                )
            }
        }

        if (!isLast) {
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = Grey30
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
fun PreviewAppointmentSummaryDateTime() {
    ImmunifyTheme {
        AppointmentSummaryCard(
            type = AppointmentCardType.DATE_TIME,
            date = "Sat, 22nd April 2023",
            time = "13:00 - 14:30",
        )
    }
}

@Preview(showBackground = false)
@Composable
fun PreviewAppointmentSummaryVaccinants() {
    ImmunifyTheme {
        val children = listOf(
            ChildData(
                id = "1",
                name = "Jane Doe",
                birthDate = "2020-01-01",
                    gender = Gender.FEMALE
            ),
            ChildData(
                id = "2",
                name = "John Doe",
                birthDate = "2019-05-10",
                gender = Gender.MALE
            )
        )

        AppointmentSummaryCard(
            type = AppointmentCardType.VACCINANTS,
            vaccinants = children,
        )
    }
}
