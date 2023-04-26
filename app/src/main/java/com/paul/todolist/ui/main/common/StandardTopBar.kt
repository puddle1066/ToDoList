package com.paul.todolist.ui.main.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.paul.todoList.R
import com.paul.todolist.ui.theme.typography
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun StandardTopBar(title: String, scope: CoroutineScope, scaffoldState: ScaffoldState) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(MaterialTheme.colorScheme.primary)
    ) {
        IconButton(
            onClick = {
                run {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                }
            }
        ) {
            Icon(
                imageVector = Icons.Filled.MoreVert,
                contentDescription = stringResource(id = R.string.missing_resource),
                modifier = Modifier
                    .width(30.dp)
                    .height(50.dp)
                    .background(MaterialTheme.colorScheme.primary),
                tint = MaterialTheme.colorScheme.secondary
            )
        }

        Spacer(Modifier.width(10.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterVertically),
            text = title,
            color = MaterialTheme.colorScheme.secondary,
            style = typography.titleLarge
        )
    }
}