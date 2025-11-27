package com.example.immunify.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.immunify.R
import com.example.immunify.ui.theme.*

@Composable
fun BottomAppBar(
    text: String,
    enabled: Boolean = true,
    onMainClick: () -> Unit,
    onCallClick: (() -> Unit)? = null
) {
    Surface(
        color = White10,
        tonalElevation = 8.dp,
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 32.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Tampilkan tombol telepon hanya jika onCallClick tidak null
            if (onCallClick != null) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .background(
                            color = PrimaryMain,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable { onCallClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_telephone),
                        contentDescription = "Call",
                        tint = White10,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            MainButton(text = text, onClick = onMainClick, enabled = enabled)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBottomAppBarWithButton() {
    ImmunifyTheme {
        BottomAppBar(
            text = "Set Appointment",
            onMainClick = {},
            onCallClick = {},
        )
    }
}