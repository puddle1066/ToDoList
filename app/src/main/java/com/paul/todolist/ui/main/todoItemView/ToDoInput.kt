package com.paul.todolist.ui.main.todoItemView

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.paul.todolist.ui.main.common.speechToText.VoiceToTextParserState
import com.paul.todolist.ui.theme.ToDoListTheme
import com.paul.todolist.ui.theme.typography

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun ToDoInputText(
    model: ToDoItemModel,
    fieldTitle: String,
    voiceState: VoiceToTextParserState,
    onFinished: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    ToDoListTheme {
        var textValue = model.todoDataItem.description
        var textState = remember { mutableStateOf(textValue) }
        val keyboardController = LocalSoftwareKeyboardController.current

        if (!voiceState.isSpeaking && !voiceState.spokenText.isEmpty()) {
            textState.value = voiceState.spokenText
            onFinished(textState.value)
        }

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .border(
                    width = 4.dp,
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(15.dp)
                ),

            label = {
                Text(
                    modifier = Modifier.background(MaterialTheme.colorScheme.primary),
                    text = fieldTitle,
                    style = typography.bodyLarge,
                    textAlign = TextAlign.Left,
                    color = MaterialTheme.colorScheme.surface
                )
            },

            shape = RoundedCornerShape(50.dp),

            keyboardActions = KeyboardActions(

                onDone = {
                    keyboardController?.hide()
                    onFinished(textState.value)
                },
                onGo = {
                    keyboardController?.hide()
                    onFinished(textState.value)
                },
                onNext = {
                    keyboardController?.hide()
                    onFinished(textState.value)
                },
                onPrevious = {
                    keyboardController?.hide()
                    onFinished(textState.value)
                },
                onSearch = {
                    keyboardController?.hide()
                    onFinished(textState.value)
                },
                onSend = {
                    keyboardController?.hide()
                    onFinished(textState.value)
                },
            ),

            colors = TextFieldDefaults.textFieldColors(
                focusedTextColor = MaterialTheme.colorScheme.secondary,
                unfocusedTextColor = MaterialTheme.colorScheme.secondary,
                containerColor = MaterialTheme.colorScheme.primary,
                cursorColor = MaterialTheme.colorScheme.secondary
            ),
            textStyle = typography.bodyLarge,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
            value = textState.value,
            onValueChange = {
                textState.value = it
            },
            singleLine = true,
        )
    }
}