package com.example.immunify.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.immunify.ui.theme.Grey60
import com.example.immunify.ui.theme.Grey70
import com.example.immunify.ui.theme.ImmunifyTheme

@Composable
fun SectionHeader(
    title: String,
    subtitle: String? = null,
    onClickViewAll: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
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
                style = MaterialTheme.typography.titleSmall,
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

        if (onClickViewAll != null) {
            Text(
                text = "View All",
                style = MaterialTheme.typography.bodySmall.copy(color = Grey60),
                modifier = Modifier.clickable { onClickViewAll() },
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SectionHeaderPreview_NoViewAll() {
    ImmunifyTheme {
        SectionHeader(
            title = "Parent/Guardian Information",
            subtitle = "This information will be used to schedule and confirm the appointment."
        )
    }
}