package com.paul.todolist.database

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.paul.todolist.di.database.data.ListDataItem
import com.paul.todolist.listState_Normal
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)

class RoomListIItemsProviderTest : BaseDataProviderTest() {
    private var listDatItem = ListDataItem("4", "title", listState_Normal)

    @Test
    fun insertList() {
        listItemsDao.insert(listDatItem)
        assert(listItemsDao.getAllSortedASC().size == 4)
    }

    @Test
    fun getListOfLists() {
        assert(listItemsDao.getAllSortedASC().size == 3)
    }

    @Test
    fun getLIstItemsCount() {
        assert(listItemsDao.getListItemCount("") == 0)
    }

    @Test
    fun getAllListsSorted() {
        assert(listItemsDao.getAllSortedASC().size == 3)
    }

    @Test
    fun deleteList() {
        listItemsDao.insert(listDatItem)
        listItemsDao.deleteList("4")
        assert(listItemsDao.getAllSortedASC().size == 3)
    }

    @Test
    fun getListTitle() {
        assert(listItemsDao.getListTitle("1") == "ToDo")
    }

    @Test
    fun getListItem() {
        assert(listItemsDao.getListItem("1").listId == "1")
    }

    @Test
    fun isValidListItem() {
        assert(listItemsDao.getListItemCount("1") == 1)
    }

}