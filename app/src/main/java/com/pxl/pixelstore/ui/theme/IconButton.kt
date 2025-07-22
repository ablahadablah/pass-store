package com.pxl.pixelstore.ui.theme

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun AddIconButton(
    onClicked: () -> Unit
) {
    IconButton(onClick = {
        onClicked()
    }) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add new password",
            tint = MaterialTheme.colorScheme.onBackground
        )
    }
}