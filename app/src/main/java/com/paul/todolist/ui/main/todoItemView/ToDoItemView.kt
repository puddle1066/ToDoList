package com.paul.todolist.ui.main.todoItemView

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paul.todoList.R
import com.paul.todolist.ToDoScreens
import com.paul.todolist.di.dataStorage.DataStoreProvider
import com.paul.todolist.di.database.RoomDataProvider
import com.paul.todolist.menuOptionSettings
import com.paul.todolist.menuOptionToDoList
import com.paul.todolist.ui.main.common.StandardTopBar
import com.paul.todolist.ui.main.common.drawMenu.DrawerBody
import com.paul.todolist.ui.main.common.drawMenu.drawMenuShape
import com.paul.todolist.ui.main.common.showView
import com.paul.todolist.ui.main.common.showViewWithBackStack
import com.paul.todolist.ui.theme.ToDoListTheme
import com.paul.todolist.ui.widgets.AppButton

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "SuspiciousIndentation")
@Composable
fun ToDoItemView(model : ToDoItemModel) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val addButtonVisibility = remember { mutableStateOf(false) }
    val menuItems  = listOf(menuOptionToDoList, menuOptionSettings)
    val title = LocalContext.current.resources.getString(R.string.ToDo_item)+ " -  " + model.getListTitle()

    val voiceState by model.voiceToText.state.collectAsState()

    model.loadData()

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
                    ToDoInputText(
                        model,
                        "ToDo Task description",
                        voiceState,
                        onFinished = {
                          model.todoItem.description = it
                         addButtonVisibility.value = !model.todoItem.description.isEmpty()
                    }
                    )
                }
                Column () {
                    Spacer(Modifier.height(1.dp))
                    Row(modifier = Modifier.padding(10.dp)) {
                        AppButton(
                            onButtonPressed = {
                                if (model.isNewItem()) {
                                    model.insert()
                                } else {
                                    model.update()
                                }
                                voiceState.spokenText = ""
                                showViewWithBackStack(ToDoScreens.ToDoListView.name)
                            },
                            textID = R.string.add_todo,
                            buttonVisible = addButtonVisibility.value
                        )
                    }

                    //Only show this button if speech enabled
                    if (model.canDoSpeechToText) {
                        ToDoSpeechButton(model, voiceState)
                    }

                    Spacer(Modifier.height(1.dp))
                    Row(modifier = Modifier.padding(10.dp)) {
                        AppButton(
                            imageVector = Icons.Filled.Camera,
                            onButtonPressed = { }
                        )
                    }
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

