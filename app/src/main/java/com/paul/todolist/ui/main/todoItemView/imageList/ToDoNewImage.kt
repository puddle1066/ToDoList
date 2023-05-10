package com.paul.todolist.ui.main.todoItemView.imageList

import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.paul.todolist.ToDoScreens
import com.paul.todolist.ui.main.MainView
import com.paul.todolist.ui.main.common.showViewWithBackStack
import com.paul.todolist.ui.main.todoItemView.ToDoItemModel

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
            MainView.image = image
            showViewWithBackStack(ToDoScreens.ImageItemView.name)
        }
    )
}
