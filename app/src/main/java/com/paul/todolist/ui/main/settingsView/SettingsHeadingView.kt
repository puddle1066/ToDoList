package com.paul.todolist.ui.main.settingsView

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.paul.todoList.R
import com.paul.todolist.menuOptionLists
import com.paul.todolist.menuOptionToDoList
import com.paul.todolist.ui.main.common.StandardTopBar
import com.paul.todolist.ui.main.common.drawMenu.DrawerBody
import com.paul.todolist.ui.main.common.drawMenu.drawMenuShape
import com.paul.todolist.ui.main.common.showView
import com.paul.todolist.ui.theme.ToDoListTheme

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SettingsHeadingView() {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val menuItems = listOf(menuOptionLists, menuOptionToDoList)
    val title = LocalContext.current.resources.getString(R.string.settings)

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

        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun Preview() {
    SettingsHeadingView()
}


