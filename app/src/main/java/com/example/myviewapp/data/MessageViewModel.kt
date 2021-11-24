package com.example.myviewapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MessageViewModel: ViewModel() {
    private val _messages: MutableLiveData<MutableList<String>> = MutableLiveData(mutableListOf(""))

    val messages: LiveData<MutableList<String>> = _messages

    fun addMessage(message: String){
        _messages.value?.add(message)
        Log.d("MessageViewModel", "addMessage: count: ${_messages.value?.size}")
    }
}