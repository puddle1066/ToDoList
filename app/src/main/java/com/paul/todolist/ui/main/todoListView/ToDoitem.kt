package com.paul.todolist.ui.main.todoListView

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.paul.todolist.di.database.data.ToDoDataItem
import com.paul.todolist.listState_Finished
import com.paul.todolist.listState_all_incomplete
import com.paul.todolist.ui.theme.typography
import com.paul.todolist.util.getCurrentDateAsString

@Composable
fun ToDoItem(
    todoItem: ToDoDataItem,
    listName: String = "",
    deleteAllowed: Boolean,
    backgroundColor: MutableState<Color>,
    listType: String,
    onRowClick: (ToDoDataItem, Boolean) -> Unit,
    onCheckChanged: (ToDoDataItem, Boolean) -> Unit,
) {
    val colorUnSelected = MaterialTheme.colorScheme.primary
    val colorDeleteSelected = MaterialTheme.colorScheme.error

    var isChecked = remember { mutableStateOf(false) }
    var isVisible = remember { mutableStateOf(true) }
    var isSelectedItem = remember { mutableStateOf(false) }


    isChecked.value = todoItem.finishedDate != "0"

    when (listType) {
        listState_all_incomplete ->
            if (todoItem.finishedDate == "0") isVisible.value = true else false

        listState_Finished ->
            if (todoItem.finishedDate != "0") isVisible.value = true else false

        else ->
            isVisible.value = true
    }

    AnimatedVisibility(
        visible = isVisible.value,
        enter = fadeIn() + slideInHorizontally(),
        exit = fadeOut() + slideOutHorizontally()
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp)
                .background(backgroundColor.value)
        ) {


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(backgroundColor.value),
                verticalAlignment = Alignment.CenterVertically

            ) {

                Checkbox(
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(0.5f),
                    checked = isChecked.value,
                    onCheckedChange = {
                        isChecked.value = it

                        isVisible.value = false     //Always false hear as transition between lists

                        todoItem.finishedDate =
                            if (isChecked.value) getCurrentDateAsString() else "0"
                        onCheckChanged(todoItem, isChecked.value)
                    },
                )
                Text(
                    modifier = Modifier
                        .weight(1.5f)
                        .clickable {
                            selectForDeletion(
                                deleteAllowed,
                                isSelectedItem,
                                backgroundColor,
                                colorUnSelected,
                                colorDeleteSelected
                            )
                            onRowClick(todoItem, isSelectedItem.value)
                        },
                    text = todoItem.description,
                    style = typography.bodyLarge,
                    color = MaterialTheme.colorScheme.secondary,
                )

                if (listName.isNotBlank()) {
                    Text(
                        modifier = Modifier
                            .weight(0.5f)
                            .clickable {
                                selectForDeletion(
                                    deleteAllowed,
                                    isSelectedItem,
                                    backgroundColor,
                                    colorUnSelected,
                                    colorDeleteSelected
                                )
                                onRowClick(todoItem, isSelectedItem.value)
                            }
                            .padding(10.dp, 10.dp, 20.dp, 10.dp),
                        text = listName,
                        style = typography.titleSmall,
                        color = MaterialTheme.colorScheme.surface,
                        textAlign = TextAlign.End
                    )
                }
            }

            Divider(color = MaterialTheme.colorScheme.onBackground, thickness = 4.dp)
        }
    }
}

fun selectForDeletion(
    deleteAllowed: Boolean,
    isSelected: MutableState<Boolean>,
    backgroundColor: MutableState<Color>,
    colorUnSelected: Color,
    colorDeleteSelected: Color
) {

    if (deleteAllowed) {
        if (isSelected.value) {
            backgroundColor.value = colorUnSelected
            isSelected.value = false
        } else {
            backgroundColor.value = colorDeleteSelected
            isSelected.value = true
        }

    }
}



