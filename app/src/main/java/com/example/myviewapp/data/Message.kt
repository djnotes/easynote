package com.example.myviewapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Message(@PrimaryKey(autoGenerate = true) val uid: Long,
                   val text: String,  val author: String, val timestamp: Long = Calendar.getInstance().time.time)