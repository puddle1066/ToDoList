package com.paul.todolist.ui.main.todoItemView

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paul.todolist.ui.main.common.getToDoListId
import com.paul.todolist.ui.theme.ToolboxTheme
import com.paul.todolist.ui.theme.typography


@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ToDoItemView() {

    //TODO Get ToDO From DB if we have one
    var x = getToDoListId()
    Text("AAAA")


    ToolboxTheme {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.background)
        )
        {
            ToDoItemHeadingView()

            OutlinedTextField (
                value = ("AAAA"),
                onValueChange = {},
                label = {Text(
                    modifier = Modifier.background(MaterialTheme.colorScheme.background).clip(RoundedCornerShape(10.dp)),
                    text = "text",
                    style = typography.bodyLarge,
                    textAlign = TextAlign.Left,
                    color = MaterialTheme.colorScheme.onPrimary
                )},
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = { Icon(Icons.Outlined.ArrowDropDown, null) },
                readOnly = true,
                textStyle =  typography.bodyLarge,
            )
        }
    }
}
