package com.paul.todolist.ui.main.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.paul.todolist.ui.theme.typography
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun StandardTopBar(title : Int, scope : CoroutineScope, scaffoldState : ScaffoldState) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(MaterialTheme.colors.primary)
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
                contentDescription = "menu",
                modifier = Modifier
                    .width(30.dp)
                    .height(50.dp)
                    .background(MaterialTheme.colors.primary),
                tint = MaterialTheme.colors.secondary
            )
        }
        Spacer(Modifier.width(10.dp))

        Text(
            modifier = Modifier.fillMaxWidth().align(Alignment.CenterVertically),
            text = LocalContext.current.resources.getString(title),
            color = MaterialTheme.colors.secondary,
            style = typography.h6
        )    }
}