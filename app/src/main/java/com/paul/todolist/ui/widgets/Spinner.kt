import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paul.todolist.ui.theme.typography

/*  Example of how to use this spinner
    Spinner(
                        distanceBetweenPoints,
                        preselected = distanceBetweenPoints[measureModel.measureDistanceIndex],
                        onSelectionChanged = {
                            measureModel.supportStaffHeightIndex = listOfStaffSizes.indexOf(it) },
                        stringResource(id = R.string.MeasureDistance)
                    )
                }
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Spinner(
    list: List<Pair<String, String>>,
    preselected: Pair<String, String>,
    onSelectionChanged: (selection: Pair<String, String>) -> Unit,
    listTitle: String = "",
    startAnimationColor: Color =  MaterialTheme.colorScheme.primary,
    endAnimationColour: Color =  MaterialTheme.colorScheme.onBackground
) {
    var selected by remember { mutableStateOf(preselected) }
    var expanded by remember { mutableStateOf(false) } // initial value list closed

    val color = remember { Animatable(startAnimationColor) }

    LaunchedEffect(expanded) {
        color.animateTo(if (expanded) endAnimationColour else startAnimationColor, animationSpec = tween(2000))
    }

    Box ( modifier = Modifier.background(color.value))
        {
            Column (modifier = Modifier.fillMaxWidth().background(color.value)) {
                OutlinedTextField (
                    value = (selected.second),
                    onValueChange = {},
                    label = {  androidx.compose.material.Text(
                        modifier = Modifier.background(color.value).clip(RoundedCornerShape(10.dp)),
                        text = listTitle,
                        style = typography.caption,
                        textAlign = TextAlign.Left,
                        color = MaterialTheme.colorScheme.onPrimary
                    )},
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = { Icon(Icons.Outlined.ArrowDropDown, null) },
                    readOnly = true,
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = MaterialTheme.colorScheme.secondary,
                        containerColor = color.value,
                        disabledTextColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    textStyle =  typography.body1
                )

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .width(340.dp)
                        .background(color.value),
                ) {
                    list.forEach { entry ->

                        DropdownMenuItem(
                            modifier = Modifier
                                      .background(color.value),
                            onClick = {
                                selected = entry
                                expanded = false
                                onSelectionChanged.invoke(entry)
                            },
                            text = {
                                Text(
                                    text = (entry.second),
                                    modifier = Modifier.wrapContentWidth().align(Alignment.Start),
                                    style = typography.body1
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
                    .padding(10.dp)
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

        val entry1 = Pair("Key1", "Entry1")
        val entry2 = Pair("Key2", "Entry2")
        val entry3 = Pair("Key3", "Entry3")

        Spinner(
            listOf(entry1, entry2, entry3),
            preselected = entry2,
            onSelectionChanged = {}
        )
    }
}
