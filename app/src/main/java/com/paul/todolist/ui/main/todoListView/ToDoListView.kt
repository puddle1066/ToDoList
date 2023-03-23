package com.paul.todolist.ui.main.todoListView

import android.annotation.SuppressLint
import android.app.ProgressDialog.show
import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paul.todolist.ToDoList
import com.paul.todolist.ToDoScreens
import com.paul.todolist.di.database.RoomDataProvider
import com.paul.todolist.ui.main.common.NavigationFactory
import com.paul.todolist.ui.main.common.showView
import com.paul.todolist.ui.theme.ToolboxTheme

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ToDoListView(model : ToDoListModel) {

        ToolboxTheme {
            Scaffold(
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
                            showView(ToDoScreens.ToDoItemView.name)
                        }
                    )
                    { Icon(Icons.Filled.Add,"")}
                }

            ) {
                ToDoListHeadingView(model.getList())

        Box(
            Modifier
                .background(MaterialTheme.colorScheme.background)
        ) {
            LazyColumn(contentPadding = PaddingValues(top = 10.dp)) {
                itemsIndexed(model.getBatchList()) { _, item ->
                    Text(
                        item.description,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .combinedClickable(
                                onClick = {
                                    val listId = "22"
                                //    ToDoList.NavHostController.
                                },
                                onLongClick = { model.deleteItem(seletcedItem = item) },
                            )
                    )
                }
            }
            }}}
}
    @Preview
    @Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
    @Composable
    fun MainViewMockLayout() {
        ToDoListView(ToDoListModel(RoomDataProvider()))
    }






