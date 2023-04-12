package com.paul.todolist.ui.main.todoItemView

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paul.todoList.R
import com.paul.todolist.di.dataStorage.DataStoreProvider
import com.paul.todolist.di.database.RoomDataProvider
import com.paul.todolist.di.database.data.ToDoImageData
import com.paul.todolist.menuOptionSettings
import com.paul.todolist.menuOptionToDoList
import com.paul.todolist.ui.main.common.StandardTopBar
import com.paul.todolist.ui.main.common.drawMenu.DrawerBody
import com.paul.todolist.ui.main.common.drawMenu.drawMenuShape
import com.paul.todolist.ui.main.common.showView
import com.paul.todolist.ui.main.listItemsView.swapList
import com.paul.todolist.ui.theme.ToDoListTheme

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ToDoItemView(model: ToDoItemModel) {

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val addButtonVisibility = remember { mutableStateOf(false) }
    val menuItems = listOf(menuOptionToDoList, menuOptionSettings)

    val title = stringResource(R.string.ToDo_item) + " -  " + model.getListTitle()

    val voiceState by model.voiceToText.state.collectAsState()
    val toDoImageData = remember { mutableStateListOf<ToDoImageData>() }

    model.loadData()

    toDoImageData.clear()
    model.getItemId {
        toDoImageData.swapList(model.getToDoImages(it))
    }

    ToDoListTheme {
        Scaffold(
            topBar = { StandardTopBar(title, scope, scaffoldState) },
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
                        stringResource(R.string.ToDo_Task_description),
                        voiceState,
                        onFinished = {
                            model.todoItem.description = it
                            if (model.todoItem.description.isEmpty()) {
                                addButtonVisibility.value = false
                            } else {
                                addButtonVisibility.value = model.hasDataChanges()
                            }
                        }
                    )
                }

                if (model.isSpeechToTextEnabled) {
                    Spacer(Modifier.height(1.dp))
                    ToDoSpeechButton(model, voiceState)
                }

                ToDoChangeListDropDown(model, addButtonVisibility)

                if (model.isPhotoCaptureEnabled) {
                    Spacer(Modifier.height(1.dp))
                    ToDoItemCameraButton(
                        onPictureTaken = { bitmap ->
                            model.insertToDoImage(bitmap)

                            toDoImageData.clear()
                            model.getItemId { itemID ->
                                toDoImageData.swapList(model.getToDoImages(itemID))

                            }
                        }
                    )
                }

                LazyColumn {
                    itemsIndexed(toDoImageData) { _, item ->
                        ToDoImageitem(item) { imageData: ToDoImageData, delete: Boolean ->
                        }
                    }
                }

                if (addButtonVisibility.value) {
                    ToDoItemAddButton(model, voiceState)
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

