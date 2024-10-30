package com.paullanducci.todolist.ui.main.todoItemView.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.paullanducci.todolist.R

@Composable
fun ToDoUpdateTextDialog(
    dialogText: String,
    onReplace: () -> Unit,
    onAppend: () -> Unit,
    onIgnore: () -> Unit,
    dialogState: MutableState<Boolean>
) {
    Dialog(onDismissRequest = {
        dialogState.value = false
        onIgnore()
    }) {
        TextDialog(dialogText, onReplace, onAppend, onIgnore, dialogState)
    }
}

@Composable
fun TextDialog(
    dialogText: String,
    onReplace: () -> Unit,
    onAppend: () -> Unit,
    onIgnore: () -> Unit,
    dialogState: MutableState<Boolean>
) {

    var displayText = "\"$dialogText\""

    if (dialogState.value) {
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp),
            elevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary)
            ) {

                Icon(
                    Icons.Default.Info,
                    contentDescription = stringResource(R.string.dialog_info_icon),
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .align(Alignment.CenterHorizontally)
                        .height(50.dp)
                        .width(50.dp),
                    tint = MaterialTheme.colorScheme.secondary
                )

                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = stringResource(R.string.dialog_update_current_text),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 5.dp)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = displayText,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.secondary

                    )
                }

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                        .background(MaterialTheme.colorScheme.primary),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {

                    TextButton(onClick = {
                        onReplace()
                    }) {

                        Text(
                            stringResource(R.string.replace),
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                        )
                    }
                    TextButton(onClick = {
                        onAppend()
                    }) {
                        Text(
                            stringResource(R.string.append),
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.Black,
                            modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                        )
                    }

                    TextButton(onClick = {
                        onIgnore()
                    }) {
                        Text(
                            stringResource(R.string.ignore),
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.Black,
                            modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                        )
                    }
                }
            }
        }
    }
}


@Preview(name = "Custom Dialog")
@Composable
fun ToDoUpdateTextDialogPreview() {
    val dialogState = remember { mutableStateOf(true) }

    ToDoUpdateTextDialog(
        dialogText = "Some Block of text I hav Just recorded",
        onAppend = {},
        onReplace = {},
        onIgnore = {},
        dialogState = dialogState
    )
}
