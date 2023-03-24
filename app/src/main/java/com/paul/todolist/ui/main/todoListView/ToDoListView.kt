package com.paul.todolist.ui.main.todoListView

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paul.todolist.ToDoScreens
import com.paul.todolist.di.database.RoomDataProvider
import com.paul.todolist.di.database.data.ToDoDataItem
import com.paul.todolist.ui.main.common.UiState
import com.paul.todolist.ui.main.common.drawMenu.DrawerBody
import com.paul.todolist.ui.main.common.drawMenu.drawMenuShape
import com.paul.todolist.ui.main.common.showView
import com.paul.todolist.ui.main.listItemsView.swapList
import com.paul.todolist.ui.theme.ToolboxTheme

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ToDoListView(uiState: UiState, model : ToDoListModel) {
        val scaffoldState = rememberScaffoldState()
        val scope = rememberCoroutineScope()
        var selectedListId : String = "n/a"

        val listDataItems = remember { mutableStateListOf<ToDoDataItem>() }

        //TODO last listId should be stored and reloaded on restart
        selectedListId = model.getAllSortedASC()[0].listId


        listDataItems.swapList(model.getToDoList(selectedListId))

        ToolboxTheme {
            Scaffold(
            topBar = {
                ToDoListTopBar(scope,
                    scaffoldState,
                    model.getAllSortedASC(),
                    onListChanged ={
                        selectedListId = it
                        var list = model.getToDoList(listId = selectedListId)
                        listDataItems.swapList(list)
                    })},
                scaffoldState = scaffoldState,
                drawerGesturesEnabled = true,
                drawerShape = drawMenuShape(model.menuItems.size),
                drawerContent = {
                    DrawerBody(
                        drawItems = model.menuItems,
                        scaffoldState = scaffoldState,
                        scope = scope
                    ) {
                        //Launch Options
                        showView(it.link)
                    }
            },
           floatingActionButton = {
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
                            uiState.listId = selectedListId
                            uiState.itemId=""
                            showView(ToDoScreens.ToDoItemView.name)
                        }
                    )
                    { Icon(Icons.Filled.Add,"")}
                }

            ) {
            Column(Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxHeight()
                .fillMaxWidth()
            ) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(10.dp)
                        .border(width = 2.dp, color = MaterialTheme.colorScheme.surface,shape = RoundedCornerShape(15.dp))

                ) {
                LazyColumn() {
                    itemsIndexed(listDataItems) { _, item ->
                        ToDoItem(item, { item: ToDoDataItem ->
                            uiState.listId = selectedListId
                            uiState.itemId=item.itemId
                            showView(ToDoScreens.ToDoItemView.name)
                        })
                    }
                }
                }
            }}}
}

@Preview
    @Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
    @Composable
    fun MainViewMockLayout() {
        ToDoListView(UiState(),ToDoListModel(RoomDataProvider()))
    }






