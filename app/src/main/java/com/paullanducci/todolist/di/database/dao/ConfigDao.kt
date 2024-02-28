package com.paullanducci.todolist.di.database.dao

import androidx.room.Dao
import androidx.room.Query

@Dao
interface ConfigDao {
    @Query(value = "SELECT value FROM Config where id = :id")
    fun getConfigValue(id: String): String

    @Query("UPDATE Config SET value = :value WHERE id = :key")
    fun setConfigValue(key: String, value: String)
}