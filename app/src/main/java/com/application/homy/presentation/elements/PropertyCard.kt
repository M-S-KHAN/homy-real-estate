package com.application.homy.presentation.elements

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.application.homy.data.Property

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PropertyCard(property: Property, onTap: () -> Unit = {}) {
    var isFavorite by remember { mutableStateOf(property.is_favorite) }
    val formattedPrice = "$${property.price}"

    val formattedDate = property.created_at.split(" ")[0].split("-").let {
        "${it[2]}-${it[1]}-${it[0]}"
    }

    isFavorite = property.is_favorite

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                onTap()
            }, shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column {
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(property.images.firstOrNull() ?: "").placeholder(R.drawable.logo_full)
                        .scale(Scale.FILL).build()
                ),
                contentDescription = "Property Image",
                modifier = Modifier
                    .height(120.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(30.dp, 7.dp, 30.dp, 7.dp)) {
                Text(
                    text = property.title,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
                Text(
                    text = property.description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 14.sp,
                    lineHeight = 16.sp,
                    modifier = Modifier.padding(bottom = 5.dp)
                )
                Text(
                    text = property.address,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = formattedPrice,
                        color = Color.White,
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier
                            .background(
                                MaterialTheme.colorScheme.background, RoundedCornerShape(25)
                            )
                            .padding(6.dp, 4.dp, 6.dp, 4.dp)
                    )
                    Spacer(Modifier.weight(1f))
                    Text(
                        text = "Posted: $formattedDate",
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
