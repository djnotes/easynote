package com.example.myviewapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myviewapp.databinding.ActivityEditBinding

class EditActivity: AppCompatActivity() {
    companion object{
        val EXTRA_MESSAGE = "com.example.myviewapp.extra_message"
        val EXTRA_AUTHOR = "com.example.myviewapp.extra_author"
    }
    private lateinit var binding: ActivityEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addMessage.setOnClickListener{
            val text = binding.message.text.toString()
            val author = binding.name.text.toString()

            if (text.isNotBlank()) {
                setResult(RESULT_OK, Intent().apply {
                    putExtra(EXTRA_MESSAGE, text)
                    putExtra(EXTRA_AUTHOR, author)
                })
                finish()
            }
        }

    }
}