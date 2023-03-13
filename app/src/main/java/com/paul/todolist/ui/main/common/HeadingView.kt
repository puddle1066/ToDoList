package com.paul.todolist.ui.main.common

import android.app.Activity
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paul.todolist.ui.theme.ToolboxTheme
import com.paul.todolist.ui.widgets.DropDownMenuComponent
import com.paul.todolist.ui.widgets.DropDownMenuParameter

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HeadingView() {
    val listofValues =  listOf("ToDo","Finished")    //From Database

    ToolboxTheme {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(MaterialTheme.colors.primary)
            ) {
                IconButton(
                    onClick = {
                        //TODO Implement menu
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = "menu",
                        modifier = Modifier
                            .width(30.dp)
                            .height(50.dp)
                            .background(MaterialTheme.colors.primary),
                        tint = MaterialTheme.colors.secondary
                    )
                }

                Spacer(Modifier.width(10.dp))

                DropDownMenuComponent(DropDownMenuParameter(listofValues,false,""))

            }
    }
}

