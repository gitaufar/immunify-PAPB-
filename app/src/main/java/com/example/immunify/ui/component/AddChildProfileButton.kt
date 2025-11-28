package com.example.immunify.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.immunify.R
import com.example.immunify.ui.theme.*

@Composable
fun AddChildProfileButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .border(2.dp, PrimaryMain, RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_delete),
                contentDescription = "Add Child",
                tint = PrimaryMain,
                modifier = Modifier
                    .size(48.dp)
                    .rotate(45f)
            )
            
            Text(
                text = "Add Child Profile",
                style = MaterialTheme.typography.titleMedium.copy(color = PrimaryMain)
            )
            
            Text(
                text = "Add your child's profile to start tracking their vaccination schedule",
                style = MaterialTheme.typography.bodySmall.copy(color = Grey70),
                textAlign = TextAlign.Center
            )
        }
    }
}
