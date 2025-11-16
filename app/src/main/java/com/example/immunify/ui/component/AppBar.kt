package com.example.immunify.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.immunify.R
import com.example.immunify.ui.theme.ImmunifyTheme
import com.example.immunify.ui.theme.Grey70

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    title: String,
    onBackClick: () -> Unit,
    showIcon: Boolean = false,
    isBookmarked: Boolean = false,
    onBookmarkClick: (() -> Unit)? = null,
    onShareClick: (() -> Unit)? = null
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge.copy(
                    color = MaterialTheme.colorScheme.onBackground
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "Back",
                    tint = Grey70
                )
            }
        },
        actions = {
            if (showIcon) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        painter = painterResource(
                            id = if (isBookmarked) R.drawable.ic_bookmark_fill
                            else R.drawable.ic_bookmark_blank
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
        modifier = Modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
            navigationIconContentColor = MaterialTheme.colorScheme.onTertiary
        ),
        windowInsets = WindowInsets(0.dp),
        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
    )

    Divider(
        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
        thickness = 1.5.dp
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewAppBarWithIcons() {
    ImmunifyTheme {
        AppBar(
            title = "RS EMC Pulomas",
            onBackClick = { },
            showIcon = true,
            isBookmarked = false,
            onBookmarkClick = { },
            onShareClick = { }
        )
    }
}

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
