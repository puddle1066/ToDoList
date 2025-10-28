package com.paullanducci.todolist.ui.main.listItemsView

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ListItemsDeleteDialog(openDialog: MutableState<Boolean>) {

    if (openDialog.value) {
        AlertDialog(
            modifier = Modifier.padding(20.dp),
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(text = "Cannot Delete")
            },
            text = {
                Text(
                    "You cannot delete this list as it has Tasks associated with It. Please check the Finished List.."
                )
            },
            buttons = {
                Row(
                    modifier = Modifier.padding(all = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { openDialog.value = false }
                    ) {
                        Text("OK")
                    }
                }
            }
        )
    }
}