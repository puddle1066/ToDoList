package com.paullanducci.todolist.ui.main.todoListView

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paullanducci.todolist.LAST_LIST_ID
import com.paullanducci.todolist.ToDoScreens
import com.paullanducci.todolist.di.database.RoomDataProvider
import com.paullanducci.todolist.di.database.data.ToDoDataItem
import com.paullanducci.todolist.ui.main.MainActivity
import com.paullanducci.todolist.ui.main.common.draganddrop.DragDropColumn
import com.paullanducci.todolist.ui.main.common.showViewWithBackStack
import com.paullanducci.todolist.ui.main.todoListView.buttons.CreateAddButton
import com.paullanducci.todolist.ui.main.todoListView.buttons.CreateDeleteButton
import com.paullanducci.todolist.ui.theme.ToDoListTheme
import com.paullanducci.todolist.util.ResourcesProvider

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ToDoListView(model: ToDoListModel) {

    val deleteList = remember { mutableStateListOf<ToDoDataItem>() }

    val isAddButtonVisible = remember { mutableStateOf(true) }
    val isDeleteButtonVisible = remember { mutableStateOf(false) }
    val isMoveAllowed = remember { mutableStateOf(false) }
    val isDeleteAllowed = remember { mutableStateOf(true) }

    val uiState = model.uiState.collectAsState()

    val startDragIndex = remember { mutableIntStateOf(-1) }
    val alertThresholds = model.getAlertValues()

    //If we don't have a first entry in the list use the first
    //entry in the list as a default
    if (model.isListNotKnown()) {
        MainActivity.listId = model.getAllSortedASC()[0].listId
        model.setOption(LAST_LIST_ID, MainActivity.listId)
    }

    model.getToDoList(MainActivity.listId)
    deleteList.clear()

    isAddButtonVisible.value = model.isNormalList()
    isDeleteAllowed.value = model.isFinishedList()

    if (model.isFinishedList() || model.isFullList()) {
        isMoveAllowed.value = false
    } else {
        isMoveAllowed.value = true
    }

    ToDoListTheme {
        Scaffold(
            topBar = {
                ToDoListTopBar(

                    model
                ) {
                    MainActivity.listId = it
                    model.setOption(LAST_LIST_ID, it)
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
                        isMoveAllowed,
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
                            alertThresholds,
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
            ResourcesProvider(LocalContext.current)
        )
    )
}






