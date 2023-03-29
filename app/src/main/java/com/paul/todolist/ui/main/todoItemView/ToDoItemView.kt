package com.paul.todolist.ui.main.todoItemView

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paul.todoList.R
import com.paul.todolist.ToDoScreens
import com.paul.todolist.di.dataStorage.DataStoreProvider
import com.paul.todolist.di.database.RoomDataProvider
import com.paul.todolist.di.database.data.ToDoDataItem
import com.paul.todolist.menuOptionSettings
import com.paul.todolist.menuOptionToDoList
import com.paul.todolist.ui.main.common.StandardTopBar
import com.paul.todolist.ui.main.common.drawMenu.DrawerBody
import com.paul.todolist.ui.main.common.drawMenu.drawMenuShape
import com.paul.todolist.ui.main.common.showView
import com.paul.todolist.ui.theme.ToDoListTheme
import com.paul.todolist.ui.widgets.InputField


@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "SuspiciousIndentation")
@Composable
fun ToDoItemView(model : ToDoItemModel) {
     val scaffoldState = rememberScaffoldState()
     val scope = rememberCoroutineScope()
     val menuItems  = listOf(menuOptionToDoList, menuOptionSettings)
     var textDescription = ""
     val title = LocalContext.current.resources.getString(R.string.ToDo_item)+ " -  " + model.getListTitle()

    //Load Any Data we Have
    model.getItemId {
        if (it.length > 0) {
            var todoModel = model.getItem(it)
            textDescription = todoModel.description
        }
    }

    fun loadData(todoItem : ToDoDataItem) {
        textDescription = todoItem.description
    }

    ToDoListTheme {
        Scaffold(
            topBar = { StandardTopBar(title, scope, scaffoldState)},
            scaffoldState = scaffoldState,
            drawerGesturesEnabled = true,
            drawerShape = drawMenuShape(menuItems.size),
            drawerContent = {
                DrawerBody(
                    drawItems = menuItems,
                    scaffoldState = scaffoldState,
                    scope = scope
                ) {
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
                        if (textDescription.length > 0) {
                            model.insertToDO(model.selectedlistId, textDescription)
                        }
                        textDescription = ""
                        showView(ToDoScreens.ToDoListView.name)
                    }
                )
                { Icon(Icons.Filled.Add,"") }
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(MaterialTheme.colorScheme.background)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp, 20.dp, 10.dp, 0.dp)
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    InputField(
                        text = textDescription,
                        onTextChanged = {},
                        fieldTitle = "Description",
                        keyboardType = KeyboardType.Text,
                        onFinished = { textDescription = it },
                        clearFieldOnKeyboard = false
                    )
                }
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun Preview() {
    ToDoItemView(ToDoItemModel(RoomDataProvider(), DataStoreProvider(LocalContext.current)))
}

