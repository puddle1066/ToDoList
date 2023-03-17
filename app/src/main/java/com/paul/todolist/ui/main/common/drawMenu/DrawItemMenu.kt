package com.paul.todolist.ui.main.common.drawMenu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.paul.todolist.ui.theme.typography

@Composable
fun DrawerItem(drawItem: DrawItem, modifier: Modifier = Modifier, onItemClick: (DrawItem) -> Unit) {
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
                tint = MaterialTheme.colors.secondary
            )
            Spacer(Modifier.width(20.dp))
            Text(
                text = stringResource(drawItem.title),
                style = typography.body1,
                color =  MaterialTheme.colors.secondary
            )
        }
        Divider( color =  MaterialTheme.colors.primary, thickness = 2.dp)
    }
}