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
import com.paul.todolist.DATABASE_NAME
import com.paul.todolist.R
import com.paul.todolist.ToDoScreens
import com.paul.todolist.di.database.RoomDataProvider
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
fun SettingsView(model: SettingsModel) {
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

                SettingsAlertPicker(
                    stringResource(R.string.alerts_overdue),
                    model.getOverdueDays(),
                    model.getOverdueColor(),
                    { model.setOverdueDays(it) },
                    { model.setOverdueColor(it) }
                )
                Spacer(modifier = Modifier.height(20.dp))

                SettingsAlertPicker(
                    stringResource(R.string.alerts_late),
                    model.getLateDays(),
                    model.getLateColor(),
                    { model.setLateDays(it) },
                    { model.setLateColor(it) }
                )

                //Only show backup restore options for Debug Builds
                if (BuildConfig.BUILD_TYPE == "debug") {
                    ShowBackupRestoreButtons(model)
                }

                Spacer(modifier = Modifier.weight(1f))

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

@SuppressLint("SimpleDateFormat")
@Composable
fun ShowBackupRestoreButtons(model: SettingsModel) {
    AppButton(
        onButtonPressed = {
            model.closeDatabase()
            copyFile(File(DB_PATH + DATABASE_NAME), File("$DB_PATH$DATABASE_NAME-back"))
            copyFile(
                File("$DB_PATH$DATABASE_NAME-shm"),
                File(DB_PATH + DATABASE_NAME + "shm-back")
            )
            copyFile(
                File("$DB_PATH$DATABASE_NAME-wal"),
                File("$DB_PATH$DATABASE_NAME-wal-back")
            )
            model.openDatabase()
        },
        textID = R.string.todo_backup
    )
    val file = File("$DB_PATH$DATABASE_NAME-back")
    val formatedDate = SimpleDateFormat("dd-MMM-yyyy").format(file.lastModified())

    val buttonText = stringResource(R.string.todo_restore) + "\n$formatedDate"

    AppButton(
        onButtonPressed = {
            model.closeDatabase()
            deleteFile(DB_PATH, DATABASE_NAME)
            deleteFile(DB_PATH, "$DATABASE_NAME-shm")
            deleteFile(DB_PATH, "$DATABASE_NAME-wal")

            copyFile(File("$DB_PATH$DATABASE_NAME-back"), File(DB_PATH + DATABASE_NAME))
            copyFile(
                File("$DB_PATH$DATABASE_NAME-shm-back"),
                File("$DB_PATH$DATABASE_NAME-shm")
            )
            copyFile(
                File("$DB_PATH$DATABASE_NAME-wal-back"),
                File("$DB_PATH$DATABASE_NAME-wal")
            )
            model.openDatabase()
        },
        textString = buttonText,
        buttonVisible = file.exists()
    )
}

@Preview
@Composable
fun SettingsViewPreview() {
    SettingsView(SettingsModel(RoomDataProvider()))
}



