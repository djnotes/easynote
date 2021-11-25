package com.example.myviewapp.data.model

import androidx.lifecycle.*
import com.example.myviewapp.data.MessageRepository
import kotlinx.coroutines.launch
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