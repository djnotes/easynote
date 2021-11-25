package com.mehdi.easynote.data

import com.mehdi.easynote.data.model.Message
import kotlinx.coroutines.flow.Flow

class MessageRepository(private val messageDao: MessageDao) {
    val messages: Flow<List<Message>> = messageDao.getAll()

    @Suppress("RedundantSuspendModifier")
    suspend fun insert(message: Message){
        messageDao.insert(message)
    }

    @Suppress("RedundantSuspendModifier")
    suspend fun update(message: Message){
        messageDao.update(message)
    }

}