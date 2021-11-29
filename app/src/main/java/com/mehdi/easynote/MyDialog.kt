package com.mehdi.easynote

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
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

//        mBinding.dialogText.text = mText

        mBinding.alertText.apply{
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent{
                Text(stringResource(R.string.are_you_sure_you_want_to_remove_this_item), modifier = Modifier
                    .padding(dimensionResource(R.dimen.default_padding)))
            }
        }

        mBinding.dialogText.apply{
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent{
                Text(mText, modifier = Modifier.padding(dimensionResource(id = R.dimen.default_padding)),
                maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
        }


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