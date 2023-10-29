package com.paullanducci.todolist.ui.main.todoItemView.imageList

import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.paullanducci.todolist.ToDoScreens
import com.paullanducci.todolist.di.database.data.ToDoImageData
import com.paullanducci.todolist.ui.main.MainActivity
import com.paullanducci.todolist.ui.main.common.showViewWithBackStack
import com.paullanducci.todolist.ui.main.listItemsView.swapList
import com.paullanducci.todolist.ui.main.todoItemView.ToDoItemModel
import com.paullanducci.todolist.util.decodeBase64

@Composable
fun ToDoImageListItem(
    model: ToDoItemModel,
    item: ToDoImageData,
    toDoImageData: SnapshotStateList<ToDoImageData>,
    toDoImages: SnapshotStateList<Bitmap>,
    addUpdateButtonVisibility: MutableState<Boolean>
) {
    decodeBase64(item.image)?.let {
        ToDoImageDisplayImage(
            item.key,
            it,
            onDeleteClick = {
                decodeBase64(item.image)?.let {
                    toDoImages.remove(it)
                }
                model.deleteImage(item.key)

                toDoImageData.clear()
                toDoImageData.swapList(model.getToDoImages(MainActivity.itemId))
                if (model.hasDescription()) {
                    addUpdateButtonVisibility.value = true
                }
            },
            onExpandClick = {
                decodeBase64(item.image)?.let {
                    MainActivity.image = it
                }
                showViewWithBackStack(ToDoScreens.ImageItemView.name)
            }
        )
    }
}
