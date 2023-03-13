package com.paul.todolist.di.database.dao

import androidx.room.*
import com.paul.todolist.di.database.data.Lists

@Dao
interface ListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(List: List<Lists>)

    @Query("SELECT * FROM Lists")
    fun getAll(): List<Lists>

    @Delete
    fun deleteGender(List: Lists)
}