package com.example.myviewapp.data

import androidx.room.Dao
import androidx.room.Query

@Dao
interface MessageDao{
    @Query("SELECT * FROM message")
    fun getAll(): List<Message>


}