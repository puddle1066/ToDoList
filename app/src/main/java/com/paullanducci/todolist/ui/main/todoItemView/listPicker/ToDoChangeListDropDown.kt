package com.paullanducci.todolist.ui.main.todoItemView.listPicker

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.paullanducci.todolist.R
import com.paullanducci.todolist.ui.main.todoItemView.ToDoItemModel
import com.paullanducci.todolist.ui.widgets.Spinner

@Composable
fun ToDoChangeListDropDown(model: ToDoItemModel, addButtonVisibility: MutableState<Boolean>) {
    Column() {
        Spacer(Modifier.height(1.dp))
        Row(
            modifier = Modifier
                .padding(10.dp, 10.dp, 10.dp, 0.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(15.dp))
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(15.dp)
                ),
        ) {
            Spinner(
                model.getListOfLists(),
                model.getListTitle(model.todoDataItem.listID),
                {
                    model.todoDataItem.listID = it
                    addButtonVisibility.value = model.hasDataChanges()
                },
                listTitle = stringResource(R.string.move_to_list)
            )

        }
    }
}
