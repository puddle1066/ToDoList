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
fun TutorialPageThree() {
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
                        text = stringResource(id = R.string.screen_3_6),
                        style = typography.bodyLarge,
                        textAlign = TextAlign.Left,
                        color = MaterialTheme.colorScheme.secondary,
                    )

                    Text(
                        modifier = Modifier
                            .padding(carousel_text_left_margin, 0.dp, 10.dp, 0.dp)
                            .background(MaterialTheme.colorScheme.background),
                        text = stringResource(id = R.string.screen_3_1),
                        style = typography.bodyMedium,
                        textAlign = TextAlign.Left,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                    Image(
                        painterResource(R.drawable.menu_3_1),
                        contentDescription = "menu_3_1",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .width(350.dp)
                            .height(50.dp)
                            .padding(40.dp, 10.dp, 30.dp, 0.dp)
                    )

                    Text(
                        modifier = Modifier
                            .padding(carousel_text_left_margin, 10.dp, 0.dp, 10.dp)
                            .background(MaterialTheme.colorScheme.background),
                        text = stringResource(id = R.string.screen_3_2),
                        style = typography.bodyMedium,
                        textAlign = TextAlign.Left,
                        color = MaterialTheme.colorScheme.secondary,
                    )

                    Image(
                        painterResource(R.drawable.menu_3_6),
                        contentDescription = "menu_3_6",
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
                        text = stringResource(id = R.string.screen_3_3),
                        style = typography.bodyMedium,
                        textAlign = TextAlign.Left,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                    Image(
                        painterResource(R.drawable.menu_3_4),
                        contentDescription = "menu_3_4",
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
                        text = stringResource(id = R.string.screen_3_4),
                        style = typography.bodyMedium,
                        textAlign = TextAlign.Left,
                        color = MaterialTheme.colorScheme.secondary,
                    )

                    Image(
                        painterResource(R.drawable.menu_3_2),
                        contentDescription = "menu_3_2",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .width(350.dp)
                            .height(100.dp)
                            .padding(40.dp, 0.dp, 30.dp, 0.dp)
                    )
                }
            }
        )
    }
}


@Preview
@Composable
fun TutorialPageThreePreview() {
    TutorialPageThree()
}