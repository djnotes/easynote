package com.mehdi.easynote

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mehdi.easynote.databinding.ActivityEditBinding




class EditActivity : AppCompatActivity() {
    private var requestedAction: Action? = null

    companion object {
        const val EXTRA_MESSAGE = "com.mehdi.easynote.extra_message"
        const val EXTRA_AUTHOR = "com.mehdi.easynote.extra_author"
    }

    enum class Action { CREATE, EDIT }

    private lateinit var binding: ActivityEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestedAction = intent.action?.let { Action.valueOf(it.substringAfter("com.mehdi.easynote.")) }

        when (requestedAction) {
            Action.EDIT -> {
                binding.header.text = getString(R.string.editing_message)
                binding.takeAction.text = getString(R.string.update)
                binding.name.setText(intent.getStringExtra(EXTRA_AUTHOR))
                binding.message.setText(intent.getStringExtra(EXTRA_MESSAGE))
            }
            Action.CREATE -> {
                binding.header.text = getString(R.string.add_new_message)
                binding.takeAction.text = getString(R.string.add)
            }
            else -> {}
        }

        binding.takeAction.setOnClickListener {
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
