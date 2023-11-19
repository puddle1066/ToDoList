package com.paullanducci.todolist.ui.main.settingsView

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.paullanducci.todolist.R
import com.paullanducci.todolist.ui.theme.typography

@Composable
fun SettingsShowCarousel(model: SettingsModel) {

    val showInstructions = remember { mutableStateOf(model.showInstructions()) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.show_tutorial),
            style = typography.titleLarge,
            color = MaterialTheme.colorScheme.secondary,
        )
        Checkbox(
            showInstructions.value,
            onCheckedChange = {
                showInstructions.value = it
                model.setShowInstructions(it)
            },
        )
    }
}