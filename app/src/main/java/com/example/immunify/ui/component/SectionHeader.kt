package com.example.immunify.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.immunify.R
import com.example.immunify.ui.theme.*

@Composable
fun SectionHeader(
    title: String,
    subtitle: String? = null,
    onClickViewAll: (() -> Unit)? = null,
    onFilterClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (onClickViewAll != null) {
                    Modifier.clickable { onClickViewAll() }
                } else Modifier
            )
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            if (subtitle != null) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall.copy(color = Grey70),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {

            if (onClickViewAll != null) {
                Text(
                    text = "View All",
                    style = MaterialTheme.typography.bodySmall.copy(color = Grey60),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            if (onFilterClick != null) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .border(
                            BorderStroke(1.dp, Grey30),
                            shape = MaterialTheme.shapes.small
                        )
                        .clickable { onFilterClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_filter),
                        contentDescription = "Filter",
                        tint = PrimaryMain,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SectionHeaderViewAllPreview() {
    ImmunifyTheme {
        SectionHeader(
            title = "Upcoming Vaccine",
            onClickViewAll = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SectionHeaderFilterPreview() {
    ImmunifyTheme {
        SectionHeader(
            title = "Appointments",
            onFilterClick = {}
        )
    }
}
