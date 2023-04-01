package com.paul.todolist.ui.main.todoItemView

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
    voiceState: VoiceToTextParserState,
    onFinished: (String) -> Unit,
    keyboardType : KeyboardType = KeyboardType.Text
) {
    ToDoListTheme {
        var rememberText by remember { mutableStateOf(model.todoItem.description) }
        val keyboardController = LocalSoftwareKeyboardController.current

        if (!voiceState.isSpeaking && !voiceState.spokenText.isEmpty()) {
            rememberText = voiceState.spokenText
            onFinished(rememberText)
        }

        TextField(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .border(width = 4.dp, color = MaterialTheme.colorScheme.surface,shape = RoundedCornerShape(15.dp)),

        label = {
            Text(
                modifier = Modifier.background(MaterialTheme.colorScheme.primary),
                text = "Field Title",
                style = typography.bodyLarge,
                textAlign = TextAlign.Left,
                color = MaterialTheme.colorScheme.secondary)
        },

        shape = RoundedCornerShape(50.dp),

        keyboardActions = KeyboardActions(

            onDone = {
                keyboardController?.hide()
                onFinished(rememberText)
               },
            onGo = {
                keyboardController?.hide()
                onFinished(rememberText)
              },
            onNext = {
                keyboardController?.hide()
                onFinished(rememberText)
              },
            onPrevious = {
                keyboardController?.hide()
                onFinished(rememberText)
              },
            onSearch = {
                keyboardController?.hide()
                onFinished(rememberText)
               },
            onSend = {
                keyboardController?.hide()
                onFinished(rememberText)
                },
        ),

        colors = TextFieldDefaults.textFieldColors(
            focusedTextColor = MaterialTheme.colorScheme.surface,
            unfocusedTextColor = MaterialTheme.colorScheme.surface,
            containerColor = MaterialTheme.colorScheme.primary,
            cursorColor =  MaterialTheme.colorScheme.secondary
        ),
        textStyle = typography.bodyLarge,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
        value = rememberText,
        onValueChange = {
            rememberText = it
        },
        singleLine = true,
    )
  }
}