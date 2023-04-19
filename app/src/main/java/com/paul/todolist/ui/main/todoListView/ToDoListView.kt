package com.paul.todolist.ui.main.todoListView

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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paul.todolist.ToDoScreens
import com.paul.todolist.di.dataStorage.DataStoreProvider
import com.paul.todolist.di.database.RoomDataProvider
import com.paul.todolist.di.database.data.ToDoDataItem
import com.paul.todolist.ui.main.common.drawMenu.DrawerBody
import com.paul.todolist.ui.main.common.drawMenu.drawMenuShape
import com.paul.todolist.ui.main.common.showViewWithBackStack
import com.paul.todolist.ui.main.listItemsView.swapList
import com.paul.todolist.ui.theme.ToDoListTheme

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ToDoListView(model: ToDoListModel) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val listDataItems = remember { mutableStateListOf<ToDoDataItem>() }
    val isAddEnabled = remember { mutableStateOf(true) }
    val deleteButtonVisible = remember { mutableStateOf(false) }
    val isDeleteAllowed = remember { mutableStateOf(true) }


    //Set a default value for the start of the list if we don't have one..
    model.getListId {
        if (it.isEmpty()) {
            model.setListId(model.getAllSortedASC()[0].listId)
        }

        listDataItems.clear()
        listDataItems.swapList(model.getToDoList(it))
        isAddEnabled.value = !model.showListName()
        isDeleteAllowed.value = model.showFinished
    }

    ToDoListTheme {
        Scaffold(
            topBar = {
                ToDoListTopBar(
                    scope,
                    scaffoldState,
                    model.getAllSortedASC(),
                    model.getListTitle()
                ) {
                    model.setListId(it)
                    listDataItems.clear()
                    listDataItems.swapList(model.getToDoList(it))
                    isAddEnabled.value = !model.showListName()
                }
            },
            scaffoldState = scaffoldState,
            drawerGesturesEnabled = true,
            drawerShape = drawMenuShape(model.menuItems.size),
            drawerContent = {
                DrawerBody(
                    drawItems = model.menuItems,
                    scaffoldState = scaffoldState,
                    scope = scope
                ) {
                    showViewWithBackStack(it.link)
                }
            },

            floatingActionButton = {

                //Add button
                AnimatedVisibility(
                    visible = isAddEnabled.value,
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
                        backgroundColor = MaterialTheme.colorScheme.primary,
                        onClick = {
                            model.setItemId("")             //Clear Item ID as its a new item
                            showViewWithBackStack(ToDoScreens.ToDoItemView.name)
                        }
                    )
                    { Icon(Icons.Filled.Add, "") }
                }

                //Delete Button if only shown on the finished list
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
                                model.deleteItem(it.itemId)
                            }
                            model.deleteList.clear()
                            model.getListId {
                                listDataItems.clear()
                                listDataItems.swapList(model.getToDoList(it))
                            }
                            deleteButtonVisible.value = false
                        }
                    )
                    { Icon(Icons.Filled.Delete, "") }
                }


            }

        ) {
            Column(
                Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxHeight()
                    .fillMaxWidth()
            ) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(7.dp)
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(15.dp)
                        )

                ) {
                    LazyColumn() {
                        itemsIndexed(listDataItems) { _, item ->
                            var itemListName = ""
                            if (model.showListName()) itemListName =
                                model.getListTitleforId(item.listID)

                            ToDoItem(
                                item,
                                itemListName,
                                isDeleteAllowed.value
                            ) { todoItem: ToDoDataItem, isSelected: Boolean ->
                                if (isDeleteAllowed.value) {
                                    if (isSelected) {
                                        model.deleteList.add(item)
                                    } else {
                                        model.deleteList.remove(item)
                                    }
                                    deleteButtonVisible.value = model.deleteList.size != 0
                                } else {
                                    if (todoItem.finishedDate == "0") {
                                        model.setItemId(todoItem.itemId)
                                        model.setListId(todoItem.listID)
                                        showViewWithBackStack(ToDoScreens.ToDoItemView.name)
                                    } else {
                                        model.setFinishedDate(
                                            todoItem.itemId,
                                            todoItem.finishedDate
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MainViewMockLayout() {
    ToDoListView(ToDoListModel(RoomDataProvider(), DataStoreProvider(LocalContext.current)))
}






