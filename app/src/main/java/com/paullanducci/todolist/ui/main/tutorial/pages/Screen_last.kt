package com.paullanducci.todolist.ui.main.tutorial.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.paullanducci.todolist.R
import com.paullanducci.todolist.ToDoScreens
import com.paullanducci.todolist.ui.main.common.showView
import com.paullanducci.todolist.ui.theme.ToDoListTheme
import com.paullanducci.todolist.ui.widgets.AppButton

@Composable
fun Screen_last() {
    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    ToDoListTheme {
        Box(
            modifier = Modifier
                .width(screenWidth)
                .height(screenHeight)
                .background(MaterialTheme.colorScheme.background)
        ) {
            AppButton(
                onButtonPressed = {
                    showView(ToDoScreens.ToDoListView.name)
                },
                textID = R.string.finished
            )

        }
    }
}