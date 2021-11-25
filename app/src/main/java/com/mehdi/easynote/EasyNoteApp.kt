package com.mehdi.easynote

import android.app.Application
import com.mehdi.easynote.data.MessageDatabase
import com.mehdi.easynote.data.MessageRepository

class EasyNoteApp: Application() {
    val database by lazy { MessageDatabase.getInstance(this)}
    val repository by lazy { MessageRepository(database.messageDao()) }
}