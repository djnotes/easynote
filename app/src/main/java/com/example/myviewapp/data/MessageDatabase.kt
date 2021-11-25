package com.example.myviewapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myviewapp.data.model.Message

@Database(entities = [Message::class], version = 1, exportSchema = true)
abstract class MessageDatabase: RoomDatabase(){
    abstract fun messageDao(): MessageDao

    companion object {
        @Volatile
        private var instance: MessageDatabase? = null
        fun getInstance(context: Context): MessageDatabase{
            //I don't understand Android Team's short snippet, so I prefer my dirty way of getting the instance :(
            return if (instance != null) {
                instance!!
            } else {
        //
            //               val dbInstance: MessageDatabase = instance
                val temp = Room.databaseBuilder(
                    context.applicationContext,
                    MessageDatabase::class.java, "message-database.db"
                )
                    .build()
                instance = temp
                temp
            }
//            return instance ?: synchronized(this){
//                val temp = Room.databaseBuilder(context,
//                MessageDatabase::class.java,
//                "easy-note.db").build()
//
//                instance = temp
//                temp
//            }
        }
    }
}