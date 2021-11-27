package com.mehdi.easynote.data

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mehdi.easynote.R
import com.mehdi.easynote.data.model.Message
import com.mehdi.easynote.databinding.ListItemBinding
import java.text.DateFormat
import java.util.*

class MessageListAdapter(private val context: Context, public var items: List<Message> = listOf()): RecyclerView.Adapter<MessageListAdapter.ViewHolder>() {

    interface ItemClickListener{
        fun onItemClicked(position: Int)
    }

    interface ItemLongClickListener{
        fun onItemLongClicked(position: Int)
    }

    private var mItemClickListener: ItemClickListener? = null
    private var mItemLongClickListener: ItemLongClickListener? = null


    fun updateList(items: List<Message>){
        this.items = items
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ListItemBinding.bind(itemView)
        val root = binding.root
        val itemText = binding.itemText
        val itemAuthor = binding.itemAuthor
        val itemDate = binding.itemDate
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.root.setOnClickListener { mItemClickListener?.onItemClicked(position)}
        holder.root.setOnLongClickListener{
            mItemLongClickListener?.onItemLongClicked(position)
            true
        }
        holder.itemText.text = items[position].text
        holder.itemAuthor.text = items[position].author
//        val date = Date.from(Instant.ofEpochSecond(items[position].timestamp)).toString()
        val df = DateFormat.getDateInstance()
        val time = df.format(Date(items[position].timestamp))
        holder.itemDate.text = time

    }

    override fun getItemCount(): Int {
        return items.size
    }


    fun setOnItemClickListener(listener: ItemClickListener){
        mItemClickListener = listener
    }

    fun setOnItemLongClickListener(listener: ItemLongClickListener){
        mItemLongClickListener = listener
    }
}