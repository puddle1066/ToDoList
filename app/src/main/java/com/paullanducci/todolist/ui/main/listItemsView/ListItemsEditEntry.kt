package com.paullanducci.todolist.ui.main.listItemsView

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.paullanducci.todolist.R
import com.paullanducci.todolist.di.database.data.ListDataItem
import com.paullanducci.todolist.ui.main.todoItemView.inputName.ToDoInputText

@Composable
fun ListItemsEditEntry(
    model: ListItemsModel,
    title: MutableState<String>,
    selected: MutableState<ListDataItem>,
    onTextChanged: (String) -> Unit,
    onFinished: () -> Unit,
) {
    val openDialog = remember { mutableStateOf(false) }
    val showButtons = remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current

    if (openDialog.value) {
        ListItemsDeleteDialog(openDialog)
    }

    if (title.value.isNotEmpty()) {
        showButtons.value = true
    } else {
        showButtons.value = false
        keyboardController!!.hide()
    }

    Box(
        Modifier
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(15.dp)
            )
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth()
    ) {
        Column {
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .padding(10.dp)
            ) {

                ToDoInputText(
                    stringResource(R.string.ToDo_Task_description),
                    title,
                    onTextChanged = {
                        onTextChanged(it)
                    },
                    onKeyboardStateChange = { showKeyboard, text ->
                        //DO Nothing as we need a positive action
                    }
                )
            }

            if (showButtons.value) {
                ListItemsShowButtons(selected, onPressed = {
                    when (it) {
                        R.string.add_button -> {
                            insertTask(model, title.value)
                            showButtons.value = false
                            keyboardController!!.hide()
                            onFinished()
                        }

                        R.string.update_button -> {
                            updateTask(model, selected, title.value)
                            showButtons.value = false
                            keyboardController!!.hide()
                            onFinished()
                        }

                        R.string.delete_button -> {
                            deleteTask(model, selected, openDialog)
                            showButtons.value = false
                            keyboardController!!.hide()
                            onFinished()
                        }
                    }

                })
            }
        }
    }

}
