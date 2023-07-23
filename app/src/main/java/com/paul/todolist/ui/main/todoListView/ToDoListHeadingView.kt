package com.paul.todolist.ui.main.todoListView

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.paul.todolist.R
import com.paul.todolist.ToDoScreens
import com.paul.todolist.di.database.data.ListDataItem
import com.paul.todolist.ui.main.common.AppMenu
import com.paul.todolist.ui.main.listItemsView.ListItemsDropDown
import com.paul.todolist.ui.theme.ToDoListTheme

@Composable
fun ToDoListTopBar(
    lists: List<ListDataItem>,
    selected: String,
    onListChanged: (listId: String) -> Unit
) {
    val expanded = remember { mutableStateOf(false) }
    val menuItems = hashMapOf<Int, String>(
        R.string.lists to ToDoScreens.listsView.name,
        R.string.settings to ToDoScreens.SettingsView.name
    )

    ToDoListTheme {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(MaterialTheme.colorScheme.primary)
        ) {

            IconButton(
                onClick = {
                    expanded.value = !expanded.value
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = stringResource(id = R.string.missing_resource),
                    modifier = Modifier
                        .width(30.dp)
                        .height(50.dp)
                        .background(MaterialTheme.colorScheme.primary),
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
            Spacer(Modifier.width(10.dp))

            ListItemsDropDown(
                lists,
                onValueChanged = { onListChanged(it.listId) },
                false,
                selected
            )
        }

        AppMenu(menuItems, expanded)

    }
}