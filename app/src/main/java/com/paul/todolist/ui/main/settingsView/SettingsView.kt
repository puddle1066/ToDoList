package com.paul.todolist.ui.main.settingsView

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paul.todolist.BuildConfig
import com.paul.todolist.R
import com.paul.todolist.ToDoScreens
import com.paul.todolist.ui.main.common.StandardTopBar
import com.paul.todolist.ui.theme.ToDoListTheme
import com.paul.todolist.ui.theme.typography
import com.paul.todolist.ui.widgets.AppButton
import com.paul.todolist.util.DB_PATH
import com.paul.todolist.util.copyFile
import com.paul.todolist.util.deleteFile
import java.io.File
import java.text.SimpleDateFormat

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SettingsView() {
    val menuItems = hashMapOf<Int, String>(
        R.string.ToDo_Lists to ToDoScreens.ToDoListView.name,
        R.string.lists to ToDoScreens.listsView.name
    )

    ToDoListTheme {
        Scaffold(
            topBar = {
                StandardTopBar(stringResource(R.string.settings), menuItems)
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(MaterialTheme.colorScheme.background)
            ) {

                Text(
                    modifier = Modifier.padding(40.dp, 10.dp, 10.dp, 10.dp),
                    text = stringResource(R.string.alerts_screen_title),
                    style = typography.titleLarge,
                    color = MaterialTheme.colorScheme.secondary,
                )

                SettingsAlertPicker(stringResource(R.string.alerts_overdue), {}, {})
                Spacer(modifier = Modifier.height(20.dp))

                SettingsAlertPicker(stringResource(R.string.alerts_late), {}, {})

                Spacer(modifier = Modifier.weight(1f))

                AppButton(
                    onButtonPressed = {
                        copyFile(File(DB_PATH + "tools-db"), File(DB_PATH + "tools-db-back"))
                        copyFile(
                            File(DB_PATH + "tools-db-shm"),
                            File(DB_PATH + "tools-db-shm-back")
                        )
                        copyFile(
                            File(DB_PATH + "tools-db-wal"),
                            File(DB_PATH + "tools-db-wal-back")
                        )
                    },
                    textID = R.string.todo_backup
                )
                val file = File(DB_PATH + "tools-db-back")
                val formatedDate = SimpleDateFormat("dd-MMM-yyyy").format(file.lastModified())

                var buttonText = stringResource(R.string.todo_restore) + "\n$formatedDate"

                AppButton(
                    onButtonPressed = {
                        deleteFile(DB_PATH, "tools-db")
                        deleteFile(DB_PATH, "tools-db-shm")
                        deleteFile(DB_PATH, "tools-db-wal")

                        copyFile(File(DB_PATH + "tools-db-back"), File(DB_PATH + "tools-db"))
                        copyFile(
                            File(DB_PATH + "tools-db-shm-back"),
                            File(DB_PATH + "tools-db-shm")
                        )
                        copyFile(
                            File(DB_PATH + "tools-db-wal-back"),
                            File(DB_PATH + "tools-db-wal")
                        )
                    },
                    textString = buttonText,
                    buttonVisible = file.exists()
                )

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
@Composable
fun SettingsViewPreview() {
    SettingsView()
}



