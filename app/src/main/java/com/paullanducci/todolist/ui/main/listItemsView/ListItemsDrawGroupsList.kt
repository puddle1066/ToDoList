package com.paullanducci.todolist.ui.main.listItemsView

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.paullanducci.todolist.di.database.data.ListDataItem

@Composable
fun ListItemsDrawGroupsList(
    model: ListItemsModel,
    listDataItems: SnapshotStateList<ListDataItem>,
    selected: MutableState<ListDataItem>,
    onListSelected: (ListDataItem) -> Unit
) {
    listDataItems.swapList(model.getUserDefinedLists())
    Box(
        Modifier
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(15.dp)
            )
            .fillMaxWidth()
            .height(550.dp)
    ) {
        LazyColumn {
            itemsIndexed(listDataItems) { _, item ->
                val count = model.getListCount(item.listId)
                ListItem(item, count, selected) { selectedListData: ListDataItem ->
                    onListSelected(selectedListData)
                }
            }
        }
    }
}
