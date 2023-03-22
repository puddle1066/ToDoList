package com.paul.todolist.ui.main.listItemsView

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.paul.todolist.ToDoList
import com.paul.todolist.di.database.data.ListDataItem
import com.paul.todolist.ui.theme.typography

@Composable
fun ListListItem(list: ListDataItem, onItemClick: (ListDataItem) -> Unit) {

            val colorSelected = MaterialTheme.colorScheme.error
            val colorUnSelected = MaterialTheme.colorScheme.primary
            val isSelected = false
            val backgroundColor = remember {mutableStateOf(colorUnSelected)}
            var selected by remember { mutableStateOf(isSelected) }

            Box(modifier = Modifier
                .clickable {
                        if (selected) {
                            backgroundColor.value  =  colorUnSelected
                            selected = false
                        } else {
                            backgroundColor.value  =  colorSelected
                            selected = true
                        }
                        onItemClick(list)
                }
                .fillMaxSize()
                .height(70.dp)
                .background(backgroundColor.value),
                contentAlignment = Alignment.Center) {
                Text(
                        text = list.title,
                        style = typography.bodyLarge,
                        color = MaterialTheme.colorScheme.secondary,
                    )
            }

            Divider(color = MaterialTheme.colorScheme.onBackground, thickness = 4.dp)
    }

