package com.paul.todolist.ui.main.todoListView

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.util.Log
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paul.todolist.ToDoScreens
import com.paul.todolist.di.dataStorage.DataStoreProvider
import com.paul.todolist.di.database.RoomDataProvider
import com.paul.todolist.di.database.data.ToDoDataItem
import com.paul.todolist.ui.main.common.drawMenu.DrawerBody
import com.paul.todolist.ui.main.common.drawMenu.drawMenuShape
import com.paul.todolist.ui.main.common.showView
import com.paul.todolist.ui.main.common.showViewWithBackStack
import com.paul.todolist.ui.main.listItemsView.swapList
import com.paul.todolist.ui.theme.ToDoListTheme

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ToDoListView(model : ToDoListModel) {
        val scaffoldState = rememberScaffoldState()
        val scope = rememberCoroutineScope()
        val listDataItems = remember { mutableStateListOf<ToDoDataItem>() }

        //Set a default value for the start of the list if we don't have one..
        model.getListId { Log.e("TAG","Value {$it}") }

        model.getListId {
            if (it.isEmpty()) {
                model.setListId(model.getAllSortedASC()[0].listId)
                Log.e("ToDoListView","Setting default list to  $model.getAllSortedASC()[0].selectedlistId")
            } else {
                listDataItems.clear()
                listDataItems.swapList(model.getToDoList(it))
            }
        }

        ToDoListTheme {
            Scaffold(
            topBar = {
                ToDoListTopBar(scope,
                    scaffoldState,
                    model.getAllSortedASC(),
                    model.getListTitle()
                ) {
                    model.setListId(it)
                    listDataItems.clear()
                    listDataItems.swapList(model.getToDoList(it))
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
                            model.setItemId("")             //Clear Item ID as its a new item
                            showViewWithBackStack(ToDoScreens.ToDoItemView.name)
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
                            model.setItemId(item.itemId)
                            model.setListId(item.listID)
                            Log.e("ToDoListView"," Saving - selectedlistId"+item.listID+"ItemId "+item.itemId)
                            showViewWithBackStack(ToDoScreens.ToDoItemView.name)
                        })
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






