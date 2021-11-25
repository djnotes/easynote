package com.example.myviewapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myviewapp.databinding.ActivityEditBinding
import com.example.myviewapp.R


enum class Action{CREATE, EDIT}

class EditActivity: AppCompatActivity() {
    lateinit var requestedAction: Action?
    companion object{
        const val EXTRA_MESSAGE = "com.example.myviewapp.extra_message"
        const val EXTRA_AUTHOR = "com.example.myviewapp.extra_author"
        const val EXTRA_ACTION = "com.example.myviewapp.extra_action"
    }

    private lateinit var binding: ActivityEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestedAction = intent.data.getStringExtra(EXTRA_ACTION)

        when(requestedAction){
            Action.EDIT -> {
                binding.message.text = intent.data.getStringExtra(EXTRA_MESSAGE)
                binding.name.text = intent.data.getStringExtra(EXTRA_AUTHOR)
                binding.addMessage.text = getString(R.string.update)
            }
            Action.CREATE -> {
                binding.addMessage.text = getString(R.string.add)
            }
            else -> {}
        }

        binding.addMessage.setOnClickListener{
            val text = binding.message.text.toString()
            val author = binding.name.text.toString()

             if (text.isNotBlank() and author.isNotBlank()) {
                 setResult(RESULT_OK, Intent().apply {
                     putExtra(EXTRA_MESSAGE, text)
                     putExtra(EXTRA_AUTHOR, author)
                 })
                 finish()

             }

            }

        }

    }
}