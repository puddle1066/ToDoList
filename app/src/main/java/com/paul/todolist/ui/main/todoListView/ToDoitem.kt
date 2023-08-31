package com.paul.todolist.ui.main.todoListView

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.snapshots.SnapshotStateList
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

@Composable
fun ToDoItem(
    item: ToDoDataItem,
    listName: String = "",
    listType: String,
    deleteList: SnapshotStateList<ToDoDataItem>,
    isDeleteEnabled: MutableState<Boolean>,
    isMoveAllowed: MutableState<Boolean>,
    onRowDelete: (ToDoDataItem, Boolean) -> Unit,
    onRowDetails: (ToDoDataItem) -> Unit,
    onCheckChanged: (ToDoDataItem, Boolean) -> Unit,
) {
    val TAG = object {}::class.java.enclosingMethod?.name

    val isChecked = remember { mutableStateOf(false) }
    val isVisible = remember { mutableStateOf(false) }
    val isSelectedItem = remember { mutableStateOf(false) }

    val colorUnSelected = MaterialTheme.colorScheme.primary
    val colorMoveSelected = MaterialTheme.colorScheme.onSurface
    val colorDeleteSelected = MaterialTheme.colorScheme.error

    val backgroundColor = remember { mutableStateOf(colorUnSelected) }

    isChecked.value = item.finishedDate != "0"

    backgroundColor.value = colorUnSelected
    when (listType) {
        listState_all_incomplete ->
            if (item.finishedDate == "0") isVisible.value = true

        listState_Finished -> {
            if (item.finishedDate != "0") isVisible.value = true
            if (deleteList.contains(item)) backgroundColor.value = colorDeleteSelected
        }

        else -> {
            isVisible.value = true
            if (item.sequence == 999) backgroundColor.value = colorMoveSelected
        }
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
                                isSelectedItem.value = !isSelectedItem.value
                            }
                        },
                        onDoubleTap = {
                            Log.e(TAG, "onDoubleTap")
                        },
                        onTap = {
                            Log.e(TAG, "onTAP")
                        }
                    )
                }

        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .clickable {
                        if (isDeleteEnabled.value) {
                            isSelectedItem.value = !isSelectedItem.value
                            onRowDelete(item, isSelectedItem.value)
                        } else {
                            onRowDetails(item)
                        }
                    },
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
                        item.finishedDate =
                            if (isChecked.value) getCurrentDateAsString() else "0"
                        onCheckChanged(item, isChecked.value)
                    },
                )
                Text(
                    modifier = Modifier
                        .weight(1.5f)
                        .padding(0.dp, 0.dp, 15.dp, 0.dp),
                    text = item.description,
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

            Divider(
                modifier = Modifier.padding(5.dp, 0.dp, 5.dp, 0.dp),
                color = MaterialTheme.colorScheme.onBackground,
                thickness = 2.dp
            )
        }
    }
}
