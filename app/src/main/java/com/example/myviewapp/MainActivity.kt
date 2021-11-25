package com.example.myviewapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myviewapp.data.*
import com.example.myviewapp.data.model.Message
import com.example.myviewapp.data.model.MessageViewModel
import com.example.myviewapp.data.model.MessageViewModelFactory
import com.example.myviewapp.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), MessageListAdapter.ItemClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var startForResult: ActivityResultLauncher<Intent>
    private lateinit var database: MessageDatabase
    private lateinit var recyclerView: RecyclerView

    private val viewModel: MessageViewModel by viewModels {
        MessageViewModelFactory((application as EasyNoteApp).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Binding class for this activity's layout
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)


        database = MessageDatabase.getInstance(applicationContext)
        val repo = MessageRepository(database.messageDao())



        recyclerView = binding.messagesList
        recyclerView.layoutManager = LinearLayoutManager(this)





        startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result->
            if (result.resultCode == Activity.RESULT_OK){
                result.data?.apply{
                    val messageText = getStringExtra(EditActivity.EXTRA_MESSAGE)
                    val messageAuthor = getStringExtra(EditActivity.EXTRA_AUTHOR)

                    GlobalScope.launch {
                        viewModel.addMessage(
                            Message(
                                0L,
                                messageText.toString(),
                                messageAuthor.toString()
                            )
                        )
                        Snackbar.make(binding.root, R.string.message_added_successfully, Snackbar.LENGTH_SHORT)
                            .show()
                    }
                }

            }
        }

        binding.fab.setOnClickListener {
            Toast.makeText(this, "Fab clicked", Toast.LENGTH_LONG).show()
            val intent = Intent(
                this,
                EditActivity::class.java
            )
            startForResult.launch(intent)
        }

        //Add observer for items in database
        viewModel.messages.observe(this){
            recyclerView.adapter = MessageListAdapter(this, it).apply{
                setOnClickListener(this@MainActivity)
            }
        }

    }

    override fun onItemClicked(position: Int) {
        Log.d("MainActivity", "onItemClicked: $position")
    }
}