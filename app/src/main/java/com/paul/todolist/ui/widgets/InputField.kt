package com.paul.todolist.ui.widgets

import android.content.res.Configuration
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paul.todolist.ui.theme.ToolboxTheme
import com.paul.todolist.ui.theme.typography

/* Example of how to use this widget

                Row (modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically)
                {
                    InputField(
                        text = "",
                        onTextChanged = {
                            measureModel.inputtext = it
                        },
                        "Input Title"
                    )
                }
*/


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun InputField(
    text: String,
    onTextChanged: (String) -> Unit,
    fieldTitle: String = "",
    keyboardType : KeyboardType = KeyboardType.Number,
    onFinished: (String) -> Unit,
) {
    ToolboxTheme {
        var rememberText by remember { mutableStateOf(text) }
        val keyboardController = LocalSoftwareKeyboardController.current

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .border(width = 4.dp, color = MaterialTheme.colorScheme.surface,shape = RoundedCornerShape(15.dp)),

            label = {
                Text(
                    modifier = Modifier.background(MaterialTheme.colorScheme.primary),
                    text = fieldTitle,
                    style = typography.bodyLarge,
                    textAlign = TextAlign.Left,
                    color = MaterialTheme.colorScheme.secondary)
            },

            shape = RoundedCornerShape(50.dp),

            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    onFinished(rememberText)
                    rememberText = ""},
                onGo = {  keyboardController?.hide()
                    onFinished(rememberText)
                    rememberText = ""},
                onNext = {  keyboardController?.hide()
                    onFinished(rememberText)
                    rememberText = ""},
                onPrevious = {  keyboardController?.hide()
                    onFinished(rememberText)
                    rememberText = ""},
                onSearch = {  keyboardController?.hide()
                    onFinished(rememberText)
                    rememberText = "" },
                onSend = {  keyboardController?.hide()
                    onFinished(rememberText)
                    rememberText = ""}
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
    InputField("",  onTextChanged = { },"Field Title", KeyboardType.Number, onFinished = {})
}

