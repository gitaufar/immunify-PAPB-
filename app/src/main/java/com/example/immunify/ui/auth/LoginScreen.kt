package com.example.immunify.ui.auth

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.immunify.R
import com.example.immunify.ui.component.AuthTextField
import com.example.immunify.ui.component.GoogleButton
import com.example.immunify.ui.component.MainButton
import com.example.immunify.ui.theme.*

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onGoogleClick: () -> Unit = {}
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val whiteHeight = screenHeight * 0.85f

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryMain)
    ) {
        // Surface putih dibatasi tinggi 85%
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(whiteHeight)
                .align(Alignment.BottomCenter),
            color = Color.White,
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Welcome Back",
                    style = MaterialTheme.typography.titleLarge.copy(color = Black100),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Login to continue using Immunify",
                    style = MaterialTheme.typography.bodySmall.copy(color = Black100),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                GoogleButton(
                    text = "Continue with Google",
                    onClick = onGoogleClick
                )

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Divider(modifier = Modifier.weight(1f), thickness = 1.dp, color = Grey50)
                    Text(
                        text = "OR",
                        style = MaterialTheme.typography.bodySmall.copy(color = Grey90)
                    )
                    Divider(modifier = Modifier.weight(1f), thickness = 1.dp, color = Grey50)
                }

                Spacer(modifier = Modifier.height(32.dp))

                var email by remember { mutableStateOf(androidx.compose.ui.text.input.TextFieldValue("")) }
                var password by remember { mutableStateOf(androidx.compose.ui.text.input.TextFieldValue("")) }

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(text = "Email", style = MaterialTheme.typography.labelMedium.copy(color = Black100))
                    AuthTextField(
                        value = email,
                        onValueChange = { email = it },
                        placeholder = "Enter your email",
                        leadingIcon = R.drawable.ic_email
                    )

                    Text(text = "Password", style = MaterialTheme.typography.labelMedium.copy(color = Black100))
                    AuthTextField(
                        value = password,
                        onValueChange = { password = it },
                        placeholder = "Enter your password",
                        leadingIcon = R.drawable.ic_password,
                        isPassword = true
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Forgot Password?",
                    style = MaterialTheme.typography.labelSmall.copy(color = PrimaryMain),
                    modifier = Modifier
                        .align(Alignment.End)
                        .clickable { onForgotPasswordClick() }
                )

                Spacer(modifier = Modifier.height(48.dp))

                val termsText = buildAnnotatedString {
                    append("By signing up, you agree to the ")
                    pushStyle(MaterialTheme.typography.labelSmall.toSpanStyle().copy(color = Grey90))
                    append("Terms and Conditions")
                    pop()
                    append(" as well as the ")
                    pushStyle(MaterialTheme.typography.labelSmall.toSpanStyle().copy(color = Grey90))
                    append("Privacy Policy")
                    pop()
                    append(" of Immunify")
                }

                Text(
                    text = termsText,
                    style = MaterialTheme.typography.bodySmall.copy(color = Grey70),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                MainButton(
                    text = "Sign In",
                    onClick = { onLoginSuccess() }
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Don’t have an account? ",
                        style = MaterialTheme.typography.bodySmall.copy(color = Grey70)
                    )
                    Text(
                        text = "Sign up",
                        style = MaterialTheme.typography.labelSmall.copy(color = PrimaryMain),
                        modifier = Modifier.clickable { onRegisterClick() }
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    ImmunifyTheme {
        LoginScreen(
            onLoginSuccess = {},
            onForgotPasswordClick = {},
            onRegisterClick = {}
        )
    }
}
