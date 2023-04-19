package com.paul.todolist.ui.main.settingsView

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paul.todoList.R
import com.paul.todolist.ui.theme.ToDoListTheme
import com.paul.todolist.ui.theme.typography

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SettingsHeadingView() {

    ToDoListTheme {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(MaterialTheme.colorScheme.primary)
        ) {
            androidx.compose.material3.Text(
                modifier = Modifier.padding(40.dp, 15.dp, 10.dp, 10.dp),
                text = stringResource(R.string.settings),
                style = typography.titleLarge,
                color = MaterialTheme.colorScheme.secondary,
            )
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun Preview() {
    SettingsHeadingView()
}


