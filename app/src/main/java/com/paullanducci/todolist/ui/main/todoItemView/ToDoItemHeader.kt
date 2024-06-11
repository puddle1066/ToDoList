package com.paullanducci.todolist.ui.main.todoItemView

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paullanducci.todolist.R
import com.paullanducci.todolist.di.database.RoomDataProvider
import com.paullanducci.todolist.ui.theme.typography

@Composable
fun ToDoItemHeader(model: ToDoItemModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Text(
            modifier = Modifier.padding(40.dp, 10.dp, 10.dp, 10.dp),
            text = stringResource(R.string.ToDo_item) + " -  " + model.getListTitle(model.todoDataItem.listID),
            style = typography.titleLarge,
            color = MaterialTheme.colorScheme.secondary,
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewHeader() {
    ToDoItemHeader(
        ToDoItemModel(
            RoomDataProvider()
        )
    )
}

