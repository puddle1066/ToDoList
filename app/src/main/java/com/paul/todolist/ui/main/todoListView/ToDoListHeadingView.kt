package com.paul.todolist.ui.main.todoListView

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.paul.todoList.R
import com.paul.todolist.*
import com.paul.todolist.di.database.data.ListDataItem
import com.paul.todolist.ui.main.listItemsView.ListItemsDropDown
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ToDoListTopBar(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    lists: List<ListDataItem>,
    selected: String,
    onListChanged: (listId: String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(MaterialTheme.colorScheme.primary)
    ) {
        IconButton(
            onClick = {
                run {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                }
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

}