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
import com.example.immunify.ui.theme.Grey40
import com.example.immunify.ui.theme.PrimaryMain
import com.example.immunify.ui.theme.White10

@Composable
fun Onboarding1Screen(
    onNext: () -> Unit,
    onSkip: (() -> Unit)? = null
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
                    painter = painterResource(id = R.drawable.onboarding_1),
                    contentDescription = "Onboarding 1 Illustration",
                    modifier = Modifier.size(320.dp)
                        .padding(bottom = 12.dp)
                )

                Text(
                    text = "Immunization made easy",
                    style = MaterialTheme.typography.titleLarge,
                    color = PrimaryMain,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Never miss an appointment again while eliminating the hassle of paper records.",
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
                    currentPage = 0 // karena ini halaman pertama
                )
            }
            Spacer(modifier = Modifier.height(6.dp))

            // Tombol Next & Skip
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            ) {
                Button(
                    onClick = { onNext() },
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
                        text = "Next",
                        fontSize = 14.sp
                    )
                }

                TextButton(
                    onClick = { onSkip?.invoke() },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = "Skip",
                        color = Grey40,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

