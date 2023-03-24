package com.paul.todolist.ui.main.todoListView

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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

@Composable
fun ToDoItem(list: ToDoDataItem, onItemClick: (ToDoDataItem) -> Unit) {

            val colorUnSelected = MaterialTheme.colorScheme.primary
            val backgroundColor = remember {mutableStateOf(colorUnSelected)}

            Box(modifier = Modifier
                .clickable {
                        onItemClick(list)
                }
                .fillMaxSize()
                .height(70.dp)
                .background(backgroundColor.value),
                contentAlignment = Alignment.CenterStart) {
                Text(
                        text = list.description,
                        style = typography.bodyLarge,
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.padding(10.dp,0.dp,0.dp,0.dp)
                    )
            }

            Divider(color = MaterialTheme.colorScheme.onBackground, thickness = 4.dp)
    }

