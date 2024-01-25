package com.paullanducci.todolist.ui.main.todoItemView.imageList

import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.paullanducci.todolist.ToDoScreens
import com.paullanducci.todolist.ui.main.MainActivity
import com.paullanducci.todolist.ui.main.common.showViewWithBackStack
import com.paullanducci.todolist.ui.main.todoItemView.ToDoItemModel

@Composable
fun ToDoNewImage(
    model: ToDoItemModel,
    image: Bitmap,
    toDoImages: SnapshotStateList<Bitmap>,
    addUpdateButtonVisibility: MutableState<Boolean>
) {
    ToDoImageDisplayImage(
        "",
        image,
        onDeleteClick = {
            toDoImages.remove(image)
            if (model.hasDescription()) {
                addUpdateButtonVisibility.value = true
            }
        },
        onExpandClick = {
            MainActivity.image = image
            showViewWithBackStack(ToDoScreens.ImageItemView.name)
        }
    )
}
