package com.paul.todolist.ui.main.todoItemView

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paul.todoList.R
import com.paul.todolist.ToDoList
import com.paul.todolist.menuOptionSettings
import com.paul.todolist.menuOptionToDoList
import com.paul.todolist.ui.main.common.drawMenu.DrawerBody
import com.paul.todolist.ui.main.common.drawMenu.drawShape
import com.paul.todolist.ui.theme.ToolboxTheme
import com.paul.todolist.ui.theme.typography
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ToDoItemHeadingView() {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val menuItems  = listOf(menuOptionToDoList, menuOptionSettings)

    ToolboxTheme {
        Scaffold(
            topBar = { ToDoItemTopBar(scope,scaffoldState) },
            scaffoldState = scaffoldState,
            drawerGesturesEnabled = true,
            drawerShape = drawShape(menuItems.size),
            drawerContent = {
                DrawerBody(
                    drawItems = menuItems,
                    scaffoldState = scaffoldState,
                    scope = scope
                ) {
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
fun ToDoItemTopBar(scope : CoroutineScope, scaffoldState : ScaffoldState) {
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

        Text(
            modifier = Modifier.fillMaxWidth().align(Alignment.CenterVertically),
            text = LocalContext.current.resources.getString(R.string.ToDo_item),
            color = MaterialTheme.colors.secondary,
            style = typography.h6
        )    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun Preview() {
    ToDoItemHeadingView()
}


