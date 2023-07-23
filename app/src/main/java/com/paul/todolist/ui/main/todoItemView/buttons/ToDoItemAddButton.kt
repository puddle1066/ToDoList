package com.paul.todolist.ui.main.todoItemView.buttons

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.paul.todolist.R
import com.paul.todolist.ToDoScreens
import com.paul.todolist.ui.main.common.showView
import com.paul.todolist.ui.main.common.speechToText.VoiceToTextParserState
import com.paul.todolist.ui.main.todoItemView.ToDoItemModel
import com.paul.todolist.ui.widgets.AppButton

@Composable
fun ToDoItemAddButton(
    model: ToDoItemModel,
    toDoImages: SnapshotStateList<Bitmap>,
    voiceState: VoiceToTextParserState
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
                voiceState.spokenText = ""
                toDoImages.clear()
                showView(ToDoScreens.ToDoListView.name)
            },
            textID = buttonStringId
        )
    }
}