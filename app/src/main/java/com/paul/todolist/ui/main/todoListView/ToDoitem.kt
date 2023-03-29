package com.paul.todolist.ui.main.todoListView

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.paul.todolist.di.database.data.ToDoDataItem
import com.paul.todolist.ui.theme.typography
import com.paul.todolist.util.getCurrentDateAsString
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ToDoItem(todoItem: ToDoDataItem, onItemClick: (ToDoDataItem) -> Unit) {

            val colorUnSelected = MaterialTheme.colorScheme.primary
            val backgroundColor = remember {mutableStateOf(colorUnSelected)}
            val checkedState = remember { mutableStateOf(false) }


            val swipeableState = rememberSwipeableState(0)
            val iconSize = (-68).dp
            val iconPx = with(LocalDensity.current) { iconSize.toPx() }
            val anchors = mapOf(0f to 0, iconPx to 1)

            Row(modifier = Modifier
                .clickable {
                    todoItem.finishedDate = getCurrentDateAsString()
                    onItemClick(todoItem)
                    checkedState.value = true
                }
                .fillMaxSize()
                .height(70.dp)
                .background(backgroundColor.value),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    modifier = Modifier.padding(16.dp),
                    checked = checkedState.value,
                    onCheckedChange = { checkedState.value = it },
                )
                Text(
                    text = todoItem.description,
                    style = typography.titleLarge,
                    color = MaterialTheme.colorScheme.secondary,
                    )
            }

            Divider(color = MaterialTheme.colorScheme.onBackground, thickness = 4.dp)
    }


