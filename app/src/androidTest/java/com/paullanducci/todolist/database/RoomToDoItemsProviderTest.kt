package com.paullanducci.todolist.database

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.paullanducci.todolist.di.database.data.ToDoDataItem
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)

class RoomToDoItemsProviderTest : BaseDataProviderTest() {
    private var todoDataItem = ToDoDataItem("1", "1", "description", "0", "0", "0", 999999)

    @Test
    fun deleteItem() {
        toDoItemsDao.insert(todoDataItem)
        toDoItemsDao.deleteItem("1")
        assert(toDoItemsDao.getAll().isEmpty())
    }

    @Test
    fun getToDoItems() {
        toDoItemsDao.insert(todoDataItem)
        assert(toDoItemsDao.getAll().size == 1)
    }

    @Test
    fun getAllIncompleteItems() {
        toDoItemsDao.insert(todoDataItem)
        assert(toDoItemsDao.getAll().size == 1)
    }

    @Test
    fun getFinishedItems() {
        val data = todoDataItem
        data.finishedDate = "24/09/1960"
        toDoItemsDao.insert(data)
        assert(toDoItemsDao.getAllFinished().size == 1)
    }

    @Test
    fun insertToDo() {
        toDoItemsDao.insert(todoDataItem)
        assert(toDoItemsDao.getToDoItem("1").itemId == "1")
    }

    @Test
    fun updateToDo() {
        toDoItemsDao.insert(todoDataItem)
        val data = todoDataItem
        data.description = "updated"
        toDoItemsDao.insert(data)
        assert(toDoItemsDao.getToDoItem("1").description == "updated")
    }

    @Test
    fun setFinishedDate() {
        toDoItemsDao.insert(todoDataItem)
        toDoItemsDao.setFinishedDate("1", "24/09/1960")
        assert(toDoItemsDao.getToDoItem("1").finishedDate == "24/09/1960")
    }

    @Test
    fun getLastSequence() {
        toDoItemsDao.insert(todoDataItem)
        val data = todoDataItem
        data.sequence = 999999
        toDoItemsDao.insert(data)
        assert(toDoItemsDao.getLastSequence().sequence == 999999)
    }

    @Test
    fun getToDoItem() {
        toDoItemsDao.insert(todoDataItem)
        toDoItemsDao.getToDoItem("1")
        assert(toDoItemsDao.getLastSequence().sequence == 999999)
    }

}