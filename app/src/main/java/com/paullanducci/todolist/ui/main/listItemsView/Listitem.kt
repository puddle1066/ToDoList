package com.paullanducci.todolist.ui.main.listItemsView

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.paullanducci.todolist.di.database.data.ListDataItem
import com.paullanducci.todolist.ui.theme.typography

@Composable
fun ListItem(
    item: ListDataItem,
    count: Int,
    selected: MutableState<ListDataItem>,
    onItemClick: (ListDataItem) -> Unit
) {
    val colorSelected = MaterialTheme.colorScheme.surface
    val colorUnSelected = MaterialTheme.colorScheme.primary
    val backgroundColor = remember { mutableStateOf(colorUnSelected) }

    if (item.listId == selected.value.listId) {
        backgroundColor.value = colorSelected
    } else {
        backgroundColor.value = colorUnSelected
    }

    Box(modifier = Modifier
        .clickable {
            onItemClick(item)
        }
        .fillMaxSize()
        .height(70.dp)
        .background(backgroundColor.value)
        .padding(20.dp, 10.dp, 0.dp, 0.dp),
        contentAlignment = Alignment.TopStart) {
        Column {
            Text(
                text = item.title,
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

