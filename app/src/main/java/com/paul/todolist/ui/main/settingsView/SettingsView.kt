package com.paul.todolist.ui.main.settingsView

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paul.todoList.BuildConfig
import com.paul.todoList.R
import com.paul.todolist.ToDoScreens
import com.paul.todolist.ui.main.common.StandardTopBar
import com.paul.todolist.ui.theme.ToDoListTheme
import com.paul.todolist.ui.theme.typography

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SettingsView() {
    val menuItems = hashMapOf<Int, String>(
        R.string.ToDo_Lists to ToDoScreens.ToDoListView.name,
        R.string.settings to ToDoScreens.SettingsView.name
    )

    ToDoListTheme {
        Scaffold(
            topBar = {
                StandardTopBar(stringResource(R.string.settings), menuItems)
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                Text(
                    modifier = Modifier.padding(40.dp, 10.dp, 10.dp, 10.dp),
                    text = "Version: " + BuildConfig.VERSION_NAME + "      Build: (" + BuildConfig.VERSION_CODE + ")",
                    style = typography.titleLarge,
                    color = MaterialTheme.colorScheme.secondary,
                )
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SettingsViewPreview() {
    SettingsView()
}



