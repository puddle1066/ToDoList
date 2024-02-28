package com.paullanducci.todolist.ui.main.todoItemView.inputName

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.paullanducci.todolist.R
import com.paullanducci.todolist.ui.main.common.speechToText.VoiceToTextParserState
import com.paullanducci.todolist.ui.main.todoItemView.ToDoItemModel

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
            .clip(RoundedCornerShape(15.dp))
            .background(MaterialTheme.colorScheme.background)
    ) {
        ToDoInputText(
            model,
            stringResource(R.string.ToDo_Task_description),
            voiceState,
            onFinished = {
                voiceState.spokenText = ""
                model.todoDataItem.description = it
                if (it.isNullOrBlank()) {
                    addButtonVisibility.value = false
                } else {
                    addButtonVisibility.value = model.hasDataChanges()
                }
            },
            onTextChanged = {
                addButtonVisibility.value = true
            }
        )
    }
}