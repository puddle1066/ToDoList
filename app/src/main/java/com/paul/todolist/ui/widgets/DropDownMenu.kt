package com.paul.todolist.ui.widgets

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paul.todolist.ui.theme.ToolboxTheme
import com.paul.todolist.ui.theme.typography

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DropDownMenuComponent(parameters: DropDownMenuParameter) {
    val expanded  = remember { mutableStateOf(parameters.expanded) }
    val chosedText  = remember { mutableStateOf(parameters.selectedOptionText) }

    val localStyle = typography.bodyLarge
    val mergedStyle = localStyle.merge(TextStyle(color = MaterialTheme.colorScheme.secondary))

    ToolboxTheme {
        ExposedDropdownMenuBox(
            expanded = expanded.value,
            onExpandedChange = {
                expanded.value = !parameters.expanded
            }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .background(MaterialTheme.colorScheme.primary),
                verticalAlignment =  Alignment.CenterVertically,
            ) {

                Icon(
                    modifier = Modifier
                        .width(30.dp)
                        .height(30.dp),
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "list",
                    tint = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.width(10.dp))
                BasicTextField(
                    modifier = Modifier.width(200.dp),
                    readOnly = true,
                    value = chosedText.value,
                    onValueChange = { },
                    singleLine = true,
                    enabled = true,
                    textStyle = mergedStyle,
                    )

                    Icon(
                        modifier = Modifier
                            .width(40.dp)
                            .height(40.dp)
                            .background(MaterialTheme.colorScheme.primary),
                        imageVector = Icons.Filled.ExpandMore,
                        contentDescription = "menu",
                        tint = MaterialTheme.colorScheme.secondary
                    )
            }

            ExposedDropdownMenu(
                expanded = expanded.value,
                onDismissRequest = {
                    expanded.value = false
                }
            ) {
                parameters.options.forEach { selectionOption ->
                    DropdownMenuItem(
                        onClick = {
                            chosedText.value = selectionOption
                            expanded.value = false
                        },

                        ) {
                        Icon(
                            modifier = Modifier
                                .width(30.dp)
                                .height(30.dp),
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "list",
                            tint = MaterialTheme.colorScheme.secondary
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = selectionOption,
                            color = MaterialTheme.colorScheme.secondary,
                            style = mergedStyle
                        )
                    }
                }
            }
        }
    }
}

data class DropDownMenuParameter(
    var options: List<String>,
    var expanded: Boolean,
    var selectedOptionText: String,
)

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun Preview() {
    DropDownMenuComponent(DropDownMenuParameter(listOf("ToDo","Finished"),true,"Finished"))
}
