package com.application.homy.presentation.elements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.application.homy.R

@Composable
fun BidDialog(
    onDismissRequest: () -> Unit,
    bidAmount: String,
    onBidAmountChange: (String) -> Unit,
    message: String,
    onMessageChange: (String) -> Unit,
    onConfirm: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                stringResource(id = R.string.place_bid),
                color = MaterialTheme.colorScheme.tertiary,
                fontWeight = FontWeight.W600,
                fontSize = 20.sp
            )
        },
        text = {
            Column {
                OutlinedTextField(
                    value = bidAmount,
                    onValueChange = onBidAmountChange,
                    keyboardOptions = KeyboardOptions.Default.merge(
                        other = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        )
                    ),
                    label = { Text(stringResource(id = R.string.bid_amount)) },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        focusedBorderColor = MaterialTheme.colorScheme.secondary,
                        unfocusedBorderColor = Color.White,
                        unfocusedTextColor = Color.White,
                        cursorColor = Color.White,
                        unfocusedLabelColor = Color.White,
                        focusedLabelColor = MaterialTheme.colorScheme.secondary,
                    )
                )
                Spacer(Modifier.height(5.dp))
                OutlinedTextField(
                    value = message,
                    onValueChange = onMessageChange,
                    label = { Text(stringResource(id = R.string.bid_comments)) },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        focusedBorderColor = MaterialTheme.colorScheme.secondary,
                        unfocusedBorderColor = Color.White,
                        unfocusedTextColor = Color.White,
                        cursorColor = Color.White,
                        unfocusedLabelColor = Color.White,
                        focusedLabelColor = MaterialTheme.colorScheme.secondary,
                    ),
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onConfirm, colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary, contentColor = Color.Black
                )
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismissRequest, colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error, contentColor = Color.White
                )
            ) { Text("Cancel") }
        },
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true),
        containerColor = MaterialTheme.colorScheme.inverseSurface,
    )
}