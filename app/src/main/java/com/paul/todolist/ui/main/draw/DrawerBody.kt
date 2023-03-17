package com.paul.todolist.ui.main.draw

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun DrawerBody(
    drawItems: List<DrawItem>,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
    modifier: Modifier = Modifier,
    onItemClick: (DrawItem) -> Unit
) {
    Spacer(Modifier.height(50.dp))

    LazyColumn(
        ) {
            items(drawItems) { item ->
                DrawerItem(
                    item,
                    modifier = modifier
                ) {
                    scope.launch {
                        scaffoldState.drawerState.close()
                    }
                    onItemClick(item)
                }
            }
        }
}

