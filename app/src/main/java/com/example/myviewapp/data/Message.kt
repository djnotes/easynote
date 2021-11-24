package com.example.myviewapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Message(@PrimaryKey val uid: Int,
                   val text: String, val author: String, val timestamp: Long = Calendar.getInstance().time.time)