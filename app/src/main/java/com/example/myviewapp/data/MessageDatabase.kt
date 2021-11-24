package com.example.myviewapp.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [], version = 1)
abstract class MessageDatabase: RoomDatabase(){
    abstract fun messageDao(): MessageDao
}