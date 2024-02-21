package com.paullanducci.todolist.ui.widgets

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.paullanducci.todolist.ui.main.settingsView.SettingsModel
import com.paullanducci.todolist.ui.theme.typography

@Composable
fun SettingsButton(model: SettingsModel, textId: Int, key: String) {

    val settingsSwitch = remember { mutableStateOf(model.getOption(key)) }

    Row(
        modifier = Modifier
            .padding(20.dp, 0.dp, 10.dp, 0.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Text(
            modifier = Modifier.weight(0.9f),
            text = stringResource(id = textId),
            style = typography.bodyLarge,
            color = MaterialTheme.colorScheme.secondary,
        )

        Switch(
            checked = settingsSwitch.value,
            onCheckedChange = {
                settingsSwitch.value = it
                model.setOption(key, it)
            },
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colorScheme.primary,
                checkedTrackColor = MaterialTheme.colorScheme.onBackground,
                uncheckedThumbColor = MaterialTheme.colorScheme.secondary
            )
        )
    }
}