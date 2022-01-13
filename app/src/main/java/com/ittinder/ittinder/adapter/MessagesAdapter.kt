package com.ittinder.ittinder.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ittinder.ittinder.R
import com.ittinder.ittinder.data.Message
import com.ittinder.ittinder.entities.MessageEntity

class MessagesAdapter(
    private val layoutInflater: LayoutInflater,
    private val getData: (MessagesAdapter, Int) -> Unit
) : RecyclerView.Adapter<MessagesAdapter.MessageViewHolder>() {
    private val cachedData = mutableMapOf<Int,MessageEntity>()
    private var length: Int = 0

    fun refresh(newLength: Int = length) {
        Log.i("LENCHANGE", "Someone says length is " + length.toString())
        length = newLength
        notifyDataSetChanged()
    }

    fun putData(position: Int, message: MessageEntity){
        cachedData[position] = message
        Log.i("LEN", cachedData.size.toString())
    }

    fun resetData(){
        cachedData.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = layoutInflater.inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        Log.i("RECYC", "asking for " + position.toString())
        var message: MessageEntity? = cachedData[position];
        if(message == null){
            getData(this, position)
            cachedData[position] = MessageEntity(1, "Loading...", "Loading...", "", 1)
        }
        holder.bindData(cachedData[position]!!)
    }

     override fun getItemCount(): Int {
         Log.i("RECYC", "asking for length" + length.toString())
         return length
     }


    inner class MessageViewHolder(
        containerView: View,
    ) : RecyclerView.ViewHolder(containerView) {
        private val chatNameView: TextView = containerView.findViewById(R.id.item_message_name)
        private val chatMessageView: TextView =  containerView.findViewById(R.id.item_message_message)

        fun bindData(message: MessageEntity) {
            chatNameView.text = message.name
            chatMessageView.text = message.message
        }
    }
}