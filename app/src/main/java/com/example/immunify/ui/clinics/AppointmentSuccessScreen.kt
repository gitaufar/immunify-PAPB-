package com.example.immunify.ui.clinics

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.immunify.R
import com.example.immunify.ui.component.MainButton
import com.example.immunify.ui.theme.*

@Composable
fun AppointmentSuccessScreen(
    onBackToHome: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White10)
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.image_success),
                        contentDescription = "Appointment Success Illustration",
                        modifier = Modifier
                            .size(320.dp)
                            .padding(bottom = 12.dp)
                    )

                    Text(
                        text = "Successful!",
                        style = MaterialTheme.typography.titleLarge,
                        color = PrimaryMain,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Appointment has been made",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Black100,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            ) {
                MainButton(
                    text = "Back to Home",
                    onClick = onBackToHome
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAppointmentSuccessScreen() {
    ImmunifyTheme {
        AppointmentSuccessScreen(
            onBackToHome = {}
        )
    }
}
