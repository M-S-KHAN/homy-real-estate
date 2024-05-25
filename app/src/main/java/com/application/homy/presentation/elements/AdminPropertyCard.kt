package com.application.homy.presentation.elements

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.application.homy.R
import com.application.homy.data.Property

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AdminPropertyCard(property: Property, onDelete: () -> Unit, onEdit: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(25.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column {
            Image(
                painter =
                if (property.images.isNotEmpty()) {
                    rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current).data(property.images[0]).build()
                    )
                } else {
                    rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current)
                            .data(R.drawable.logo_full).build()
                    )

                },
                contentDescription = "Property Image",
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(30.dp, 10.dp, 30.dp, 7.dp)) {
                Row(
                    //
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                    // Give the row a space between the children
                ) {
                    Text(
                        text = property.title,
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Bold,
//                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                    Text(
                        text = "$${property.price}",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                Text(
                    text = "by ${property.owner.email}", // Assuming 'username' is part of the property model
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 11.sp,
                    lineHeight = 16.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
                Text(
                    text = "${property.address}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                )
                Row(
                    modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End
                ) {
                    IconButton(onClick = onEdit) {
                        Icon(Icons.Rounded.Edit, contentDescription = "Edit Property")
                    }
                    IconButton(onClick = onDelete) {
                        Icon(
                            Icons.Rounded.Delete,
                            contentDescription = "Delete Property",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}
