package com.paullanducci.todolist.ui.main.listItemsView

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paullanducci.todolist.R
import com.paullanducci.todolist.ToDoScreens
import com.paullanducci.todolist.di.database.RoomDataProvider
import com.paullanducci.todolist.di.database.data.ListDataItem
import com.paullanducci.todolist.ui.main.common.StandardTopBar
import com.paullanducci.todolist.ui.theme.ToDoListTheme
import com.paullanducci.todolist.ui.widgets.InputField

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ListItemsView(model: ListItemsModel) {

    val listDataItems = remember { mutableStateListOf<ListDataItem>() }
    val deleteButtonVisible = remember { mutableStateOf(false) }
    val scaffoldState = rememberScaffoldState()

    val menuItems = hashMapOf<Int, String>(
        R.string.ToDo_Lists to ToDoScreens.ToDoListView.name,
        R.string.settings to ToDoScreens.SettingsView.name
    )

    ToDoListTheme {
        Scaffold(
            topBar = {
                StandardTopBar(stringResource(R.string.lists), menuItems)
            },
            floatingActionButton = {
                AnimatedVisibility(
                    visible = deleteButtonVisible.value,
                    enter = fadeIn() + slideInHorizontally(),
                    exit = fadeOut() + slideOutHorizontally()
                ) {
                    FloatingActionButton(
                        modifier = Modifier
                            .width(90.dp)
                            .height(70.dp)
                            .padding(
                                start = 10.dp,
                                end = 10.dp
                            ),
                        backgroundColor = MaterialTheme.colorScheme.error,
                        onClick = {
                            model.deleteList.forEach {
                                model.deleteListId(it.listId)
                            }
                            model.deleteList.clear()
                            listDataItems.swapList(model.getListOfLists())
                            deleteButtonVisible.value = false
                        }
                    )
                    { Icon(Icons.Filled.Delete, "") }
                }
            },
            scaffoldState = scaffoldState,
            modifier = Modifier
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .padding(10.dp, 20.dp, 10.dp, 20.dp),

                ) {
                InputField(
                    text = "",
                    onTextChanged = {},
                    fieldTitle = stringResource(id = R.string.input_list_name),
                    keyboardType = KeyboardType.Text,
                    onFinished = {
                        if (!it.isEmpty()) {
                            model.insertListItem(it)
                            listDataItems.clear()
                            listDataItems.swapList(model.getListOfLists())
                        }
                    }
                )

                Spacer(Modifier.height(20.dp))

                listDataItems.swapList(model.getListOfLists())
                Box(
                    Modifier
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(15.dp)
                        )
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    LazyColumn {
                        itemsIndexed(listDataItems) { _, item ->
                            val count = model.getListCount(item.listId)
                            ListItem(item, count) { listData: ListDataItem, delete: Boolean ->
                                if (delete) {
                                    model.deleteList.add(listData)
                                } else {
                                    model.deleteList.remove(listData)
                                }
                                deleteButtonVisible.value = model.deleteList.size != 0
                            }
                        }
                    }
                }
            }
        }
    }
}

fun <T> SnapshotStateList<T>.swapList(newList: List<T>) {
    clear()
    addAll(newList)
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ListItemsViewPreview() {
    ListItemsView(
        ListItemsModel(
            RoomDataProvider()
        )
    )
}


