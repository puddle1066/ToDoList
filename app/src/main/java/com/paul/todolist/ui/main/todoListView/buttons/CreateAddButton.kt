package com.paul.todolist.ui.main.todoListView.buttons

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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.paul.todolist.ToDoScreens
import com.paul.todolist.ui.main.MainActivity
import com.paul.todolist.ui.main.common.showViewWithBackStack

@Composable
fun CreateAddButton(isAddEnabled: MutableState<Boolean>) {
    AnimatedVisibility(
        visible = isAddEnabled.value,
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
            backgroundColor = MaterialTheme.colorScheme.primary,
            onClick = {
                MainActivity.itemId = ""   //Clear Item ID as its a new item
                showViewWithBackStack(ToDoScreens.ToDoItemView.name)
            }
        )
        { Icon(Icons.Filled.Add, "") }
    }
}
