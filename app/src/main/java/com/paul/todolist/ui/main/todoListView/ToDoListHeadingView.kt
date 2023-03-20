package com.paul.todolist.ui.main.todoListView

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paul.todolist.*
import com.paul.todolist.di.database.data.ListDataItem
import com.paul.todolist.ui.main.common.drawMenu.DrawerBody
import com.paul.todolist.ui.main.common.drawMenu.drawMenuShape
import com.paul.todolist.ui.theme.ToolboxTheme
import com.paul.todolist.ui.widgets.DropDownMenuComponent
import com.paul.todolist.ui.widgets.DropDownMenuParameter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ToDoListHeadingView(lists: List<ListDataItem>) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val menuItems  = listOf(menuOptionLists, menuOptionSettings)


    ToolboxTheme {
        Scaffold(
            topBar = { ToDoListTopBar(scope,scaffoldState,lists) },
            scaffoldState = scaffoldState,
            drawerGesturesEnabled = true,
            drawerShape = drawMenuShape(menuItems.size),
            drawerContent = {
                DrawerBody(
                    drawItems = menuItems,
                    scaffoldState = scaffoldState,
                    scope = scope
                ) {
                    //Launch Options
                    ToDoList.NavHostController .navigate(it.link) {
                      ToDoList.NavHostController.graph.startDestinationId
                        launchSingleTop = true
                    }
                }
            }
        ) {

        }
    }
}

@Composable
fun ToDoListTopBar(scope : CoroutineScope, scaffoldState : ScaffoldState, lists: List<ListDataItem>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(MaterialTheme.colors.primary)
    ) {
        IconButton(
            onClick = {
                run {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                }
            }
        ) {
            Icon(
                imageVector = Icons.Filled.MoreVert,
                contentDescription = "menu",
                modifier = Modifier
                    .width(30.dp)
                    .height(50.dp)
                    .background(MaterialTheme.colors.primary),
                tint = MaterialTheme.colors.secondary
            )
        }
        Spacer(Modifier.width(10.dp))

        val nameList: MutableList<String> = ArrayList()
        lists.forEach {
            nameList.add(it.title)
        }
        DropDownMenuComponent(DropDownMenuParameter(nameList, false, nameList[0]))
    }

}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun Preview() {
    val listofValues =  listOf(ListDataItem("0","First in List","0"),ListDataItem("1","Last i  List","0"))    //From Database
    ToDoListHeadingView(listofValues)
}


