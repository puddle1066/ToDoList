package com.paul.todolist.ui.main.todoItemView.camera

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.paul.todolist.di.database.data.ToDoImageData
import com.paul.todolist.ui.main.todoItemView.ToDoItemModel
import com.paul.todolist.util.encodeTobase64
import com.paul.todolist.util.rotate

@Composable
fun ToDoCameraButtonProcessing(
    model: ToDoItemModel,
    toDoImageData: SnapshotStateList<ToDoImageData>,
    addUpdateButtonVisibility: MutableState<Boolean>
) {
    ToDoItemCameraButton { bitmap ->

        val rotated =
            bitmap.rotate(90f)  //rotate the bitmap as the screen is fixed portrait

        model.addedBitmapList.add(rotated)
        addUpdateButtonVisibility.value = true
        encodeTobase64(rotated)?.let { bitmapString ->
            ToDoImageData(
                "", "",
                bitmapString
            )
        }?.let { it -> toDoImageData.add(it) }
    }
}