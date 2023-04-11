package com.paul.todolist.ui.main.todoItemView

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import com.paul.todoList.R
import com.paul.todolist.ToDoScreens
import com.paul.todolist.ui.main.common.showViewWithBackStack
import com.paul.todolist.ui.main.common.speechToText.VoiceToTextParserState
import com.paul.todolist.ui.widgets.AppButton

@Composable
fun ToDoItemAddButton(
    model: ToDoItemModel,
    voiceState: VoiceToTextParserState
) {
    var buttonStringId = R.string.add_todo

    if (model.todoItemExists) {
        buttonStringId = R.string.update_todo
    }

    Row {
        AppButton(
            onButtonPressed = {
                if (model.isNewItem()) {
                    model.insert()
                } else {
                    model.update()
                }
                voiceState.spokenText = ""
                showViewWithBackStack(ToDoScreens.ToDoListView.name)
            },
            textID = buttonStringId)
            }
}