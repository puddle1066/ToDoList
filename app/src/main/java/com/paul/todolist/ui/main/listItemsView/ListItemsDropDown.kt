package com.paul.todolist.ui.main.listItemsView

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.paul.todolist.R
import com.paul.todolist.di.database.data.ListDataItem
import com.paul.todolist.ui.theme.ToDoListTheme
import com.paul.todolist.ui.theme.typography

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListItemsDropDown(
    list: List<ListDataItem>,
    onValueChanged: (ListDataItem) -> Unit,
    showExpanded: Boolean,
    selectedOptionText: String
) {
    val expanded = remember { mutableStateOf(showExpanded) }
    val chosedText = remember { mutableStateOf(selectedOptionText) }

    val localStyle = typography.titleLarge
    val mergedStyle = localStyle.merge(TextStyle(color = MaterialTheme.colorScheme.secondary))

    ToDoListTheme {
        ExposedDropdownMenuBox(
            expanded = expanded.value,
            onExpandedChange = {
                expanded.value = !showExpanded
            }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .background(MaterialTheme.colorScheme.primary),
                verticalAlignment = Alignment.CenterVertically,
            ) {

                Icon(
                    modifier = Modifier
                        .width(30.dp)
                        .height(30.dp),
                    imageVector = Icons.Filled.Menu,
                    contentDescription = stringResource(id = R.string.missing_resource),
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
                    contentDescription = stringResource(id = R.string.missing_resource),
                    tint = MaterialTheme.colorScheme.secondary
                )
            }

            ExposedDropdownMenu(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background),
                expanded = expanded.value,
                onDismissRequest = {
                    expanded.value = false
                }
            ) {
                list.forEach { listItem ->
                    DropdownMenuItem(
                        onClick = {
                            chosedText.value = listItem.title
                            expanded.value = false
                            onValueChanged(listItem)
                        },

                        ) {
                        Icon(
                            modifier = Modifier
                                .width(30.dp)
                                .height(30.dp),
                            imageVector = Icons.Filled.Menu,
                            contentDescription = stringResource(id = R.string.missing_resource),
                            tint = MaterialTheme.colorScheme.secondary
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = listItem.title,
                            color = MaterialTheme.colorScheme.secondary,
                            style = mergedStyle
                        )
                    }
                }
            }
        }
    }
}
