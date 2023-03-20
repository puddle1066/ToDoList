package com.paul.todolist.ui.main.listItemsView

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.paul.todolist.di.database.data.ListDataItem
import com.paul.todolist.ui.theme.typography

@Composable
fun ListListItem(list: ListDataItem, modifier: Modifier = Modifier, onItemClick: (ListDataItem) -> Unit) {

            val colorSelected = MaterialTheme.colors.error
            val colorUnSelected = MaterialTheme.colors.primary
            val isSelected = false
            val backgroundColor = remember {mutableStateOf(colorUnSelected)}
            val selected by remember { mutableStateOf(isSelected) }

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
                        style = typography.h6,
                        color = MaterialTheme.colors.secondary,
                    )
            }

            Divider(color = MaterialTheme.colors.onBackground, thickness = 4.dp)
    }

