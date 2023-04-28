package com.paul.todolist.ui.main.todoListView

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paul.todolist.ToDoScreens
import com.paul.todolist.di.dataStorage.DataStoreProvider
import com.paul.todolist.di.database.RoomDataProvider
import com.paul.todolist.di.database.data.ToDoDataItem
import com.paul.todolist.ui.main.MainView
import com.paul.todolist.ui.main.common.draganddrop.DragDropColumn
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

    val isAddButtonVisible = remember { mutableStateOf(true) }
    val isDeleteButtonVisible = remember { mutableStateOf(false) }

    val backgroundColor = remember { mutableStateOf(Color.Gray) }

    val isMoveEnabled = remember { mutableStateOf(false) }
    val isDeleteAllowed = remember { mutableStateOf(true) }

    val uiState = model.uiState.collectAsState()

    val startDragIndex = remember { mutableStateOf(-1) }

    backgroundColor.value = MaterialTheme.colorScheme.primary

    //If we don't have a first entry in the list use the first
    //entry in the list as a default
    if (MainView.listId.isBlank()) {
        MainView.listId = model.getAllSortedASC()[0].listId
        model.saveListId(MainView.listId)
    }

    listDataItems.clear()
    listDataItems.swapList(model.getToDoList(MainView.listId))
    isAddButtonVisible.value = model.isNormalList()
    isDeleteAllowed.value = !model.isFullList()
    isMoveEnabled.value = model.isNormalList()

    ToDoListTheme {
        Scaffold(
            topBar = {
                ToDoListTopBar(
                    scope,
                    scaffoldState,
                    model.getAllSortedASC(),
                    model.getListTitle()
                ) {
                    model.saveListId(it)
                    listDataItems.clear()
                    listDataItems.swapList(model.getToDoList(it))
                    isAddButtonVisible.value = model.isNormalList()
                    isDeleteAllowed.value = !model.isFullList()
                    isMoveEnabled.value = model.isNormalList()

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
                CreateAddButton(isAddButtonVisible)
                CreateDeleteButton(model, listDataItems, isDeleteButtonVisible)
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
                    DragDropColumn(
                        items = uiState.value,
                        onSwap = model::swapSections,
                        backgroundColor,
                        isMoveEnabled,
                        onDragStart = {
                            startDragIndex.value = it
                        },
                        onDragEnd = {
                            model.toDoDataItems[startDragIndex.value].display_sequence = it
                            model.updateSequence()
                        }
                    ) { item ->

                        //Only display the listId for "All" or "Finished" lists
                        var listName =
                            if (!model.isNormalList()) model.getListTitleforId(item.listID) else ""

                        ToDoItem(
                            item,
                            listName,
                            isDeleteAllowed.value,
                            backgroundColor,
                            model.todoListItem.type,
                            onRowClick = { todoItem: ToDoDataItem, isSelected: Boolean ->
                                if (todoItem.finishedDate == "0") {
                                    MainView.itemID = todoItem.itemId
                                    MainView.listId = todoItem.listID
                                    showViewWithBackStack(ToDoScreens.ToDoItemView.name)
                                } else {
                                    if (isSelected) {
                                        model.deleteList.add(todoItem)
                                    } else {
                                        model.deleteList.remove(todoItem)
                                    }
                                    isDeleteButtonVisible.value = model.deleteList.size != 0
                                }
                            },
                            onCheckChanged = { todoItem: ToDoDataItem, _ ->
                                model.setFinishedDate(
                                    todoItem.itemId,
                                    todoItem.finishedDate
                                )

                                listDataItems.clear()
                                listDataItems.swapList(model.getToDoList(MainView.listId))
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CreateAddButton(isAddEnabled: MutableState<Boolean>) {
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
                MainView.itemID = ""   //Clear Item ID as its a new item
                showViewWithBackStack(ToDoScreens.ToDoItemView.name)
            }
        )
        { Icon(Icons.Filled.Add, "") }
    }
}

@Composable
fun CreateDeleteButton(
    model: ToDoListModel,
    listDataItems: SnapshotStateList<ToDoDataItem>,
    deleteButtonVisible: MutableState<Boolean>
) {
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
                listDataItems.clear()
                listDataItems.swapList(model.getToDoList(MainView.listId))
                deleteButtonVisible.value = false
            }
        )
        { Icon(Icons.Filled.Delete, "") }
    }

}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MainViewMockLayout() {
    ToDoListView(ToDoListModel(RoomDataProvider(), DataStoreProvider(LocalContext.current)))
}






