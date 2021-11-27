package com.mehdi.easynote.data

import androidx.room.*
import com.mehdi.easynote.data.model.Message
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao{
    @Query("SELECT * FROM message")
    fun getAll(): Flow<List<Message>>


    @Query("SELECT * FROM message WHERE text LIKE :keyword")
    fun findMessages(keyword: String): Flow<List<Message>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vararg messages: Message)


    @Update
    suspend fun update(message: Message)


    @Delete
    suspend fun delete(message: Message)




}