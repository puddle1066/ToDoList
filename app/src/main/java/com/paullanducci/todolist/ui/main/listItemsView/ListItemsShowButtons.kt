package com.paullanducci.todolist.ui.main.listItemsView

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddTask
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Update
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.paullanducci.todolist.R
import com.paullanducci.todolist.di.database.data.ListDataItem

@Composable
fun ListItemsShowButtons(
    selected: MutableState<ListDataItem>,
    onPressed: (Int) -> Unit
) {
    val iconWeight = (LocalConfiguration.current.screenWidthDp.dp - 40.dp) / 3

    Row(
        Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(10.dp, 20.dp, 10.dp, 20.dp),
    ) {
        if (nonSelected(selected)) {
            IconButton(
                Icons.Default.AddTask,
                onClick = {
                    onPressed(R.string.add_button)
                },
                iconWeight,
                stringResource(R.string.add_button)
            )
        } else {
            IconButton(
                Icons.Default.Update,
                onClick = {
                    onPressed(R.string.update_button)
                },
                iconWeight,
                stringResource(R.string.update_button)
            )
            IconButton(
                Icons.Default.DeleteForever,
                onClick = {
                    onPressed(R.string.delete_button)
                },
                iconWeight,
                stringResource(R.string.delete_button)
            )
        }
    }
}
