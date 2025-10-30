package com.paullanducci.todolist.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.paullanducci.todolist.ui.theme.ToDoListTheme
import com.paullanducci.todolist.ui.theme.typography


@Composable
fun InputField(
    text: MutableState<String>,
    fieldTitle: String = "",
    keyboardType: KeyboardType = KeyboardType.Number,
    onFinished: () -> Unit,
    clearFieldOnKeyboard: Boolean = true
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }

    ToDoListTheme {
        TextField(
            modifier = Modifier
                .focusRequester(focusRequester)
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
                    if (clearFieldOnKeyboard) text.value = ""
                    onFinished()
                },
                onGo = {
                    keyboardController?.hide()
                    if (clearFieldOnKeyboard) text.value = ""
                    onFinished()
                },
                onNext = {
                    keyboardController?.hide()
                    if (clearFieldOnKeyboard) text.value = ""
                    onFinished()
                },
                onPrevious = {
                    keyboardController?.hide()
                    if (clearFieldOnKeyboard) text.value = ""
                    onFinished()
                },
                onSearch = {
                    keyboardController?.hide()
                    if (clearFieldOnKeyboard) text.value = ""
                    onFinished()
                },
                onSend = {
                    keyboardController?.hide()
                    if (clearFieldOnKeyboard) text.value = ""
                    onFinished()
                }
            ),

            colors = TextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.secondary,
                unfocusedTextColor = MaterialTheme.colorScheme.secondary,
                focusedContainerColor = MaterialTheme.colorScheme.primary,
                unfocusedContainerColor = MaterialTheme.colorScheme.primary,
                disabledContainerColor = MaterialTheme.colorScheme.primary,
                cursorColor = MaterialTheme.colorScheme.secondary
            ),
            textStyle = typography.bodyLarge,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
            value = text.value,
            onValueChange = {
                text.value = it
            },
            singleLine = true,
        )

        if (text.value.isNotEmpty()) {
            LaunchedEffect(Unit) {
                focusRequester.requestFocus()
            }
        }
    }
}