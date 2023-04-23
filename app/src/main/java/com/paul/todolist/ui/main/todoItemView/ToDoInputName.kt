package com.paul.todolist.ui.main.todoItemView

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.paul.todoList.R
import com.paul.todolist.ui.main.common.speechToText.VoiceToTextParserState

@Composable
fun ToDoInputName(
    model: ToDoItemModel,
    addButtonVisibility: MutableState<Boolean>,
    voiceState: VoiceToTextParserState
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp, 20.dp, 10.dp, 0.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        ToDoInputText(
            model,
            stringResource(R.string.ToDo_Task_description),
            voiceState,
            onFinished = {
                voiceState.spokenText = ""
                model.todoItem.description = it
                if (model.todoItem.description.isEmpty()) {
                    addButtonVisibility.value = false
                } else {
                    addButtonVisibility.value = model.hasDataChanges()
                }
            }
        )
    }
}