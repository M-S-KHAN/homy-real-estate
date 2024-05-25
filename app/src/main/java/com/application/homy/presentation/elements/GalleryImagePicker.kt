package com.application.homy.presentation.elements

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun GalleryImagePicker(onImagesPicked: (List<Uri>) -> Unit) {
    val context = LocalContext.current
    val images = remember { mutableStateListOf<Uri>() }

    // Create a launcher for starting the intent and handling the result
    val launcher = rememberLauncherForActivityResult(
        contract = StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.clipData?.let { clipData ->
                for (i in 0 until clipData.itemCount) {
                    images.add(clipData.getItemAt(i).uri)
                }
                onImagesPicked(images.toList())
            } ?: result.data?.data?.let { uri ->
                images.add(uri)
                onImagesPicked(images.toList())
            }
        }
    }

    // Function to open the gallery
    fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        launcher.launch(Intent.createChooser(intent, "Select Pictures"))
    }

    // Optional: A button in UI to trigger the gallery open
    Button(onClick = { openGallery() }, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)) {
        Text("Select Images", color = MaterialTheme.colorScheme.primary)
    }
}
