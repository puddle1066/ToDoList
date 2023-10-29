package com.paullanducci.todolist.ui.main.todoItemView.imageList

import android.graphics.Bitmap
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.paullanducci.todolist.R
import com.paullanducci.todolist.imageHeight
import com.paullanducci.todolist.imageWidth

@Composable
fun ToDoImageDisplayImage(
    key: String,
    image: Bitmap,
    onDeleteClick: (String) -> Unit,
    onExpandClick: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp, 20.dp, 20.dp, 20.dp),
    ) {

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
            bitmap = Bitmap.createScaledBitmap(image, imageWidth, imageHeight, false)
                .asImageBitmap(),
            contentDescription = stringResource(id = R.string.missing_resource)
        )

        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            IconButton(
                onClick = { onDeleteClick(key) },
                modifier = Modifier
                    .padding(20.dp)
                    .width(40.dp)
                    .height(40.dp)
                    .align(Alignment.TopStart)
                    .border(
                        width = 2.dp,
                        color = Color.White,
                        shape = RoundedCornerShape(15.dp)
                    ),
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    tint = Color.White,
                    contentDescription = stringResource(id = R.string.missing_resource)
                )
            }
        }

        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            IconButton(
                onClick = { onExpandClick(key) },
                modifier = Modifier
                    .padding(20.dp)
                    .width(40.dp)
                    .height(40.dp)
                    .align(Alignment.TopEnd)
                    .border(
                        width = 2.dp,
                        color = Color.White,
                        shape = RoundedCornerShape(15.dp)
                    ),
            ) {
                Icon(
                    imageVector = Icons.Filled.OpenInFull,
                    tint = Color.White,
                    contentDescription = stringResource(id = R.string.missing_resource)
                )
            }
        }
    }
}



