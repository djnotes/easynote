package com.example.myviewapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import com.example.myviewapp.data.*
import com.example.myviewapp.data.Constant.MESSAGE_AUTHOR
import com.example.myviewapp.data.Constant.MESSAGE_TEXT
import com.example.myviewapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MessageViewModel
    private lateinit var startForResult: ActivityResultLauncher<Intent>
    private lateinit var database: MessageDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)


        setContentView(binding.root)


        database = MessageDatabase.getInstance(applicationContext)
        val repo = MessageRepository(database.messageDao())

        viewModel = MessageViewModelFactory(repo).create(MessageViewModel::class.java)


        startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result->
            if (result.resultCode == Activity.RESULT_OK){
                result.data?.apply{
                    val messageText = getStringExtra(MESSAGE_TEXT)
                    val messageAuthor = getStringExtra(MESSAGE_AUTHOR)

                    viewModel.addMessage(Message(0L, messageText.toString(), messageAuthor.toString()))
                }

            }
        }

        binding.fab.setOnClickListener {
            Toast.makeText(this, "Fab clicked", Toast.LENGTH_LONG).show()
            val intent = Intent(
                this,
                NewMessageActivity::class.java
            )
            startForResult.launch(intent)
        }

        viewModel.messages.observe(this){list->
            binding.count.text = "${list.size}"
        }
    }
}