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

@Composable
fun ToDoInputName(
    voiceTextState: MutableState<String>,
    onKeyboardVisibilityChange: (show: Boolean, text: String) -> Unit,
    onTextChanged: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp, 20.dp, 10.dp, 0.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(MaterialTheme.colorScheme.background)
    ) {
        ToDoInputText(
            stringResource(R.string.ToDo_Task_description),
            voiceTextState,
            onTextChanged = {
                onTextChanged(it)
            },
            onKeyboardStateChange = { showKeyboard, text ->
                onKeyboardVisibilityChange(showKeyboard, text)
            }
        )
    }
}