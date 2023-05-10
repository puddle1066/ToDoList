package com.paul.todolist.ui.main.todoItemView.buttons

import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.paul.todolist.imageHeight
import com.paul.todolist.imageWidth
import com.paul.todolist.ui.main.todoItemView.ToDoItemModel
import com.paul.todolist.ui.main.todoItemView.camera.ToDoItemCameraButton
import com.paul.todolist.util.rotate

@Composable
fun ToDoCameraButtonProcessing(
    model: ToDoItemModel,
    toDoImages: SnapshotStateList<Bitmap>,
    addUpdateButtonVisibility: MutableState<Boolean>
) {
    ToDoItemCameraButton { bitmap ->
        //rotate the bitmap as the screen is fixed portrait
        var image = Bitmap.createScaledBitmap(bitmap, imageWidth, imageHeight, false).rotate(90f)
        toDoImages.add(image)

        if (model.hasDescription()) {
            addUpdateButtonVisibility.value = true
        }
    }
}