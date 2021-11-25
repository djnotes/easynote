package com.example.myviewapp

import android.app.Application
import com.example.myviewapp.data.MessageDatabase
import com.example.myviewapp.data.MessageRepository
import com.example.myviewapp.data.model.MessageViewModel
import com.example.myviewapp.data.model.MessageViewModelFactory

class EasyNoteApp: Application() {
    val database by lazy { MessageDatabase.getInstance(this)}
    val repository by lazy {MessageRepository(database.messageDao())}
}