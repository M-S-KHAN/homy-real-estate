package com.application.homy.presentation.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.application.homy.data.Bid

@Composable
fun BidCard(bid: Bid) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp, 5.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black)
    ) {
        Column(
            modifier = Modifier
                .background(Color.Black)
                .padding(24.dp)
        ) {
            Text(
                "${bid.property.title}",
                color = Color.White,
                style = MaterialTheme.typography.titleLarge,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                "for $${bid.bid_amount}",
                color = Color(0xFFFFA500),  // Orange color for amount
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                "Made by ${bid.by.username + " (" + bid.by.email + ")"}",
                color = Color.LightGray,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "${bid.message}",
                color = MaterialTheme.colorScheme.tertiary,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Date Posted: ${
                    bid.created_at.split(" ")[0].split("-").let {
                        "${it[2]}-${it[1]}-${it[0]}"
                    }
                }",
                color = Color.Gray,
                style = MaterialTheme.typography.bodySmall,
                fontSize = 11.sp
            )
        }
    }
}
