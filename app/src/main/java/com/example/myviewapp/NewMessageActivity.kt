package com.example.myviewapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myviewapp.databinding.ActivityNewMessageBinding

class NewMessageActivity: AppCompatActivity() {
    private lateinit var binding: ActivityNewMessageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addMessage.setOnClickListener{
            val text = binding.message.text.toString()
            if (text.isNotBlank()) {
                setResult(RESULT_OK, Intent().apply { putExtra("message", text) })
                finish()
            }
        }

    }
}