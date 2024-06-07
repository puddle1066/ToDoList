package com.paullanducci.todolist.ui.main.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.paullanducci.todolist.ToDoScreens
import com.paullanducci.todolist.ui.theme.typography

@Composable
fun AppMenu(
    menuItems: MutableState<HashMap<Int, String>>,
    expanded: MutableState<Boolean>,
    onClearList: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(5.dp, 50.dp, 0.dp, 0.dp)
    ) {
        DropdownMenu(
            expanded.value,
            {
                expanded.value = false
            },
            Modifier
                .background(MaterialTheme.colorScheme.primary)
                .width(200.dp),
        ) {
            menuItems.value.forEach { (key, value) ->
                DropdownMenuItem(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primary),
                    onClick = {
                        expanded.value = false
                        if (value == ToDoScreens.ClearFinishedList.name) {
                            onClearList()
                        } else {
                            showView(value)
                        }

                    },
                ) {
                    Text(
                        modifier = Modifier.weight(1.5f),
                        text = stringResource(key),
                        style = typography.titleMedium,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                }
            }
        }
    }
}