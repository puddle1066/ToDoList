package com.paul.todolist.ui.main.todoListView

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paul.todolist.ToDoList
import com.paul.todolist.ToDoScreens
import com.paul.todolist.di.database.RoomDataProvider
import com.paul.todolist.ui.theme.ToolboxTheme

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ToDoListView(model : ToDoListModel) {

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                return Offset.Zero
            }
        }
    }

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
                        backgroundColor = MaterialTheme.colors.primary,
                        onClick = {
                            ToDoList.NavHostController.navigate(ToDoScreens.ToDoItemView.name)
                        }
                    )
                    { Icon(Icons.Filled.Add,"")}
                }

            ) {
                ToDoListHeadingView(model.getList())

        Box(
            Modifier
                .nestedScroll(nestedScrollConnection)
                .background(MaterialTheme.colors.background)
        ) {
            LazyColumn(contentPadding = PaddingValues(top = 10.dp)) {
                itemsIndexed(model.getBatchList()) { _, item ->
                    Text(
                        item.description,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .combinedClickable(
                                onClick = {//TODO Iplement
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






