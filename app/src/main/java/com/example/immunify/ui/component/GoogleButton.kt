package com.example.immunify.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.immunify.R
import com.example.immunify.ui.theme.*

@Composable
fun GoogleButton(
    text: String = "Register with Google",
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(8.dp)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 6.dp,
                shape = shape,
                ambientColor = Black100.copy(alpha = 0.5f),
                spotColor = Black100.copy(alpha = 0.5f),
                clip = false
            )
            .clickable(onClick = onClick),
        shape = shape,
        color = White20
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_google),
                contentDescription = "Google icon",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.labelSmall.copy(color = Black100)
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
fun PreviewGoogleButton() {
    ImmunifyTheme {
        GoogleButton(
            text = "Register with Google",
            onClick = {}
        )
    }
}
