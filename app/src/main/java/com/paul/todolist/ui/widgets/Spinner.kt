import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paul.todolist.ui.theme.typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Spinner(
    list: HashMap<String, String>,
    preselected: String,
    onSelectionChanged: (key: String) -> Unit,
    listTitle: String = ""
) {
    var selected by remember { mutableStateOf(preselected) }
    var expanded by remember { mutableStateOf(false) } // initial value list closed

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(
                MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(4.dp)
            )
    )
    {
        Column {
            TextField(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary)
                    .weight(0.90f)
                    .fillMaxWidth()
                    .height(40.dp),
                value = selected,
                onValueChange = {},
                label = {
                    Text(
                        text = listTitle,
                        style = typography.bodyLarge,
                        textAlign = TextAlign.Left,
                        color = MaterialTheme.colorScheme.surface,
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    focusedTextColor = MaterialTheme.colorScheme.secondary,
                    unfocusedTextColor = MaterialTheme.colorScheme.secondary,
                    containerColor = MaterialTheme.colorScheme.primary,
                    cursorColor = MaterialTheme.colorScheme.secondary
                ),
                trailingIcon = {
                    Icon(
                        Icons.Outlined.ArrowDropDown,
                        null,
                        modifier = Modifier
                            .weight(0.10f)
                            .width(40.dp)
                            .height(40.dp)
                            .background(MaterialTheme.colorScheme.primary)
                            .clickable(
                                onClick = {
                                    expanded = !expanded
                                }
                            )
                    )
                },
                readOnly = true,
                textStyle = typography.bodyLarge,
            )

            DropdownMenu(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background),
                expanded = expanded,
                onDismissRequest = { expanded = false }

            ) {
                list.forEach { entry ->

                    DropdownMenuItem(
                        modifier = Modifier
                            .padding(10.dp, 0.dp, 10.dp, 0.dp)
                            .background(MaterialTheme.colorScheme.surface),
                        onClick = {
                            selected = entry.value
                            expanded = false
                            onSelectionChanged.invoke(entry.key)
                        },
                        text = {
                            Text(
                                text = (entry.value),
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .align(Alignment.Start),
                                style = typography.bodyLarge,
                                color = MaterialTheme.colorScheme.secondary,
                            )
                        }
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Transparent)
                .clickable(
                    onClick = {
                        expanded = !expanded
                    }
                )
        )
    }
}


@Preview(showBackground = true)
@Composable
fun SpinnerPreview() {
    MaterialTheme {
        val list = hashMapOf("Key1" to "Entry1", "Key2" to "Entry2", "Key3" to "Entry3")

        Spinner(
            list,
            preselected = "Key2",
            onSelectionChanged = {}
        )
    }
}
