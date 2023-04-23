package com.paul.todolist.ui.main.imageView

import android.content.res.Configuration
import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paul.todoList.R
import com.paul.todolist.di.dataStorage.DataStoreProvider
import com.paul.todolist.ui.main.MainView
import com.paul.todolist.ui.theme.ToDoListTheme
import com.paul.todolist.util.decodeBase64

@Composable
fun ItemImageView(model: ItemImageModel) {

    //Create a placeholder image until we can populate the image
    val image: Bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
    val imageState = remember { mutableStateOf(image) }
    decodeBase64(MainView.image)?.let {
        imageState.value = it
    }

    ToDoListTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            ImageHeadingView()
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(15.dp))
                    .border(
                        width = 4.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(15.dp)
                    ),
                contentScale = ContentScale.FillBounds,
                bitmap = imageState.value.asImageBitmap(),
                contentDescription = stringResource(id = R.string.missing_resource)
            )
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ItemImageViewPreview() {
    ItemImageView(ItemImageModel(DataStoreProvider(LocalContext.current)))
}



