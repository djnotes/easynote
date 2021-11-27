package com.mehdi.easynote

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.mehdi.easynote.databinding.MyDialogBinding

class MyDialog: DialogFragment() {
    private lateinit var mBinding: MyDialogBinding
    private lateinit var mText: String
    private var mItemId: Long? = null
    private var mItemCallback: ItemCallback? = null

    companion object{
        const val EXTRA_TEXT = "com.mehdi.easynote.MyDialog.extra_text"
        const val EXTRA_MESSAGE_ID = "com.mehdi.easynote.MyDialog.extra_message_id"
    }

    interface ItemCallback{
        fun onConfirmDelete(id: Long)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = MyDialogBinding.inflate(layoutInflater, container, false)

        mBinding.dialogText.text = mText
        mBinding.confirm.setOnClickListener {
            Log.d("MyDialog", "onCreateView: confirm clicked")
            mItemId?.let {
                mItemCallback?.onConfirmDelete(it)
                dismiss()
            }
        }

        mBinding.cancel.setOnClickListener { dismiss()}

        return mBinding.root
//        return super.onCreateView(inflater, container, savedInstanceState)

    }

    override fun setArguments(args: Bundle?) {
        super.setArguments(args)
        mText = args?.getString(EXTRA_TEXT).toString()
        mItemId = args?.getLong(EXTRA_MESSAGE_ID)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(EXTRA_TEXT, mText)
        mItemId?.let { outState.putLong(EXTRA_MESSAGE_ID, it) }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.let {saved->
            mText = saved.getString(EXTRA_TEXT).toString()
            mItemId = saved.getLong(EXTRA_MESSAGE_ID)
        }
    }

    fun setItemCallback(itemCallback: ItemCallback){
        mItemCallback = itemCallback
    }
}