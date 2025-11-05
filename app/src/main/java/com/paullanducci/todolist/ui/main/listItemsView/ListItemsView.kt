package com.paullanducci.todolist.ui.main.listItemsView

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.paullanducci.todolist.R
import com.paullanducci.todolist.ToDoScreens
import com.paullanducci.todolist.di.database.data.ListDataItem
import com.paullanducci.todolist.di.database.data.listDataItemInitail
import com.paullanducci.todolist.ui.main.common.StandardTopBar
import com.paullanducci.todolist.ui.theme.ToDoListTheme
import com.paullanducci.todolist.util.LockScreenOrientation

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ListItemsView(model: ListItemsModel) {

    val listDataItems = remember { mutableStateListOf<ListDataItem>() }
    val selected = remember { mutableStateOf(listDataItemInitail()) }
    val title = remember { mutableStateOf("") }

    val menuItems = hashMapOf<Int, String>(
        R.string.ToDo_Lists to ToDoScreens.ToDoListView.name,
        R.string.settings to ToDoScreens.SettingsView.name
    )

    ToDoListTheme {
        Scaffold(
            topBar = {
                StandardTopBar(stringResource(R.string.lists), menuItems)
            },
            modifier = Modifier
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {
            LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .padding(10.dp, 20.dp, 10.dp, 20.dp),
            ) {

                Spacer(Modifier.height(30.dp))

                ListItemsEditEntry(
                    model,
                    title,
                    selected,
                    onTextChanged = {
                        title.value = it
                        if (it.isEmpty()) {
                            title.value = ""
                            selected.value.listId = ""
                            selected.value.title = ""
                            listDataItems.clear()
                            listDataItems.swapList(model.getUserDefinedLists())
                        }
                    },
                    onFinished = {
                        title.value = ""
                        selected.value.listId = ""
                        selected.value.title = ""
                        listDataItems.clear()
                        listDataItems.swapList(model.getUserDefinedLists())
                    }
                )

                Spacer(Modifier.height(20.dp))

                ListItemsDrawGroupsList(
                    model,
                    listDataItems,
                    selected,
                    onListSelected = {
                        selected.value = it
                        title.value = it.title
                    })
            }
        }
    }
}


fun nonSelected(selected: MutableState<ListDataItem>): Boolean {
    return selected.value.listId.isEmpty()
}

fun insertTask(
    model: ListItemsModel,
    title: String
) {
    if (!title.isEmpty()) {
        model.insertListItem(title)
    }
}

fun updateTask(
    model: ListItemsModel,
    selected: MutableState<ListDataItem>,
    title: String
) {
    selected.value.title = title
    model.updateListItem(selected.value)
}

fun deleteTask(
    model: ListItemsModel,
    selected: MutableState<ListDataItem>,
    openDialog: MutableState<Boolean>
) {
    if (model.getListCount(selected.value.listId) == 0) {
        model.deleteListId(selected.value.listId)
    } else {
        openDialog.value = true
    }
}

fun <T> SnapshotStateList<T>.swapList(newList: List<T>) {
    clear()
    addAll(newList)
}
