package com.paul.todolist.ui.main.todoItemView.textView

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.paul.todolist.ui.main.todoItemView.ToDoItemModel
import com.paul.todolist.ui.theme.typography

@Composable
fun LastUpdatedText(
    model: ToDoItemModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(50.dp, 20.dp, 20.dp, 20.dp),
    ) {
        Text(
            text = "Last Updated: ",
            style = typography.bodyLarge,
            color = MaterialTheme.colorScheme.surface
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = model.todoDataItem.lastupdated,
            style = typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}