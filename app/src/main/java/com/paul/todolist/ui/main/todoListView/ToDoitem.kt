package com.paul.todolist.ui.main.todoListView

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.paul.todolist.di.database.data.ToDoDataItem
import com.paul.todolist.listState_Finished
import com.paul.todolist.listState_all_incomplete
import com.paul.todolist.ui.theme.typography
import com.paul.todolist.util.getCurrentDateAsString

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ToDoItem(
    todoItem: ToDoDataItem,
    listName: String = "",
    isDeleteEnabled: MutableState<Boolean>,
    isMoveAllowed: MutableState<Boolean>,
    listType: String,
    onRowDelete: (ToDoDataItem, Boolean) -> Unit,
    onRowDetails: (ToDoDataItem, Boolean) -> Unit,
    onCheckChanged: (ToDoDataItem, Boolean) -> Unit,
) {
    var TAG = object {}::class.java.enclosingMethod.name

    val isChecked = remember { mutableStateOf(false) }
    val isVisible = remember { mutableStateOf(true) }
    val isSelectedItem = remember { mutableStateOf(false) }

    val colorUnSelected = MaterialTheme.colorScheme.primary
    val colorDeleteSelected = MaterialTheme.colorScheme.error
    val colorMoveSelected = MaterialTheme.colorScheme.primary  //Color.Gray

    val backgroundColor = remember { mutableStateOf(colorUnSelected) }

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

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp)
                .background(backgroundColor.value)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = {
                            Log.e(TAG, "onPress")
                            if (isMoveAllowed.value) {
                                if (isSelectedItem.value) {
                                    backgroundColor.value = colorUnSelected
                                    isSelectedItem.value = false
                                } else {
                                    backgroundColor.value = colorMoveSelected
                                    isSelectedItem.value = true
                                }
                            }
                        },
                        onDoubleTap = {
                            Log.e(TAG, "onDoubleTap")
                        },
                        onTap = {
                            Log.e(TAG, "onTAP")
                            if (isDeleteEnabled.value) {
                                if (isSelectedItem.value) {
                                    backgroundColor.value = colorUnSelected
                                    isSelectedItem.value = false
                                } else {
                                    backgroundColor.value = colorDeleteSelected
                                    isSelectedItem.value = true
                                }
                                onRowDelete(todoItem, isSelectedItem.value)
                            } else {
                                onRowDetails(todoItem, isSelectedItem.value)
                            }
                        }
                    )
                }

        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
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
                    modifier = Modifier.weight(1.5f),
                    text = todoItem.description,
                    style = typography.bodyLarge,
                    color = MaterialTheme.colorScheme.secondary,
                )

                if (listName.isNotBlank()) {
                    Text(
                        modifier = Modifier
                            .weight(0.5f)
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
