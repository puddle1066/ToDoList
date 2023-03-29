package com.paul.todolist.ui.main.todoListView

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.paul.todolist.di.database.data.ToDoDataItem
import com.paul.todolist.ui.theme.typography
import com.paul.todolist.util.getCurrentDateAsString

@Composable
fun ToDoItem(todoItem: ToDoDataItem, onItemClick: (ToDoDataItem) -> Unit) {

        val colorUnSelected = MaterialTheme.colorScheme.primary
        val backgroundColor = remember {mutableStateOf(colorUnSelected)}
        val visible = remember { mutableStateOf(false) }
        val checked = remember { mutableStateOf(false) }

        checked.value = (!todoItem.finishedDate.equals("0"))

        if (!visible.value) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .height(70.dp)
                    .background(backgroundColor.value),
                verticalAlignment = Alignment.CenterVertically

            ) {

                Checkbox(
                    modifier = Modifier.padding(16.dp),
                    checked = checked.value,
                    onCheckedChange = {
                        todoItem.finishedDate = getCurrentDateAsString()
                        checked.value = it
                        onItemClick(todoItem)
                    },
                )
                Text(
                    modifier = Modifier.clickable { onItemClick(todoItem) },
                    text = todoItem.description,
                    style = typography.titleLarge,
                    color = MaterialTheme.colorScheme.secondary,
                )
            }

            Divider(color = MaterialTheme.colorScheme.onBackground, thickness = 4.dp)
        }
    }



