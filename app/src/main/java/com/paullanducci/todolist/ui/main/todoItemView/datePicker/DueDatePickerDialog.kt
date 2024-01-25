package com.paullanducci.todolist.ui.main.todoItemView.datePicker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paullanducci.todolist.R
import com.paullanducci.todolist.dateFormat
import com.paullanducci.todolist.ui.theme.typography
import com.paullanducci.todolist.ui.widgets.AppButton
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(
    openDialog: MutableState<Boolean>,
    currentDueDate: String,
    onDateChange: (String) -> Unit,
    onCancel: () -> Unit,
    onDelete: () -> Unit
) {


    val dateFormatter = SimpleDateFormat(dateFormat, Locale.getDefault())

    val errorMessageState = remember { mutableStateOf("") }

    val initialDate = Calendar.getInstance()
    val backgroundColor = MaterialTheme.colorScheme.primary
    val buttonBackgroundColor = remember { mutableStateOf(backgroundColor) }

    if (openDialog.value) {
        if (currentDueDate != "0") {
            initialDate.time = dateFormatter.parse(currentDueDate)!!
        }
        errorMessageState.value = ""

        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            }
        ) {
            Surface(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight(),
                shape = MaterialTheme.shapes.large
            ) {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .background(MaterialTheme.colorScheme.background)
                )
                {
                    dueDatePicker(initialDate, errorMessageState)
                    dueDateTimePicker(initialDate)

                    Text(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .padding(20.dp, 0.dp, 0.dp, 0.dp),
                        text = errorMessageState.value,
                        style = typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error
                    )
                    Spacer(Modifier.height(1.dp))

                    //Button enabled or not
                    if (errorMessageState.value.isEmpty()) {
                        buttonBackgroundColor.value = MaterialTheme.colorScheme.primary
                    } else {
                        buttonBackgroundColor.value = MaterialTheme.colorScheme.background
                    }

                    AppButton(
                        onButtonPressed = {
                            if (errorMessageState.value.isEmpty()) {
                                openDialog.value = false
                                onDateChange(dateFormatter.format(initialDate.time))
                            }
                        },
                        backgroundColor = buttonBackgroundColor.value,
                        textID = R.string.ok
                    )
                    Spacer(Modifier.height(1.dp))

                    if (currentDueDate != "0") {
                        AppButton(
                            onButtonPressed = {
                                openDialog.value = false
                                onDelete()
                            },
                            textID = R.string.remove
                        )
                        Spacer(Modifier.height(1.dp))
                    }

                    AppButton(
                        onButtonPressed = {
                            openDialog.value = false
                            onCancel()
                        },
                        textID = R.string.cancel
                    )
                    Spacer(Modifier.height(1.dp))
                }
            }
        }
    }
}

fun validation(initDate: Calendar): String {
    if (initDate.before(Calendar.getInstance())) {
        return "Due Date cannot be in the past"
    }
    if (initDate.get(Calendar.MONTH) == 1 &&
        initDate.get(Calendar.DAY_OF_MONTH) > 28
    ) {
        return "Invalid Date"
    }
    return ""
}

fun getAdjustedMonth(initialDate: Calendar): Int {
    return initialDate.get(Calendar.MONTH) + 1
}

@Preview
@Composable
fun MeasurePreview() {
    val openDialog = remember { mutableStateOf(true) }
    DatePickerDialog(
        openDialog,
        "01/JAN/2023 23:59 AM",
        onDateChange = {},
        onCancel = {},
        onDelete = {})
}
