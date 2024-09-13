package com.paullanducci.todolist.ui.main.privacy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.paullanducci.todolist.R
import com.paullanducci.todolist.R.string.legal_Text_finished
import com.paullanducci.todolist.ToDoScreens
import com.paullanducci.todolist.ui.main.common.showView
import com.paullanducci.todolist.ui.theme.ToDoListTheme
import com.paullanducci.todolist.ui.theme.typography
import com.paullanducci.todolist.ui.widgets.AppButton

@Composable
fun LegalTextView(model: LegalTextModel) {

    ToDoListTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier
                    .weight(0.12f)
                    .padding(90.dp, 20.dp, 0.dp, 0.dp)
                    .background(MaterialTheme.colorScheme.primary),
                text = stringResource(id = R.string.title_privacy),
                style = typography.headlineSmall,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.secondary
            )

            Column(
                modifier = Modifier
                    .weight(0.71f)
                    .verticalScroll(rememberScrollState())
                    .padding(0.dp, 20.dp, 0.dp, 20.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(MaterialTheme.colorScheme.primary)

            ) {
                Text(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(10.dp, 0.dp, 0.dp, 10.dp),
                    text = model.legalTextBody,
                    style = typography.bodySmall,
                    textAlign = TextAlign.Left,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            Column(
                modifier = Modifier
                    .weight(0.20f)
            ) {
                AppButton(
                    onButtonPressed = {
                        showView(ToDoScreens.SettingsView.name)
                    },
                    textID = legal_Text_finished
                )
            }
        }
    }
}
