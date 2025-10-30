package com.paullanducci.todolist.ui.main.listItemsView

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddTask
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Update
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.paullanducci.todolist.R
import com.paullanducci.todolist.ToDoScreens
import com.paullanducci.todolist.di.database.data.ListDataItem
import com.paullanducci.todolist.di.database.data.ListDataItemInitail
import com.paullanducci.todolist.ui.main.common.StandardTopBar
import com.paullanducci.todolist.ui.theme.ToDoListTheme
import com.paullanducci.todolist.ui.widgets.InputField
import com.paullanducci.todolist.util.LockScreenOrientation

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ListItemsView(model: ListItemsModel) {

    val listDataItems = remember { mutableStateListOf<ListDataItem>() }
    val selected = remember { mutableStateOf(ListDataItemInitail()) }
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

                EditEntry(model, listDataItems, selected)

                Spacer(Modifier.height(20.dp))

                DrawListOfGroups(model, listDataItems, selected)
            }
        }
    }
}

@Composable
fun EditEntry(
    model: ListItemsModel,
    listDataItems: SnapshotStateList<ListDataItem>,
    selected: MutableState<ListDataItem>
) {
    val openDialog = remember { mutableStateOf(false) }
    val hasTitle = remember { mutableStateOf(false) }
    val titleText = remember { mutableStateOf("") }

    if (openDialog.value) {
        ListItemsDeleteDialog(openDialog)
    }

    hasTitle.value = titleText.value.isNotEmpty()

    if (nonSelected(selected)) {
        listDataItems.clear()
        listDataItems.swapList(model.getUserDefinedLists())
    } else {
        titleText.value = selected.value.title
    }

    Box(
        Modifier
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(15.dp)
            )
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth()
    ) {
        Column {
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .padding(10.dp)
            ) {
                InputField(
                    titleText,
                    fieldTitle = stringResource(id = R.string.input_list_name),
                    keyboardType = KeyboardType.Text,
                    onFinished = {
                        if (isSelected(selected)) {
                            updateTask(model, listDataItems, selected, titleText)
                        } else {
                            if (haveTitle(selected)) {
                                insertTask(model, listDataItems, titleText)
                            }
                        }

                        listDataItems.clear()
                        listDataItems.swapList(model.getUserDefinedLists())
                        selected.value.listId = ""
                        selected.value.title = ""
                        titleText.value = ""
                    })
            }

            if (hasTitle.value || isSelected(selected)) {
                ShowButtons(selected, onPressed = {
                    when (it) {
                        R.string.add_button -> {
                            insertTask(model, listDataItems, titleText)
                        }

                        R.string.update_button -> {
                            updateTask(model, listDataItems, selected, titleText)
                        }

                        R.string.delete_button -> {
                            deleteTask(model, listDataItems, selected, openDialog)
                        }
                    }
                    titleText.value = ""
                    selected.value.listId = ""
                    selected.value.title = ""
                    listDataItems.clear()
                    listDataItems.swapList(model.getUserDefinedLists())
                })
            }
        }
    }
}

fun nonSelected(selected: MutableState<ListDataItem>): Boolean {
    return selected.value.listId.isEmpty()
}

fun isSelected(selected: MutableState<ListDataItem>): Boolean {
    return !selected.value.listId.isEmpty()
}

fun haveTitle(selected: MutableState<ListDataItem>): Boolean {
    return !selected.value.title.isEmpty()
}

fun insertTask(
    model: ListItemsModel,
    listDataItems: SnapshotStateList<ListDataItem>,
    title: MutableState<String>
) {
    if (!title.value.isEmpty()) {
        model.insertListItem(title.value)
        listDataItems.clear()
        listDataItems.swapList(model.getUserDefinedLists())
    }
}

fun updateTask(
    model: ListItemsModel,
    listDataItems: SnapshotStateList<ListDataItem>,
    selected: MutableState<ListDataItem>,
    title: MutableState<String>
) {
    selected.value.title = title.value
    model.updateListItem(selected.value)
    listDataItems.clear()
    listDataItems.swapList(model.getUserDefinedLists())
}

fun deleteTask(
    model: ListItemsModel,
    listDataItems: SnapshotStateList<ListDataItem>,
    selected: MutableState<ListDataItem>,
    openDialog: MutableState<Boolean>
) {
    if (model.getListCount(selected.value.listId) == 0) {
        model.deleteListId(selected.value.listId)
        listDataItems.clear()
        listDataItems.swapList(model.getUserDefinedLists())
    } else {
        openDialog.value = true
    }
}

@Composable
fun ShowButtons(
    selected: MutableState<ListDataItem>,
    onPressed: (Int) -> Unit
) {
    val iconWeight = (LocalConfiguration.current.screenWidthDp.dp - 40.dp) / 3

    Row(
        Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(10.dp, 20.dp, 10.dp, 20.dp),
    ) {
        if (nonSelected(selected)) {
            IconButton(
                Icons.Default.AddTask,
                onClick = {
                    onPressed(R.string.add_button)
                },
                iconWeight,
                stringResource(R.string.add_button)
            )
        } else {
            IconButton(
                Icons.Default.Update,
                onClick = {
                    onPressed(R.string.update_button)
                },
                iconWeight,
                stringResource(R.string.update_button)
            )
            IconButton(
                Icons.Default.DeleteForever,
                onClick = {
                    onPressed(R.string.delete_button)
                },
                iconWeight,
                stringResource(R.string.delete_button)
            )
        }
    }
}

@Composable
fun DrawListOfGroups(
    model: ListItemsModel,
    listDataItems: SnapshotStateList<ListDataItem>,
    selected: MutableState<ListDataItem>
) {
    listDataItems.swapList(model.getUserDefinedLists())
    Box(
        Modifier
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(15.dp)
            )
            .fillMaxWidth()
            .height(550.dp)
    ) {
        LazyColumn {
            itemsIndexed(listDataItems) { _, item ->
                val count = model.getListCount(item.listId)
                ListItem(item, count, selected) { selectedListData: ListDataItem ->
                    selected.value = selectedListData
                    listDataItems.clear()
                    listDataItems.swapList(model.getUserDefinedLists())
                }
            }
        }
    }
}

fun <T> SnapshotStateList<T>.swapList(newList: List<T>) {
    clear()
    addAll(newList)
}
