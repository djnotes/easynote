package com.mehdi.easynote

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.appcompattheme.AppCompatTheme
import com.google.android.material.composethemeadapter.MdcTheme
import com.mehdi.easynote.databinding.ActivityEditBinding




class EditActivity : AppCompatActivity() {
    private var requestedAction: Action? = null
    private var mText = ""

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

        intent.getStringExtra(EXTRA_MESSAGE)?.let{
            mText = it
        }

        requestedAction = intent.action?.let { Action.valueOf(it.substringAfter("com.mehdi.easynote.")) }

        binding.header.apply{
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnLifecycleDestroyed(lifecycle))
            setContent{
                AppCompatTheme{
                    Text(
                        if (requestedAction == Action.EDIT) stringResource(R.string.editing_message)
                        else stringResource(id = R.string.add_new_message),
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }

        binding.message.apply{
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnLifecycleDestroyed(lifecycle))
            setContent{
                val (input, onInputChange) = remember{mutableStateOf(mText)}
                AppCompatTheme{
                    TextField(
                        value = input,
                        onValueChange = {
                            onInputChange(it)
                            mText = it
                        },
                        modifier = Modifier
                            .padding(dimensionResource(R.dimen.default_padding)),
                    )
                }
            }
        }


        when (requestedAction) {
            Action.EDIT -> {
//                binding.header.text = getString(R.string.editing_message)
                binding.takeAction.text = getString(R.string.update)
                binding.name.setText(intent.getStringExtra(EXTRA_AUTHOR))
//                binding.message.setText(intent.getStringExtra(EXTRA_MESSAGE))
            }
            Action.CREATE -> {
//                binding.header.text = getString(R.string.add_new_message)
                binding.takeAction.text = getString(R.string.add)
            }
            else -> {}
        }

        binding.takeAction.setOnClickListener {
//            val text = binding.message.text.toString()
//            val text = ""
            val author = binding.name.text.toString()

            if (mText.isNotBlank() and author.isNotBlank()) {
                setResult(RESULT_OK, Intent().apply {
                    putExtra(EXTRA_MESSAGE, mText)
                    putExtra(EXTRA_AUTHOR, author)
                })
                finish()

            }

        }

    }

}
