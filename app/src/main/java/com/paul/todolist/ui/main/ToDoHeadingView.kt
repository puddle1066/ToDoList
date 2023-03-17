package com.paul.todolist.ui.main

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
import com.paul.todolist.ToDoList
import com.paul.todolist.di.database.data.Lists
import com.paul.todolist.menuOptionSettings
import com.paul.todolist.ui.main.draw.DrawerBody
import com.paul.todolist.ui.main.draw.drawShape
import com.paul.todolist.ui.theme.ToolboxTheme
import com.paul.todolist.ui.widgets.DropDownMenuComponent
import com.paul.todolist.ui.widgets.DropDownMenuParameter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HeadingView(lists: List<Lists>) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val menuItems  = listOf(menuOptionSettings)

    ToolboxTheme {
        Scaffold(
            topBar = { topBar(scope,scaffoldState,lists) },
            scaffoldState = scaffoldState,
            drawerGesturesEnabled = true,
            drawerShape = drawShape(menuItems.size),
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
fun topBar(scope : CoroutineScope, scaffoldState : ScaffoldState, lists: List<Lists>) {
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

        var nameList: MutableList<String> = ArrayList()
        lists.forEach {
            nameList.add(it.title ?: "")
        }
        DropDownMenuComponent(DropDownMenuParameter(nameList, false, nameList.get(0)))
    }

}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun Preview() {
    val listofValues =  listOf(Lists("0","First","0"),Lists("1","Last","0"))    //From Database
    HeadingView(listofValues)
}


