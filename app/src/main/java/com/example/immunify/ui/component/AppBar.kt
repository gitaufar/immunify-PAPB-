package com.example.immunify.ui.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.immunify.R
import com.example.immunify.ui.theme.*
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    title: String,
    currentYM: YearMonth? = null,
    onBackClick: () -> Unit,
    showIcon: Boolean = false,
    isBookmarked: Boolean = false,
    onBookmarkClick: (() -> Unit)? = null,
    onShareClick: (() -> Unit)? = null,
    isOnTracker: Boolean = false,
    onNextClick: (() -> Unit)? = null,
    onCalendarClick: (() -> Unit)? = null,
    onAddClick: (() -> Unit)? = null
) {
    TopAppBar(
        title = {
            if (!isOnTracker) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge.copy(color = Black100),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            } else if (currentYM != null) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { onNextClick?.invoke() }
                ) {
                    Text(
                        text = currentYM.format(DateTimeFormatter.ofPattern("MMMM yyyy")),
                        style = MaterialTheme.typography.titleLarge.copy(color = Black100)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Icon(
                        painter = painterResource(id = R.drawable.ic_next),
                        contentDescription = "Next",
                        tint = Grey60,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        },
        navigationIcon = {
            if (!isOnTracker) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = "Back",
                        tint = Grey70
                    )
                }
            }
        },
        actions = {
            Row(
                modifier = Modifier.padding(end = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                if (onCalendarClick != null) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .border(
                                width = 1.dp,
                                color = Grey30,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable { onCalendarClick() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_date),
                            contentDescription = "Calendar",
                            tint = if (isOnTracker) Grey90 else MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                if (isOnTracker && onAddClick != null) {
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .background(
                                color = PrimaryMain,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable { onAddClick() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_delete),
                            contentDescription = "Add",
                            tint = White10,
                            modifier = Modifier
                                .size(24.dp)
                                .graphicsLayer { rotationZ = 45f }
                        )
                    }
                }

                if (!isOnTracker && showIcon) {
                    Icon(
                        painter = painterResource(
                            id = if (isBookmarked) R.drawable.ic_bookmark_fill else R.drawable.ic_bookmark_blank
                        ),
                        contentDescription = "Bookmark",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .size(28.dp)
                            .clickable { onBookmarkClick?.invoke() }
                    )

                    Icon(
                        painter = painterResource(id = R.drawable.ic_share),
                        contentDescription = "Share",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .size(26.dp)
                            .clickable { onShareClick?.invoke() }
                    )
                }
            }
        },

        windowInsets = WindowInsets(0),

        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        modifier = Modifier.fillMaxWidth()
    )

    Divider(
        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
        thickness = 1.dp
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewAppBarWithIcons() {
    ImmunifyTheme {
        AppBar(
            title = "RS EMC Pulomas",
            onBackClick = { },
            showIcon = true
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewAppBarWithoutIcons() {
    ImmunifyTheme {
        AppBar(
            title = "Insight",
            onBackClick = { }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewTrackerAppBar() {
    ImmunifyTheme {
        AppBar(
            title = "",
            onBackClick = {},
            isOnTracker = true,
            onNextClick = {},
            onCalendarClick = {},
            onAddClick = {}
        )
    }
}