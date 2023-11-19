package com.paullanducci.todolist.ui.main.settingsView

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paullanducci.todolist.BuildConfig
import com.paullanducci.todolist.DATABASE_NAME
import com.paullanducci.todolist.R
import com.paullanducci.todolist.ToDoScreens
import com.paullanducci.todolist.di.database.RoomDataProvider
import com.paullanducci.todolist.ui.main.common.StandardTopBar
import com.paullanducci.todolist.ui.theme.ToDoListTheme
import com.paullanducci.todolist.ui.theme.typography
import com.paullanducci.todolist.ui.widgets.AppButton
import com.paullanducci.todolist.util.DB_PATH
import com.paullanducci.todolist.util.copyFile
import com.paullanducci.todolist.util.deleteFile
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

                //check box so show carousel
                Spacer(modifier = Modifier.weight(1f))
                SettingsShowCarousel(model)

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

    val buttonVisible = remember { mutableStateOf<Boolean>(false) }
    val file = File("$DB_PATH$DATABASE_NAME-back")
    val formatedDate = SimpleDateFormat("dd-MMM-yyyy").format(file.lastModified())

    AppButton(
        onButtonPressed = {
            model.closeDatabase()
            copyFile(File(DB_PATH + DATABASE_NAME), File("$DB_PATH$DATABASE_NAME-back"))
            copyFile(
                File("$DB_PATH$DATABASE_NAME-shm"),
                File("$DB_PATH$DATABASE_NAME-shm-back")
            )
            copyFile(
                File("$DB_PATH$DATABASE_NAME-wal"),
                File("$DB_PATH$DATABASE_NAME-wal-back")
            )
            model.openDatabase()

            buttonVisible.value = file.exists()
        },
        textID = R.string.todo_backup
    )

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
        buttonVisible = buttonVisible.value
    )
}

@Preview
@Composable
fun SettingsViewPreview() {
    SettingsView(SettingsModel(RoomDataProvider()))
}



