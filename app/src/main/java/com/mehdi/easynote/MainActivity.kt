package com.mehdi.easynote

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.liveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mehdi.easynote.data.model.Message
import com.mehdi.easynote.data.model.MessageViewModel
import com.mehdi.easynote.data.model.MessageViewModelFactory
import com.mehdi.easynote.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.mehdi.easynote.data.MessageListAdapter
import kotlinx.coroutines.*
import kotlin.coroutines.coroutineContext
import kotlin.coroutines.suspendCoroutine

class MainActivity : AppCompatActivity(), MessageListAdapter.ItemClickListener,
    MessageListAdapter.ItemLongClickListener, MyDialog.ItemCallback {
    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mActivityLauncher: ActivityResultLauncher<Intent>
    private lateinit var recyclerView: RecyclerView
    private val mAdapter: MessageListAdapter = MessageListAdapter(this).apply{
        setOnItemClickListener(this@MainActivity)
        setOnItemLongClickListener(this@MainActivity)
    }



    private var mSelectedItemID: Long? = null

    private val mViewModel: MessageViewModel by viewModels {
        MessageViewModelFactory((application as EasyNoteApp).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Binding class for this activity's layout
        mBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(mBinding.root)

        recyclerView = mBinding.messagesList
        recyclerView.layoutManager = LinearLayoutManager(this)






        mActivityLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){result->
            if (result.resultCode == Activity.RESULT_OK){
                result.data?.apply{
                    val messageText = getStringExtra(EditActivity.EXTRA_MESSAGE)
                    val messageAuthor = getStringExtra(EditActivity.EXTRA_AUTHOR)

                            if (mSelectedItemID == null) {
                                mViewModel.addMessage(
                                    Message(
                                        0L, //0 Means generate UID
                                        messageText.toString(),
                                        messageAuthor.toString()
                                    )
                                )
                                Snackbar.make(
                                    mBinding.root,
                                    R.string.message_added_successfully,
                                    Snackbar.LENGTH_SHORT
                                )
                                    .show()
                            } else {
                                mSelectedItemID?.let {
                                    mViewModel.updateMessage(
                                        Message(
                                            it,
                                            messageText.toString(),
                                            messageAuthor.toString()
                                        )
                                    )
                                }
                                Snackbar.make(
                                    mBinding.root,
                                    R.string.message_updated_successfully,
                                    Snackbar.LENGTH_SHORT
                                ).show()

                                //Set selected item to null
                                mSelectedItemID = null
                            }
                }

            }
        }

        mBinding.fab.setOnClickListener {
            val intent = Intent(
                this,
                EditActivity::class.java
            ).apply{
                action = "com.mehdi.easynote.${EditActivity.Action.CREATE.name}"
            }
            mActivityLauncher.launch(intent)
        }

        //Add observer for items in database
        mViewModel.messages.observe(this){
            recyclerView.adapter = mAdapter.apply{
                updateList(it)
            }

        }

    }

    override fun onItemClicked(position: Int) {
        Log.d("MainActivity", "onItemClicked: $position")
        val item = mAdapter.items[position]
        mSelectedItemID = item.uid

        mActivityLauncher.launch(Intent(this, EditActivity::class.java)
            .apply{
                action = "com.mehdi.easynote.${EditActivity.Action.EDIT.name}"
                putExtra(EditActivity.EXTRA_AUTHOR, item.author)
                putExtra(EditActivity.EXTRA_MESSAGE, item.text)
            }
        )
    }

    override fun onItemLongClicked(position: Int) {
        val selectedItem = mAdapter.items[position]
        Log.d("MainActivity", "onItemLongClicked: $position. id: ${selectedItem.uid}")

        MyDialog()
            .apply{
                arguments = Bundle().apply{
                    putString(MyDialog.EXTRA_TEXT, selectedItem.text)
                    putLong(MyDialog.EXTRA_MESSAGE_ID, selectedItem.uid)
                }
                setItemCallback(this@MainActivity)
            }
            .show(supportFragmentManager, "my_dialog")
    }

    override fun onConfirmDelete(id: Long) {
        Log.d("MainActivity", "onConfirmDelete:")
        val message = mAdapter.items.find { it.uid == id}
            message?.let {
                mViewModel.removeMessage(it)
                Snackbar.make(
                    mBinding.root,
                    R.string.message_delete_successfully,
                    Snackbar.LENGTH_SHORT
                )
                    .show()
            }
    }

}
