package com.paul.todolist.ui.main.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.paul.todolist.ui.theme.typography

@Composable
fun AppMenu(
    menuItems: HashMap<Int, String>,
    expanded: MutableState<Boolean>
) {
    AnimatedVisibility(
        visible = expanded.value,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(
            modifier = Modifier
                .padding(5.dp, 50.dp, 0.dp, 0.dp)
        ) {
            DropdownMenu(
                expanded = expanded.value,
                onDismissRequest = {
                    expanded.value = false
                },
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary)
                    .width(200.dp),
            ) {
                menuItems.forEach { (key, value) ->
                    DropdownMenuItem(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.primary),
                        onClick = {
                            expanded.value = false
                            showView(value)
                        },
                    ) {
                        Text(
                            modifier = Modifier.weight(1.5f),
                            text = stringResource(key),
                            style = typography.bodyLarge,
                            color = MaterialTheme.colorScheme.secondary,
                        )
                    }
                }
            }
        }
    }
}