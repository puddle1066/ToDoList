package com.paul.todolist.ui.main.todoListView

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paul.todolist.ToDoScreens
import com.paul.todolist.di.dataStorage.DataStoreProvider
import com.paul.todolist.di.database.RoomDataProvider
import com.paul.todolist.di.database.data.ToDoDataItem
import com.paul.todolist.di.util.ResourcesProvider
import com.paul.todolist.ui.main.MainActivity
import com.paul.todolist.ui.main.common.draganddrop.DragDropColumn
import com.paul.todolist.ui.main.common.showViewWithBackStack
import com.paul.todolist.ui.main.todoListView.buttons.CreateAddButton
import com.paul.todolist.ui.main.todoListView.buttons.CreateDeleteButton
import com.paul.todolist.ui.theme.ToDoListTheme

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ToDoListView(model: ToDoListModel) {

    val deleteList = remember { mutableStateListOf<ToDoDataItem>() }

    val isAddButtonVisible = remember { mutableStateOf(true) }
    val isDeleteButtonVisible = remember { mutableStateOf(false) }
    val isMoveAllowed = remember { mutableStateOf(false) }
    val isDeleteAllowed = remember { mutableStateOf(true) }

    val uiState = model.uiState.collectAsState()

    val startDragIndex = remember { mutableStateOf(-1) }

    //If we don't have a first entry in the list use the first
    //entry in the list as a default
    if (model.isListNotKnown()) {
        MainActivity.listId = model.getAllSortedASC()[0].listId
        model.saveListId(MainActivity.listId)
    }

    model.getToDoList(MainActivity.listId)
    deleteList.clear()

    isAddButtonVisible.value = model.isNormalList()
    isDeleteAllowed.value = model.isFinishedList()
    isMoveAllowed.value = model.isNormalList()

    ToDoListTheme {
        Scaffold(
            topBar = {
                ToDoListTopBar(
                    model.getAllSortedASC(),
                    model.getListTitle()
                ) {
                    MainActivity.listId = it
                    model.saveListId(it)
                    model.getToDoList(it)

                    isAddButtonVisible.value = model.isNormalList()
                    isDeleteAllowed.value = model.isFinishedList()
                    isMoveAllowed.value = model.isNormalList()
                    deleteList.clear()
                }
            },
            floatingActionButton = {
                CreateAddButton(isAddButtonVisible)
                CreateDeleteButton(model, deleteList, isDeleteButtonVisible)
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
                        .clip(RoundedCornerShape(40.dp))
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
                        isMoveAllowed.value,
                        onDragStart = {
                            startDragIndex.value = it
                            uiState.value.get(startDragIndex.value).sequence = 999
                        },
                        onDragEnd = {
                            startDragIndex.value = it
                            uiState.value.get(startDragIndex.value).sequence = it
                            model.updateSequence()

                            model.getToDoList(MainActivity.listId)
                        }
                    ) { item ->

                        //Only display the listId for "All" or "Finished" lists
                        val listName =
                            if (!model.isNormalList())
                                model.getListTitleforId(item.listID) else ""

                        ToDoItem(
                            item,
                            listName,
                            model.getCurrentItemType(),
                            deleteList,
                            isDeleteAllowed,
                            isMoveAllowed,

                            onRowDelete = { todoItem: ToDoDataItem, isSelected: Boolean ->
                                if (isSelected)
                                    deleteList.add(todoItem) else deleteList.remove(todoItem)

                                isDeleteButtonVisible.value = deleteList.size != 0
                            },
                            onRowDetails = { todoItem: ToDoDataItem ->
                                if (model.isFinished(todoItem.finishedDate)) {
                                    MainActivity.itemId = todoItem.itemId
                                    showViewWithBackStack(ToDoScreens.ToDoItemView.name)
                                }
                            }
                        ) { todoItem: ToDoDataItem, _ ->
                            model.setFinishedDate(
                                todoItem.itemId,
                                todoItem.finishedDate
                            )

                            model.getToDoList(MainActivity.listId)

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
    ToDoListView(
        ToDoListModel(
            RoomDataProvider(),
            DataStoreProvider(LocalContext.current),
            ResourcesProvider(LocalContext.current)
        )
    )
}






