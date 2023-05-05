package com.paul.todolist.ui.main.todoItemView

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.paul.todolist.ToDoScreens
import com.paul.todolist.di.database.data.ToDoImageData
import com.paul.todolist.ui.main.MainView
import com.paul.todolist.ui.main.common.showViewWithBackStack
import com.paul.todolist.ui.main.listItemsView.swapList
import com.paul.todolist.util.decodeBase64

@Composable
fun ToDoImageListItem(
    model: ToDoItemModel,
    item: ToDoImageData,
    toDoImageData: SnapshotStateList<ToDoImageData>,
    addUpdateButtonVisibility: MutableState<Boolean>
) {
    ToDoImageitem(
        item,
        onDeleteClick = {
            decodeBase64(it.image)?.let {
                model.addedBitmapList.remove(it)
            }
            model.deleteImage(it.key)

            toDoImageData.clear()
            toDoImageData.swapList(model.getToDoImages(MainView.itemID))
            addUpdateButtonVisibility.value = true
        },
        onExpandClick = {
            MainView.image = it.image
            showViewWithBackStack(ToDoScreens.ImageItemView.name)
        }
    )
}
