package com.paul.todolist.ui.main.todoItemView

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.paul.todolist.ToDoScreens
import com.paul.todolist.ui.main.common.showViewWithBackStack
import com.paul.todolist.ui.main.common.speechToText.VoiceToTextParserState
import com.paul.todolist.ui.widgets.AppButton

@Composable
fun ToDoItemAddButton(
    model: ToDoItemModel,
    voiceState: VoiceToTextParserState,
    addButtonVisibility: MutableState<Boolean>
) {
    Row(modifier = Modifier.padding(10.dp)) {
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
            textID = model.getButtonText(),
            buttonVisible = addButtonVisibility.value
        )
    }
}