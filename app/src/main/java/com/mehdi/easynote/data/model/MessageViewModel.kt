package com.mehdi.easynote.data.model

import androidx.lifecycle.*
import com.mehdi.easynote.data.MessageRepository
import java.lang.IllegalArgumentException

class MessageViewModel(private val repository: MessageRepository): ViewModel() {
//    private val _messages: MutableLiveData<MutableList<String>> = MutableLiveData(mutableListOf(""))

//    val messages: LiveData<MutableList<String>> = _messages

    val messages: LiveData<List<Message>> = repository.messages.asLiveData()

    suspend fun addMessage(message: Message){
//        _messages.value?.add(message)
        repository.insert(message)
//        Log.d("MessageViewModel", "addMessage: count: ${_messages.value?.size}")
    }

    suspend fun updateMessage(message: Message){
        repository.update(message)
    }


}

class MessageViewModelFactory(private val repository: MessageRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MessageViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return MessageViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}