package com.paul.todolist.ui.main.listItemsView

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paul.todoList.R
import com.paul.todolist.ToDoList
import com.paul.todolist.di.database.RoomDataProvider
import com.paul.todolist.di.database.data.ListDataItem
import com.paul.todolist.menuOptionSettings
import com.paul.todolist.menuOptionToDoList
import com.paul.todolist.ui.main.common.StandardTopBar
import com.paul.todolist.ui.main.common.drawMenu.DrawerBody
import com.paul.todolist.ui.main.common.drawMenu.drawMenuShape
import com.paul.todolist.ui.theme.ToolboxTheme
import com.paul.todolist.ui.widgets.InputField

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ListItemsView(model : ListItemsModel) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val menuItems = listOf(menuOptionToDoList, menuOptionSettings)
    var textValue = ""
    var myList = remember { mutableStateListOf<ListDataItem>() }

    ToolboxTheme {
        Scaffold(
            topBar = {
                StandardTopBar(R.string.lists, scope, scaffoldState)
            },
            scaffoldState = scaffoldState,
            drawerGesturesEnabled = true,
            drawerShape = drawMenuShape(menuItems.size),
            drawerContent = {
                DrawerBody(
                    drawItems = menuItems,
                    scaffoldState = scaffoldState,
                    scope = scope
                ) {
                    ToDoList.NavHostController.navigate(it.link) {
                        ToDoList.NavHostController.graph.startDestinationId
                        launchSingleTop = true
                    }
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
                    onTextChanged = {textValue = it},
                    fieldTitle = "Input List Name",
                    keyboardType = KeyboardType.Text,
                    onFinished = {
                        model.insertList(textValue)
                        myList.swapList(model.getList())
                    }
                )

                Spacer(Modifier.height(20.dp))

                myList.swapList(model.getList())
                Box(
                    Modifier
                        .border(width = 4.dp, color = MaterialTheme.colorScheme.surface,shape = RoundedCornerShape(15.dp))
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    LazyColumn{
                        itemsIndexed(myList) { _, item ->
                            ListListItem(item) {}
                        }
                        }
                    }
                }
            }
        }
    }

fun <T> SnapshotStateList<T>.swapList(newList: List<T>){
    clear()
    addAll(newList)
}


@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun Preview() {
    ListItemsView(ListItemsModel(RoomDataProvider()))
}


