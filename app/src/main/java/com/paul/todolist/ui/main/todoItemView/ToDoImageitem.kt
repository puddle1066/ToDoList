package com.paul.todolist.ui.main.todoItemView

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.paul.todolist.di.database.data.ToDoImageData

@Composable
fun ToDoImageitem(
    todoimage: ToDoImageData,
    onItemClick: (ToDoImageData, Boolean) -> Unit
) {

    val colorSelected = MaterialTheme.colorScheme.error
    val colorUnSelected = MaterialTheme.colorScheme.primary
    val isSelected = false
    val backgroundColor = remember { mutableStateOf(colorUnSelected) }
    var selected by remember { mutableStateOf(isSelected) }

    Box(modifier = Modifier
        .clickable {
            if (selected) {
                backgroundColor.value = colorUnSelected
                selected = false
            } else {
                backgroundColor.value = colorSelected
                selected = true
            }
            onItemClick(todoimage, selected)
        }
        .fillMaxSize()
        .height(70.dp)
        .background(backgroundColor.value)
        .padding(20.dp, 10.dp, 0.dp, 0.dp),
        contentAlignment = Alignment.TopStart) {

        Column {
        }
    }

    Divider(color = MaterialTheme.colorScheme.onBackground, thickness = 4.dp)
}

