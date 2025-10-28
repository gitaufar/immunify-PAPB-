package com.example.immunify.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.immunify.ui.theme.Grey30
import com.example.immunify.ui.theme.PrimaryMain

@Composable
fun OnboardingIndicator(
    totalPages: Int,
    currentPage: Int
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        repeat(totalPages) { index ->
            val isActive = index == currentPage
            val dotColor = if (isActive) PrimaryMain else Grey30
            val dotWidth = if (isActive) 48.dp else 12.dp

            Box(
                modifier = Modifier
                    .height(12.dp)
                    .width(dotWidth)
                    .clip(RoundedCornerShape(50))
                    .background(dotColor)
            )

            if (index != totalPages - 1) {
                Spacer(modifier = Modifier.width(6.dp))
            }
        }
    }
}
