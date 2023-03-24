package com.paul.todolist.ui.main.settingsView

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.paul.todolist.ui.main.common.UiState
import com.paul.todolist.ui.theme.ToolboxTheme

@Composable
fun SettingsView(uiState: UiState) {

    ToolboxTheme {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.background)
                ) {
                    SettingsHeadingView()
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SettingsViewPreview() {
    SettingsView(UiState())
}



