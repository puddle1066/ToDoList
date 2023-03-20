package com.paul.todolist.ui.widgets

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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

        Column(
            modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.onPrimary),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                label = {
                    Text(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.onPrimary)
                            .clip(
                                RoundedCornerShape(10.dp)
                            ),
                        text = fieldTitle,
                        style = typography.caption,
                        textAlign = TextAlign.Left,
                        color = MaterialTheme.colorScheme.secondary)
                },
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        onFinished("done") },
                    onGo = {  keyboardController?.hide()
                        onFinished("go") },
                    onNext = {  keyboardController?.hide()
                        onFinished("Next") },
                    onPrevious = {  keyboardController?.hide()
                        onFinished("Previous") },
                    onSearch = {  keyboardController?.hide()
                        onFinished("Search") },
                    onSend = {  keyboardController?.hide()
                        onFinished("Send") }
                ),
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = MaterialTheme.colorScheme.secondary,
                    containerColor = MaterialTheme.colorScheme.onPrimary,
                    disabledTextColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = MaterialTheme.colorScheme.onError
                ),
                textStyle = typography.body1,
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
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MeasurePreview() {
    InputField("",  onTextChanged = { },"Field Title", KeyboardType.Number, onFinished = {})
}

