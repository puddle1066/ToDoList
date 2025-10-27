package com.paullanducci.todolist.ui.main.listItemsView

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun IconButton(icon: ImageVector, onClick: () -> Unit, iconWeight: Dp, contentDescription: String) {
    IconButton(
        modifier = Modifier
            .width(iconWeight)
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.secondary,
                shape = RoundedCornerShape(5.dp)
            ),
        onClick = { onClick() }
    ) {
        Icon(
            icon,
            tint = MaterialTheme.colorScheme.secondary,
            contentDescription = contentDescription
        )
    }
}