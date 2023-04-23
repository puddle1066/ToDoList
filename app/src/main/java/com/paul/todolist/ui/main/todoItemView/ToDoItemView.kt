package com.paul.todolist.ui.main.todoItemView

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paul.todoList.R
import com.paul.todolist.ToDoScreens
import com.paul.todolist.di.dataStorage.DataStoreProvider
import com.paul.todolist.di.database.RoomDataProvider
import com.paul.todolist.di.database.data.ToDoImageData
import com.paul.todolist.ui.main.MainView
import com.paul.todolist.ui.main.common.showViewWithBackStack
import com.paul.todolist.ui.main.listItemsView.swapList
import com.paul.todolist.ui.theme.ToDoListTheme
import com.paul.todolist.ui.theme.typography
import com.paul.todolist.util.decodeBase64
import com.paul.todolist.util.encodeTobase64
import com.paul.todolist.util.rotate

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ToDoItemView(model: ToDoItemModel) {

    val addUpdateButtonVisibility = remember { mutableStateOf(false) }

    val voiceState by model.voiceToText.state.collectAsState()
    val toDoImageData = remember { mutableStateListOf<ToDoImageData>() }

    model.loadData()

    toDoImageData.clear()
    toDoImageData.swapList(model.getToDoImages(MainView.itemID))

    ToDoListTheme {
        Column() {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    modifier = Modifier.padding(40.dp, 10.dp, 10.dp, 10.dp),
                    text = stringResource(R.string.ToDo_item) + " -  " + model.getListTitle(),
                    style = typography.titleLarge,
                    color = MaterialTheme.colorScheme.secondary,
                )
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            )
            {
                item {
                    ToDoInputName(model, addUpdateButtonVisibility, voiceState)
                }

                item {
                    ToDoChangeListDropDown(model, addUpdateButtonVisibility)
                }
//TODO Implement
//                item {
//                    NumberPicker(
//                        state = remember { mutableStateOf(9) },
//                        range = 0..10
//                    )
//                }

                if (model.isSpeechToTextEnabled) {
                    item {
                        Spacer(Modifier.height(1.dp))
                        ToDoSpeechButton(model, voiceState)
                    }
                }

                if (model.isPhotoCaptureEnabled && !model.isNewItem()) {
                    itemsIndexed(toDoImageData) { _, item ->
                        ToDoImageitem(
                            item,
                            onDeleteClick = {
                                decodeBase64(it.image)?.let {
                                    model.addedBitmapList.remove(it)
                                }
                                model.deleteImage(it.key)

                                toDoImageData.clear()
                                toDoImageData.swapList(model.getToDoImages(MainView.itemID))
                                addUpdateButtonVisibility.value = true
                            },
                            onExpandClick = {
                                MainView.image = it.image
                                showViewWithBackStack(ToDoScreens.ImageItemView.name)
                            }
                        )
                    }
                }

                if (model.isPhotoCaptureEnabled && !model.isNewItem()) {
                    item {
                        Spacer(Modifier.height(1.dp))
                        ToDoItemCameraButton { bitmap ->

                            //rotate the bitmap as the screen is fixed portrait
                            val rotated = bitmap.rotate(90f)

                            model.addedBitmapList.add(rotated)
                            addUpdateButtonVisibility.value = true
                            encodeTobase64(rotated)?.let { bitmapString ->
                                ToDoImageData(
                                    "", "",
                                    bitmapString
                                )
                            }?.let { it -> toDoImageData.add(it) }
                        }
                    }
                }

                if (addUpdateButtonVisibility.value) {
                    item {
                        ToDoItemAddButton(model, voiceState)
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

