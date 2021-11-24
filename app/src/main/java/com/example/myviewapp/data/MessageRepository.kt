package com.example.myviewapp.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class MessageRepository(private val messageDao: MessageDao) {
    val messages: Flow<List<Message>> = messageDao.getAll()

    @Suppress("RedundantSuspendModifier")
    suspend fun insert(message: Message){
        messageDao.insertMessage(message)
    }
}