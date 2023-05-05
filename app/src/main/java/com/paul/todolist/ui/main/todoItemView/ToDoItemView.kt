package com.paul.todolist.ui.main.todoItemView

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paul.todolist.di.dataStorage.DataStoreProvider
import com.paul.todolist.di.database.RoomDataProvider
import com.paul.todolist.di.database.data.ToDoImageData
import com.paul.todolist.ui.main.MainView
import com.paul.todolist.ui.main.listItemsView.swapList
import com.paul.todolist.ui.main.todoItemView.buttons.ToDoItemAddButton
import com.paul.todolist.ui.main.todoItemView.buttons.ToDoSpeechButton
import com.paul.todolist.ui.main.todoItemView.camera.ToDoCameraButtonProcessing
import com.paul.todolist.ui.main.todoItemView.datePicker.ToDoDatePicker
import com.paul.todolist.ui.main.todoItemView.imageList.ToDoImageListItem
import com.paul.todolist.ui.main.todoItemView.inputName.ToDoInputName
import com.paul.todolist.ui.main.todoItemView.listPicker.ToDoChangeListDropDown
import com.paul.todolist.ui.theme.ToDoListTheme

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
            ToDoItemHeader(model)

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
                item {
                    ToDoDatePicker()
                }

                if (model.isSpeechToTextEnabled) {
                    item {
                        Spacer(Modifier.height(1.dp))
                        ToDoSpeechButton(model, voiceState)
                    }
                }

                if (model.isPhotoCaptureEnabled) {
                    itemsIndexed(toDoImageData) { _, item ->
                        ToDoImageListItem(model, item, toDoImageData, addUpdateButtonVisibility)
                    }

                    item {
                        Spacer(Modifier.height(1.dp))
                        ToDoCameraButtonProcessing(model, toDoImageData, addUpdateButtonVisibility)
                    }
                }

                if (addUpdateButtonVisibility.value && model.todoDataItem.description.isNotEmpty()) {
                    item {
                        ToDoItemAddButton(model, voiceState)
                    }
                }
            }
        }
    }
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun Preview() {
    ToDoItemView(
        ToDoItemModel(
            RoomDataProvider(),
            DataStoreProvider(LocalContext.current)
        )
    )
}

