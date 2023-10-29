package com.paullanducci.todolist.ui.main.todoListView.buttons

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.paullanducci.todolist.di.database.data.ToDoDataItem
import com.paullanducci.todolist.ui.main.MainActivity
import com.paullanducci.todolist.ui.main.todoListView.ToDoListModel

@Composable
fun CreateDeleteButton(
    model: ToDoListModel,
    deleteList: SnapshotStateList<ToDoDataItem>,
    deleteButtonVisible: MutableState<Boolean>
) {
    AnimatedVisibility(
        visible = deleteButtonVisible.value,
        enter = fadeIn() + slideInHorizontally(),
        exit = fadeOut() + slideOutHorizontally()
    ) {
        FloatingActionButton(
            modifier = Modifier
                .width(90.dp)
                .height(70.dp)
                .padding(
                    start = 10.dp,
                    end = 10.dp
                ),
            backgroundColor = MaterialTheme.colorScheme.error,
            onClick = {
                deleteList.forEach {
                    model.deleteItem(it.itemId)
                }
                deleteList.clear()
                model.getToDoList(MainActivity.listId)
                deleteButtonVisible.value = false
            }
        )
        { Icon(Icons.Filled.Delete, "") }
    }
}
