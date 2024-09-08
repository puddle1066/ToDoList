package com.paullanducci.todolist.ui.main.todoItemView.dialog

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.paullanducci.todolist.ui.theme.typography

@Composable
fun ToDoAddDialog(
    onDismiss: () -> Unit,
    onConfirmation: () -> Unit,
    dialogText: String
) {
    var displayText = "\"$dialogText\""

    AlertDialog(

        icon = {
            Icon(
                Icons.Default.Info,
                contentDescription = "info Icon",
                modifier = Modifier
                    .height(50.dp)
                    .width(50.dp),
            )
        },
        title = {
            Text(
                text = "Append",
                style = typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.secondary
            )
        },
        text = {
            Text(
                text = displayText,
                style = typography.bodySmall,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.secondary
            )
        },
        onDismissRequest = {
            onDismiss()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Append Text")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismiss()
                }
            ) {
                Text("Ignore")
            }
        },
    )
}