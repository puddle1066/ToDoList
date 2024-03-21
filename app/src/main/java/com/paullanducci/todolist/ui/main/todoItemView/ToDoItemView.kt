package com.paullanducci.todolist.ui.main.todoItemView

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paullanducci.todolist.di.database.RoomDataProvider
import com.paullanducci.todolist.di.database.data.ToDoImageData
import com.paullanducci.todolist.ui.main.MainActivity
import com.paullanducci.todolist.ui.main.listItemsView.swapList
import com.paullanducci.todolist.ui.main.todoItemView.buttons.ToDoCameraButtonProcessing
import com.paullanducci.todolist.ui.main.todoItemView.buttons.ToDoItemAddButton
import com.paullanducci.todolist.ui.main.todoItemView.buttons.ToDoSpeechButton
import com.paullanducci.todolist.ui.main.todoItemView.datePicker.ToDoDueDate
import com.paullanducci.todolist.ui.main.todoItemView.imageList.ToDoImageListItem
import com.paullanducci.todolist.ui.main.todoItemView.imageList.ToDoNewImage
import com.paullanducci.todolist.ui.main.todoItemView.inputName.ToDoInputName
import com.paullanducci.todolist.ui.main.todoItemView.listPicker.ToDoChangeListDropDown
import com.paullanducci.todolist.ui.main.todoItemView.textView.LastUpdatedText
import com.paullanducci.todolist.ui.theme.ToDoListTheme

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ToDoItemView(model: ToDoItemModel) {

    val addUpdateButtonVisibility = remember { mutableStateOf(false) }

    val voiceState by model.voiceToText.state.collectAsState()
    val toDoImageData = remember { mutableStateListOf<ToDoImageData>() }
    val toDoImagesNew = remember { mutableStateListOf<Bitmap>() }

    toDoImageData.clear()
    toDoImageData.swapList(model.getToDoImages(MainActivity.itemId))

    model.loadData()

    ToDoListTheme {
        Column {
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
                    ToDoDueDate(model, addUpdateButtonVisibility)
                }

                if (model.isSpeechToTextEnabled) {
                    item {
                        Spacer(Modifier.height(1.dp))
                        ToDoSpeechButton(model, voiceState)
                    }
                }

                if (model.isPhotoCaptureEnabled) {
                    //Add cached images from disk∆
                    itemsIndexed(toDoImageData) { _, item ->
                        ToDoImageListItem(
                            model,
                            item,
                            toDoImageData,
                            toDoImagesNew,
                            addUpdateButtonVisibility
                        )
                    }
                    //Add any images just added
                    itemsIndexed(toDoImagesNew) { _, item ->
                        ToDoNewImage(model, item, toDoImagesNew, addUpdateButtonVisibility)
                    }

                    item {
                        Spacer(Modifier.height(1.dp))
                        ToDoCameraButtonProcessing(model, toDoImagesNew, addUpdateButtonVisibility)
                    }
                }

                if (addUpdateButtonVisibility.value) {
                    item {
                        ToDoItemAddButton(model, toDoImagesNew, voiceState)
                    }
                }

                item {
                    LastUpdatedText(model)
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
            RoomDataProvider()
        )
    )
}

