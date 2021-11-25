package com.example.myviewapp.data

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myviewapp.R
import com.example.myviewapp.data.model.Message
import com.example.myviewapp.databinding.ListItemBinding
import java.text.DateFormat
import java.util.*

class MessageListAdapter(private val context: Context, private val items: List<Message>): RecyclerView.Adapter<MessageListAdapter.ViewHolder>() {
    interface ItemClickListener{
        fun onItemClicked(position: Int)
    }

    private lateinit var itemClickListener: ItemClickListener

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
        holder.root.setOnClickListener { itemClickListener?.onItemClicked(position)}
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


    fun setOnClickListener(listener: ItemClickListener){
        itemClickListener = listener
    }
}