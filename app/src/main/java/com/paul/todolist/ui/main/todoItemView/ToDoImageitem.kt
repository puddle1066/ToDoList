package com.paul.todolist.ui.main.todoItemView

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.OpenInFull
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.paul.todoList.R
import com.paul.todolist.di.database.data.ToDoImageData
import com.paul.todolist.util.decodeBase64


@Composable
fun ToDoImageitem(
    todoimage: ToDoImageData,
    onDeleteClick: (ToDoImageData) -> Unit,
    onExpandClick: (ToDoImageData) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp, 20.dp, 20.dp, 20.dp),
    ) {

        decodeBase64(todoimage.image)?.let {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(15.dp))
                    .border(
                        width = 4.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(15.dp)
                    ),
                contentScale = ContentScale.FillBounds,
                bitmap = it.asImageBitmap(),
                contentDescription = stringResource(id = R.string.missing_resource)
            )
        }

        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            IconButton(
                onClick = { onDeleteClick(todoimage) },
                modifier = Modifier
                    .padding(20.dp)
                    .width(40.dp)
                    .height(40.dp)
                    .align(Alignment.TopStart)
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(15.dp)
                    ),
            ) {
                Icon(
                    Icons.Filled.Delete,
                    contentDescription = stringResource(id = R.string.missing_resource)
                )
            }
        }

        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            IconButton(
                onClick = { onExpandClick(todoimage) },
                modifier = Modifier
                    .padding(20.dp)
                    .width(40.dp)
                    .height(40.dp)
                    .align(Alignment.TopEnd)
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(15.dp)
                    ),
            ) {
                Icon(
                    Icons.Filled.OpenInFull,
                    contentDescription = stringResource(id = R.string.missing_resource)
                )
            }
        }
    }
}


