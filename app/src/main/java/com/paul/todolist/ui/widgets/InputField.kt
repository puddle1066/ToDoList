package com.paul.todolist.ui.widgets

import android.content.res.Configuration
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paul.todolist.ui.theme.ToDoListTheme
import com.paul.todolist.ui.theme.typography

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun InputField(
    text: String,
    onTextChanged: (String) -> Unit,
    fieldTitle: String = "",
    keyboardType: KeyboardType = KeyboardType.Number,
    onFinished: (String) -> Unit,
    clearFieldOnKeyboard: Boolean = true,
) {
    ToDoListTheme {
        var rememberText by remember { mutableStateOf(text) }
        val keyboardController = LocalSoftwareKeyboardController.current

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
                    color = MaterialTheme.colorScheme.secondary
                )
            },

            shape = RoundedCornerShape(50.dp),

            keyboardActions = KeyboardActions(

                onDone = {
                    keyboardController?.hide()
                    onFinished(rememberText)
                    if (clearFieldOnKeyboard) rememberText = ""
                },
                onGo = {
                    keyboardController?.hide()
                    onFinished(rememberText)
                    if (clearFieldOnKeyboard) rememberText = ""
                },
                onNext = {
                    keyboardController?.hide()
                    onFinished(rememberText)
                    if (clearFieldOnKeyboard) rememberText = ""
                },
                onPrevious = {
                    keyboardController?.hide()
                    onFinished(rememberText)
                    if (clearFieldOnKeyboard) rememberText = ""
                },
                onSearch = {
                    keyboardController?.hide()
                    onFinished(rememberText)
                    if (clearFieldOnKeyboard) rememberText = ""
                },
                onSend = {
                    keyboardController?.hide()
                    onFinished(rememberText)
                    if (clearFieldOnKeyboard) rememberText = ""
                }
            ),

            colors = TextFieldDefaults.textFieldColors(
                focusedTextColor = MaterialTheme.colorScheme.surface,
                unfocusedTextColor = MaterialTheme.colorScheme.surface,
                containerColor = MaterialTheme.colorScheme.primary,
                cursorColor = MaterialTheme.colorScheme.secondary
            ),
            textStyle = typography.bodyLarge,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
            value = rememberText,
            onValueChange = {
                rememberText = it
                onTextChanged.invoke(rememberText)
            },
            singleLine = true,
        )

    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MeasurePreview() {
    InputField("", onTextChanged = { }, "Field Title", KeyboardType.Number, onFinished = {})
}

