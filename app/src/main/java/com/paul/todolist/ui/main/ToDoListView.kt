package com.paul.todolist.ui.main

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
import com.paul.todolist.di.database.RoomDataProvider
import com.paul.todolist.ui.main.common.HeadingView
import com.paul.todolist.ui.theme.ToolboxTheme


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainView(model : ToDoModel) {

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
                        //TODO Implement
                    }
                )
                { Icon(Icons.Filled.Add,"")}
            }
            ) {
                HeadingView()

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
                                onClick = {
                                    //TODO implement
//                                        HatchApp.NavHostController.currentBackStackEntry?.arguments?.putString(itemId, item.uid.toString())
//                                        HatchApp.NavHostController.navigate(HatchScreens.HatchDetailScreen.name)
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
        val measureModel = ToDoModel(RoomDataProvider())
        MainView(measureModel)
    }






