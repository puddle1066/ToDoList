package com.paullanducci.todolist.di.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.paullanducci.todolist.di.database.data.Config

@Dao
interface ConfigDao {
    @Query(value = "SELECT value FROM Config where id = :id")
    fun getConfigValue(id: String): String

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setConfigValue(image: Config)
}