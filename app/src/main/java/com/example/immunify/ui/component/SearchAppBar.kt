package com.example.immunify.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.immunify.R
import com.example.immunify.ui.theme.*

@Composable
fun SearchAppBar(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Search",
    showBackButton: Boolean = false,
    onBackClick: (() -> Unit)? = null,
    showFilterIcon: Boolean = false,
    onFilterClick: (() -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(White10)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Back Button
        if (showBackButton && onBackClick != null) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.onTertiary,
                modifier = Modifier
                    .size(28.dp)
                    .clickable { onBackClick() }
            )
        }

        // Search Field
        Box(modifier = Modifier.weight(1f)) {
            SearchBar(
                value = value,
                onValueChange = onValueChange,
                placeholder = placeholder
            )
        }

        // Filter Icon
        if (showFilterIcon && onFilterClick != null) {
            Icon(
                painter = painterResource(id = R.drawable.ic_filter),
                contentDescription = "Filter Icon",
                tint = Grey70,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onFilterClick() }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSearchAppBar() {
    ImmunifyTheme {
        SearchAppBar(
            value = TextFieldValue(""),
            onValueChange = {},
            placeholder = "Find nearby clinics",
            showBackButton = true,
            onBackClick = {},
            showFilterIcon = true,
            onFilterClick = {}
        )
    }
}
