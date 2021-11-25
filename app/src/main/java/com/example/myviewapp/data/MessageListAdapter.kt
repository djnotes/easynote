package com.example.myviewapp.data

import android.content.Context
import android.text.format.DateFormat.format
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myviewapp.R
import com.example.myviewapp.data.model.Message
import com.example.myviewapp.databinding.ListItemBinding
import java.lang.String.format
import java.text.DateFormat
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.*

class MessageListAdapter(private val context: Context, private val items: List<Message>): RecyclerView.Adapter<MessageListAdapter.ViewHolder>() {
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ListItemBinding.bind(itemView)
        val itemText = binding.itemText
        val itemAuthor = binding.itemAuthor
        val itemDate = binding.itemDate
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
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
}