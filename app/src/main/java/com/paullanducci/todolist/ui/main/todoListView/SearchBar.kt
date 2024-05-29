package com.paullanducci.todolist.ui.main.todoListView

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paullanducci.todolist.R
import com.paullanducci.todolist.ui.theme.ToDoListTheme
import com.paullanducci.todolist.ui.theme.typography

@Composable
fun SearchBar(onTextChanged: (String) -> Unit) {


    val searchText = remember { mutableStateOf("") }

    ToDoListTheme {
        Row(
            Modifier
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Row(
                Modifier
                    .height(65.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(40.dp))
                    .padding(7.dp)
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(15.dp)
                    ),

                ) {
                TextField(
                    modifier = Modifier
                        .height(65.dp)
                        .weight(0.9f)
                        .background(MaterialTheme.colorScheme.primary),
                    value = searchText.value,
                    textStyle = typography.bodySmall,
                    onValueChange = {
                        searchText.value = it
                        onTextChanged(searchText.value)
                    },

                    colors = TextFieldDefaults.colors(
                        focusedTextColor = MaterialTheme.colorScheme.secondary,
                        unfocusedTextColor = MaterialTheme.colorScheme.secondary,
                        focusedContainerColor = MaterialTheme.colorScheme.primary,
                        unfocusedContainerColor = MaterialTheme.colorScheme.primary,
                        disabledContainerColor = MaterialTheme.colorScheme.primary,
                        cursorColor = MaterialTheme.colorScheme.secondary
                    ),
                )
                IconButton(
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = stringResource(id = R.string.missing_resource),
                        modifier = Modifier
                            .weight(0.1f)
                            .height(50.dp),
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun preview() {
    SearchBar(onTextChanged = {})
}