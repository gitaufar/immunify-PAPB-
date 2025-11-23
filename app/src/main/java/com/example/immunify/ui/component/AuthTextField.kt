package com.example.immunify.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.immunify.R
import com.example.immunify.ui.theme.*

@Composable
fun AuthTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    placeholder: String,
    leadingIcon: Int,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false
) {
    var passwordVisible by remember { mutableStateOf(false) }
    val shape = RoundedCornerShape(8.dp)

    // InteractionSource untuk mendeteksi fokus
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused = interactionSource.collectIsFocusedAsState().value
    val borderColor = if (isFocused) PrimaryMain else Grey30

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(White20, shape)
            .border(1.dp, borderColor, shape)
            .drawWithContent {
                drawContent()
                drawRoundRect(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.08f),
                            Color.Transparent
                        ),
                        startY = 0f,
                        endY = size.height * 0.1f
                    ),
                    cornerRadius = CornerRadius(16f, 16f),
                    blendMode = BlendMode.SrcOver
                )
            }
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(id = leadingIcon),
                contentDescription = null,
                tint = Grey90,
                modifier = Modifier.size(24.dp)
            )

            Box(modifier = Modifier.weight(1f)) {
                if (value.text.isEmpty()) {
                    Text(
                        text = placeholder,
                        style = MaterialTheme.typography.bodySmall,
                        color = Grey60
                    )
                }
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    textStyle = MaterialTheme.typography.bodySmall.merge(
                        TextStyle(color = Grey90)
                    ),
                    singleLine = true,
                    visualTransformation = if (isPassword && !passwordVisible)
                        PasswordVisualTransformation()
                    else
                        VisualTransformation.None,
                    interactionSource = interactionSource,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            if (isPassword) {
                Icon(
                    imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                    contentDescription = null,
                    tint = Grey70,
                    modifier = Modifier
                        .size(22.dp)
                        .clickable { passwordVisible = !passwordVisible }
                )
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
fun PreviewAuthTextFieldPassword() {
    ImmunifyTheme {
        AuthTextField(
            value = TextFieldValue(""),
            onValueChange = {},
            placeholder = "Enter your password",
            leadingIcon = R.drawable.ic_password,
            isPassword = true
        )
    }
}
