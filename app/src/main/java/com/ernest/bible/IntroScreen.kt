package com.ernest.bible

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun IntroScreen() {
    // Background Gradient for a rich, theological feel
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(Color(0xFF6D4C41), Color(0xFF3E2723)) // Earthy Brown/Maroon
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = gradientBrush)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween // Top text, bottom footer
    ) {
        // 1. Main Title and Status (Top Section)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f, fill = true) // Take up most vertical space
        ) {
            Spacer(modifier = Modifier.height(120.dp))

            // Placeholder for an Image/Icon (replace with a Vector Asset later)
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(Color(0xFFE0C18A), shape = MaterialTheme.shapes.extraLarge)
            ) {
                Text(
                    text = "📖",
                    fontSize = 50.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Welcome to KJV Offline Bible App",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Made by Ernest Tech House",
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFFE0C18A), // Gold accent
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Loading Indicator (This shows while the database is initializing)
            CircularProgressIndicator(
                color = Color.White.copy(alpha = 0.8f),
                strokeWidth = 3.dp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Initializing Database...",
                style = MaterialTheme.typography.bodySmall,
                color = Color.White.copy(alpha = 0.7f)
            )
        }

        // 2. Footer (Bottom Section)
        Text(
            text = "All rights reserved. We are in no way affiliated with the original KJV Bible.\n\nLoading...",
            style = MaterialTheme.typography.labelSmall,
            color = Color.White.copy(alpha = 0.5f),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 8.dp)
        )
    }
}