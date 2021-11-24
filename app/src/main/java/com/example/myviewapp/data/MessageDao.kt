package com.example.myviewapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao{
    @Query("SELECT * FROM message")
    fun getAll(): Flow<List<Message>>


    @Query("SELECT * FROM message WHERE text LIKE :keyword")
    fun findMessages(keyword: String)

    @Insert
    fun insertMessage(vararg messages: Message)


    @Delete
    fun delete(message: Message)




}