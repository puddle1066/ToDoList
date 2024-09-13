package com.paullanducci.todolist.di.database

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.paullanducci.todolist.di.database.data.ToDoImageData
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)

class RoomToDoImageProviderTest : BaseDataProviderTest() {

    private var imageDatItem = ToDoImageData("2", "1", "")

    @Test
    fun insertToDoImage() {
        imageDataDao.insert(imageDatItem)
        assert(imageDataDao.getItemImages("1").size == 1)
    }

    @Test
    fun getToDoImages() {
        imageDataDao.insert(imageDatItem)
        assert(imageDataDao.getItemImages("1").size == 1)
    }

    @Test
    fun deleteAllToDoImages() {
        imageDataDao.insert(imageDatItem)
        imageDataDao.deleteAllImagesForItem("1")
        assert(imageDataDao.getItemImages("1").isEmpty())
    }

    @Test
    fun deleteToDoImage() {
        imageDataDao.insert(imageDatItem)
        imageDataDao.deleteImage("2")
        assert(imageDataDao.getItemImages("1").isEmpty())
    }

}