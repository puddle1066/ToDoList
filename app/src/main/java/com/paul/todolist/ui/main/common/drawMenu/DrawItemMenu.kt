package com.paul.todolist.ui.main.common.drawMenu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.paul.todolist.ui.theme.typography

@Composable
fun DrawerItemMenu(
    drawItem: DrawItem,
    modifier: Modifier = Modifier,
    onItemClick: (DrawItem) -> Unit
) {
    Column(
        modifier = Modifier.clickable {
            onItemClick(drawItem)
        }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .padding(10.dp)
        ) {
            Icon(
                imageVector = drawItem.icon,
                contentDescription = "menuIcon",
                modifier = Modifier
                    .width(30.dp)
                    .height(40.dp),
                tint = MaterialTheme.colorScheme.secondary
            )
            Spacer(Modifier.width(20.dp))
            Text(
                text = stringResource(drawItem.title),
                style = typography.bodyLarge,
                color = MaterialTheme.colorScheme.secondary
            )
        }
        Divider(color = MaterialTheme.colorScheme.onBackground, thickness = 2.dp)
    }
}