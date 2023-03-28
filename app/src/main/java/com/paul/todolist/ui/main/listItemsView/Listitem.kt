package com.paul.todolist.ui.main.listItemsView

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.paul.todolist.di.database.data.ListDataItem
import com.paul.todolist.ui.theme.typography

@Composable
fun ListListItem(list: ListDataItem, count : Int, onItemClick: (ListDataItem, Boolean) -> Unit) {

            val colorSelected = MaterialTheme.colorScheme.error
            val colorUnSelected = MaterialTheme.colorScheme.primary
            val isSelected = false
            val backgroundColor = remember {mutableStateOf(colorUnSelected)}
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
                    onItemClick(list, selected)
                }
                .fillMaxSize()
                .height(70.dp)
                .background(backgroundColor.value)
                .padding(20.dp, 10.dp, 0.dp, 0.dp),
                contentAlignment = Alignment.TopStart) {
                Column {
                    Text(
                        text = list.title,
                        style = typography.titleLarge,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                    Text(
                        text = "Tasks: $count",
                        style = typography.bodyMedium,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                }
            }

            Divider(color = MaterialTheme.colorScheme.onBackground, thickness = 4.dp)
    }

