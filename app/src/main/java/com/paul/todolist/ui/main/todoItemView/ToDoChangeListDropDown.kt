package com.paul.todolist.ui.main.todoItemView

import Spinner
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.paul.todoList.R

@Composable
fun ToDoChangeListDropDown(model: ToDoItemModel, addButtonVisibility: MutableState<Boolean>) {
    Column() {
        Spacer(Modifier.height(1.dp))
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .border(
                    width = 4.dp,
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(15.dp)
                ),
        ) {
            Spinner(
                model.getListOfLists(),
                model.getListTitle(),
                {
                    model.todoItem.listID = it
                    addButtonVisibility.value = model.hasDataChanges()
                },
                listTitle = stringResource(R.string.move_to_list)
            )

        }
    }
}
