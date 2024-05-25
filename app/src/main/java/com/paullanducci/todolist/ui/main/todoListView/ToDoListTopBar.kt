package com.paullanducci.todolist.ui.main.todoListView

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
import com.paullanducci.todolist.R
import com.paullanducci.todolist.ToDoScreens
import com.paullanducci.todolist.listState_Finished
import com.paullanducci.todolist.ui.main.MainActivity
import com.paullanducci.todolist.ui.main.common.AppMenu
import com.paullanducci.todolist.ui.main.listItemsView.ListItemsDropDown
import com.paullanducci.todolist.ui.theme.ToDoListTheme

@Composable
fun ToDoListTopBar(
    model: ToDoListModel,
    onListChanged: (listId: String) -> Unit
) {
    val expanded = remember { mutableStateOf(false) }
    val menuItems = hashMapOf<Int, String>()
    val menuItemsState = remember { mutableStateOf(menuItems) }

    //Build Menu
    var lists = model.getAllSortedASC()
    var selected = model.getListTitle()
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
                onValueChanged = {
                    onListChanged(it.listId)
                    buildMenuItemsList(menuItems, model.getListType())
                },
                false,
                selected
            )
        }

        buildMenuItemsList(menuItems, model.getListType())
        AppMenu(menuItemsState,
            expanded,
            onClearList = {
                model.removeAllFinished()
                model.getToDoList(MainActivity.listId)
            }
        )

    }
}

fun buildMenuItemsList(menuItems: HashMap<Int, String>, listType: String) {
    menuItems.clear()
    menuItems[R.string.lists] = ToDoScreens.listsView.name

    //Only if finished add clear finished option
    if (listType == listState_Finished) {
        menuItems[R.string.Clear_Finished] = ToDoScreens.Clear_Finished_List.name
    }

    menuItems[R.string.settings] = ToDoScreens.SettingsView.name

}

