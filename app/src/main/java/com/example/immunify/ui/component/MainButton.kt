package com.example.immunify.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.immunify.ui.theme.ImmunifyTheme
import com.example.immunify.ui.theme.PrimaryMain
import com.example.immunify.ui.theme.White10

@Composable
fun MainButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isOutline: Boolean = false,
) {
    if (isOutline) {
        OutlinedButton(
            onClick = onClick,
            modifier = modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, PrimaryMain),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.Transparent,
                contentColor = PrimaryMain
            ),
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.labelMedium.copy(color = PrimaryMain)
            )
        }

    } else {
        Button(
            onClick = onClick,
            modifier = modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryMain,
                contentColor = White10
            ),
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.labelMedium.copy(color = White10)
            )
        }
    }
}


@Preview(showBackground = false)
@Composable
fun MainButtonPreview() {
    ImmunifyTheme {
        MainButton(
            text = "Sign Up",
            onClick = {}
        )
    }
}
