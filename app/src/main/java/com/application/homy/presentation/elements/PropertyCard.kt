package com.application.homy.presentation.elements

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import com.application.homy.R
import java.time.format.DateTimeFormatter
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PropertyCard() {
    var isFavorite by remember { mutableStateOf(false) }
    val price = "$55,000"
    val postedDate =
        LocalDate.now().minusDays(5).format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column {
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data("https://ik.imagekit.io/nankanamart/pexels-pixabay-259588_8D7LNlGJa.jpg?updatedAt=1714496973402")
                        .placeholder(R.drawable.logo_full).scale(Scale.FILL).build()
                ),
                contentDescription = "Property Image",
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(30.dp, 15.dp, 30.dp, 15.dp)) {
                Text(
                    text = "Country Farmhouse",
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 10.dp)
                )
                Text(
                    text = "Charming farmhouse in the countryside, perfect for peace and quiet. Features include three bedrooms, a large barn, and over 50 acres of land.",
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 14.sp,
                    lineHeight = 16.sp,
                    modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 5.dp)
                )
                Text(
                    text = "321 Country Road, Rural Area",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = price,
                        color = Color.White,
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier
                            .background(
                                MaterialTheme.colorScheme.background, RoundedCornerShape(25)
                            )
                            .padding(10.dp)
                    )
                    Spacer(Modifier.weight(1f))
                    Text(
                        text = "Posted: $postedDate",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                    Spacer(Modifier.width(8.dp))
                    IconToggleButton(checked = isFavorite, onCheckedChange = { isFavorite = it }) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = "Toggle Favorite",
                            tint = if (isFavorite) Color.Red else Color.Gray
                        )
                    }
                }
            }
        }
    }
}
