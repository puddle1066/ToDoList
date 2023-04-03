package com.paul.todolist.di.database.dao

import androidx.room.*
import com.paul.todolist.di.database.data.ToDoImageData

@Dao
interface ImageDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(image: ToDoImageData)

    @Query("SELECT * FROM ToDoImageData where itemId = :itemId")
    fun getItemImages(itemId : String): List<ToDoImageData>


}