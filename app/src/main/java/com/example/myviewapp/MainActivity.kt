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
import com.example.myviewapp.data.MessageViewModel
import com.example.myviewapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MessageViewModel
    private lateinit var startForResult: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        viewModel = MessageViewModel()

        startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result->
            if (result.resultCode == Activity.RESULT_OK){
                result.data?.getStringExtra("message")?.let{
                    viewModel.addMessage(it)
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