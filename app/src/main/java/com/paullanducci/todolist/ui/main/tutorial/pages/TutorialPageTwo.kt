package com.paullanducci.todolist.ui.main.tutorial.pages

import TutorialScreenContainer
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import com.paullanducci.todolist.carousel_text_left_margin
import com.paullanducci.todolist.ui.theme.ToDoListTheme
import com.paullanducci.todolist.ui.theme.typography

@Composable
fun TutorialPageTwo() {
    ToDoListTheme {
        TutorialScreenContainer(
            {
                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top
                ) {
                    Text(
                        modifier = Modifier
                            .padding(carousel_text_left_margin, 0.dp, 10.dp, 10.dp)
                            .background(MaterialTheme.colorScheme.background),
                        text = stringResource(id = R.string.screen_2_1),
                        style = typography.bodyMedium,
                        textAlign = TextAlign.Left,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                    Image(
                        painterResource(R.drawable.menu_2_1),
                        contentDescription = "menu_2_1",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .padding(30.dp, 0.dp, 10.dp, 10.dp)
                            .width(300.dp)
                            .height(70.dp)
                    )

                    Text(
                        modifier = Modifier
                            .padding(carousel_text_left_margin, 0.dp, 10.dp, 10.dp)
                            .background(MaterialTheme.colorScheme.background),
                        text = stringResource(id = R.string.screen_2_2),
                        style = typography.bodyMedium,
                        textAlign = TextAlign.Left,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                    Image(
                        painterResource(R.drawable.menu_2_3),
                        contentDescription = "menu_2_3",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .padding(30.dp, 0.dp, 10.dp, 10.dp)
                            .width(300.dp)
                            .height(70.dp)
                    )

                    Text(
                        modifier = Modifier
                            .padding(carousel_text_left_margin, 50.dp, 10.dp, 10.dp)
                            .background(MaterialTheme.colorScheme.background),
                        text = stringResource(id = R.string.screen_2_3),
                        style = typography.bodyMedium,
                        textAlign = TextAlign.Left,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                    Image(
                        painterResource(R.drawable.menu_2_2),
                        contentDescription = "menu_2_2",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .padding(30.dp, 0.dp, 30.dp, 10.dp)
                            .width(300.dp)
                            .height(120.dp)
                    )
                    Text(
                        modifier = Modifier
                            .padding(carousel_text_left_margin, 0.dp, 10.dp, 10.dp)
                            .background(MaterialTheme.colorScheme.background),
                        text = stringResource(id = R.string.screen_2_4),
                        style = typography.bodyMedium,
                        textAlign = TextAlign.Left,
                        color = MaterialTheme.colorScheme.secondary,
                    )

                }
            }
        )
    }
}


@Preview
@Composable
fun TutorialPageTwoPreview() {
    TutorialPageTwo()
}