package com.paullanducci.todolist.ui.main.tutorial.pages

import TutorialScreenContainer
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paullanducci.todolist.R
import com.paullanducci.todolist.ToDoScreens
import com.paullanducci.todolist.carousel_text_left_margin
import com.paullanducci.todolist.ui.main.common.showView
import com.paullanducci.todolist.ui.theme.ToDoListTheme
import com.paullanducci.todolist.ui.theme.typography
import com.paullanducci.todolist.ui.widgets.AppButton

@Composable
fun TutorialPageFive() {
    ToDoListTheme {
        TutorialScreenContainer(
            {
                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top
                ) {

                    Text(
                        modifier = Modifier
                            .padding(carousel_text_left_margin, 0.dp, 0.dp, 10.dp)
                            .background(MaterialTheme.colorScheme.background),
                        text = stringResource(id = R.string.screen_5_1),
                        style = typography.bodyMedium,
                        textAlign = TextAlign.Left,
                        color = MaterialTheme.colorScheme.secondary,
                    )

                    Image(
                        painterResource(R.drawable.menu_5_1),
                        contentDescription = "menu_5_1",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .width(350.dp)
                            .height(100.dp)
                            .padding(40.dp, 0.dp, 30.dp, 0.dp)
                    )

                    Text(
                        modifier = Modifier
                            .padding(carousel_text_left_margin, 10.dp, 0.dp, 10.dp)
                            .background(MaterialTheme.colorScheme.background),
                        text = stringResource(id = R.string.screen_5_2),
                        style = typography.bodyMedium,
                        textAlign = TextAlign.Left,
                        color = MaterialTheme.colorScheme.secondary,
                    )

                    Image(
                        painterResource(R.drawable.menu_5_3),
                        contentDescription = "menu_5_3",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .width(350.dp)
                            .height(100.dp)
                            .padding(40.dp, 0.dp, 30.dp, 0.dp)
                    )

                    Text(
                        modifier = Modifier
                            .padding(carousel_text_left_margin, 10.dp, 0.dp, 10.dp)
                            .background(MaterialTheme.colorScheme.background),
                        text = stringResource(id = R.string.screen_5_4),
                        style = typography.bodyMedium,
                        textAlign = TextAlign.Left,
                        color = MaterialTheme.colorScheme.secondary,
                    )

                    Image(
                        painterResource(R.drawable.menu_5_4),
                        contentDescription = "menu_5_4",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .width(350.dp)
                            .height(100.dp)
                            .padding(40.dp, 0.dp, 30.dp, 0.dp)
                    )


                    Spacer(Modifier.height(50.dp))

                    AppButton(
                        onButtonPressed = {
                            showView(ToDoScreens.ToDoListView.name)
                        },
                        textID = R.string.screen_5_3
                    )
                }
            }
        )
    }
}


@Preview
@Composable
fun TutorialPageFivePreview() {
    TutorialPageFive()
}