package com.paul.todolist.di.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.paul.todolist.di.database.data.ToDoImageData

@Dao
interface ImageDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(image: ToDoImageData)

    @Query("SELECT * FROM ToDoImageData where itemId = :itemId")
    fun getItemImages(itemId: String): List<ToDoImageData>

    @Query("DELETE FROM ToDoImageData where itemId = :itemId")
    fun deleteAllImagesForItem(itemId: String)

    @Query("DELETE FROM ToDoImageData where key = :key")
    fun deleteImage(key: String)
}