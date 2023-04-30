package com.paul.todolist.ui.main.listItemsView

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paul.todoList.R
import com.paul.todolist.di.dataStorage.DataStoreProvider
import com.paul.todolist.di.database.RoomDataProvider
import com.paul.todolist.di.database.data.ListDataItem
import com.paul.todolist.ui.main.common.StandardTopBar
import com.paul.todolist.ui.main.common.drawMenu.DrawerBody
import com.paul.todolist.ui.main.common.drawMenu.drawMenuShape
import com.paul.todolist.ui.main.common.showViewWithBackStack
import com.paul.todolist.ui.theme.ToDoListTheme
import com.paul.todolist.ui.widgets.InputField

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ListItemsView(model: ListItemsModel) {

    val listDataItems = remember { mutableStateListOf<ListDataItem>() }
    val deleteButtonVisible = remember { mutableStateOf(false) }
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    ToDoListTheme {
        Scaffold(
            topBar = {
                StandardTopBar(
                    stringResource(R.string.lists),
                    coroutineScope,
                    scaffoldState
                )
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
            drawerGesturesEnabled = true,
            drawerShape = drawMenuShape(model.menuItems.size),
            drawerContent = {
                DrawerBody(
                    drawItems = model.menuItems,
                    scaffoldState = scaffoldState,
                    scope = coroutineScope
                ) {
                    showViewWithBackStack(it.link)
                }
            }

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
    ListItemsView(ListItemsModel(RoomDataProvider(), DataStoreProvider(LocalContext.current)))
}


