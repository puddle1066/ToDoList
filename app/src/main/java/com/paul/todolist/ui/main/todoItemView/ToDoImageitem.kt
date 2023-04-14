package com.paul.todolist.ui.main.todoItemView

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import com.paul.todolist.di.database.data.ToDoImageData
import com.paul.todolist.util.decodeBase64


@Composable
fun ToDoImageitem(
    todoimage: ToDoImageData,
    onItemClick: (ToDoImageData, Boolean) -> Unit
) {

    val colorSelected = MaterialTheme.colorScheme.error
    val colorUnSelected = MaterialTheme.colorScheme.primary
    val isSelected = false
    val backgroundColor = remember { mutableStateOf(colorUnSelected) }
    var selected by remember { mutableStateOf(isSelected) }

    Box(
        modifier = Modifier
            .clickable {
                if (selected) {
                    backgroundColor.value = colorUnSelected
                    selected = false
                } else {
                    backgroundColor.value = colorSelected
                    selected = true
                }
                onItemClick(todoimage, selected)
            }
            .fillMaxSize()
            .wrapContentWidth(align = Alignment.CenterHorizontally)
            .padding(20.dp, 20.dp, 20.dp, 20.dp),


        ) {
        decodeBase64(todoimage.image)?.let {
            Image(
                modifier = Modifier
                    .clip(RoundedCornerShape(15.dp))
                    .border(
                        width = 4.dp,
                        color = backgroundColor.value,
                        shape = RoundedCornerShape(15.dp)
                    ),
                bitmap = it.asImageBitmap(),
                contentDescription = "missing image",
            )
        }
    }
}

