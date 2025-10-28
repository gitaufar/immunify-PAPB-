package com.example.immunify.ui.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.immunify.R
import com.example.immunify.ui.theme.Black100
import com.example.immunify.ui.theme.PrimaryMain
import com.example.immunify.ui.theme.White10

@Composable
fun Onboarding3Screen(
    getStarted: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White10)
            .padding(horizontal = 24.dp, vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            //  Gambar dan Teks
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.onboarding_3),
                    contentDescription = "Onboarding 3 Illustration",
                    modifier = Modifier.size(320.dp)
                        .padding(bottom = 12.dp)
                )

                Text(
                    text = "Invest in your child's health",
                    style = MaterialTheme.typography.titleLarge,
                    color = PrimaryMain,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Secure your child's future with the gift of good health",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Black100,
                    textAlign = TextAlign.Center,
                )
            }

            // Indikator Halaman
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OnboardingIndicator(
                    totalPages = 3,
                    currentPage = 2
                )
            }
            Spacer(modifier = Modifier.height(6.dp))


            // Tombol Get Started
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            ) {
                Button(
                    onClick = { getStarted() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryMain,
                        contentColor = White10
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                ) {
                    Text(
                        text = "Get Started",
                        fontSize = 16.sp
                    )
                }
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

