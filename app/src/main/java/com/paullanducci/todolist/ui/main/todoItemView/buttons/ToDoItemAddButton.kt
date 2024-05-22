package com.paullanducci.todolist.ui.main.todoItemView.buttons

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.paullanducci.todolist.R
import com.paullanducci.todolist.ToDoScreens
import com.paullanducci.todolist.ui.main.common.showView
import com.paullanducci.todolist.ui.main.todoItemView.ToDoItemModel
import com.paullanducci.todolist.ui.widgets.AppButton

@Composable
fun ToDoItemAddButton(
    model: ToDoItemModel,
    toDoImages: SnapshotStateList<Bitmap>
) {
    var buttonStringId = R.string.update_todo

    if (model.todoItemIsNew) {
        buttonStringId = R.string.add_todo
    }

    Row {
        AppButton(
            onButtonPressed = {
                if (model.isNewItem()) {
                    model.insert()
                } else {
                    model.update()
                }
                model.addPhotos(toDoImages)
                toDoImages.clear()
                showView(ToDoScreens.ToDoListView.name)
            },
            textID = buttonStringId
        )
    }
}