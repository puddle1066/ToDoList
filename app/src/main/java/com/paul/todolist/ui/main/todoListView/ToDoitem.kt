package com.paul.todolist.ui.main.todoListView

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.paul.todolist.di.database.data.ToDoDataItem
import com.paul.todolist.ui.theme.typography
import com.paul.todolist.util.getCurrentDateAsString

@Composable
fun ToDoItem(
    todoItem: ToDoDataItem,
    listName: String = "",
    deleteAllowed: Boolean,
    onItemClick: (ToDoDataItem, Boolean) -> Unit,
) {
    val colorUnSelected = MaterialTheme.colorScheme.primary
    val backgroundColor = remember { mutableStateOf(colorUnSelected) }
    val checked = remember { mutableStateOf(false) }
    val isVisible = remember { mutableStateOf(true) }

    val colorSelected = MaterialTheme.colorScheme.error
    val isSelected = false
    var selected by remember { mutableStateOf(isSelected) }

    checked.value = (todoItem.finishedDate != "0")
    isVisible.value = !checked.value
    if (listName.isNotBlank()) {
        isVisible.value = true
    }

    AnimatedVisibility(
        visible = isVisible.value,
        enter = fadeIn() + slideInHorizontally(),
        exit = fadeOut() + slideOutHorizontally()
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp)
                .background(backgroundColor.value)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(backgroundColor.value)
                    .clickable {
                        if (deleteAllowed) {
                            if (selected) {
                                backgroundColor.value = colorUnSelected
                                selected = false
                            } else {
                                backgroundColor.value = colorSelected
                                selected = true
                            }
                            onItemClick(todoItem, selected)
                        }

                    },
                verticalAlignment = Alignment.CenterVertically

            ) {

                Checkbox(
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(0.5f),
                    checked = checked.value,
                    onCheckedChange = {
                        todoItem.finishedDate = getCurrentDateAsString()
                        checked.value = it
                        isVisible.value = !it
                        onItemClick(todoItem, false)
                    },
                )
                Text(
                    modifier = Modifier
                        .clickable { onItemClick(todoItem, false) }
                        .weight(1.5f),
                    text = todoItem.description,
                    style = typography.bodyLarge,
                    color = MaterialTheme.colorScheme.secondary,
                )

                if (listName.isNotBlank()) {
                    Text(
                        modifier = Modifier
                            .weight(0.5f)
                            .padding(10.dp, 10.dp, 20.dp, 10.dp),
                        text = listName,
                        style = typography.titleSmall,
                        color = MaterialTheme.colorScheme.surface,
                        textAlign = TextAlign.End
                    )
                }
            }

            Divider(color = MaterialTheme.colorScheme.onBackground, thickness = 4.dp)
        }
    }
}



