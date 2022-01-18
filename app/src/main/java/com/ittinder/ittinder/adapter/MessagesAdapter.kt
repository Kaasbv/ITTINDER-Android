package com.ittinder.ittinder.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ittinder.ittinder.R
import com.ittinder.ittinder.entities.MessageEntity

class MessagesAdapter(
    private val layoutInflater: LayoutInflater,
    private val getData: (MessagesAdapter, Int) -> Unit
) : RecyclerView.Adapter<MessagesAdapter.MessageViewHolder>() {
    private val cachedData = mutableMapOf<Int,MessageEntity>() //Holds cache of messages
    private var length: Int = 0 //Holds current length of messages in room

    fun refresh(newLength: Int = length) {//Refresh recyckler view and possibly update length
        length = newLength
        notifyDataSetChanged()
    }

    fun putData(position: Int, message: MessageEntity){//Put message in cache
        cachedData[position] = message
    }

    fun resetData(){//Reset cache
        cachedData.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = layoutInflater.inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        //Grab message from message
        var message: MessageEntity? = cachedData[position];
        if(message == null){//If message doesnt exists in cache initiate grabbing at and while waiting put in a placeholder
            getData(this, position)
            cachedData[position] = MessageEntity(1, "Loading...", "Loading...", "", 1, 1)
        }
        holder.bindData(cachedData[position]!!) //bind view
    }

     override fun getItemCount(): Int {
         return length
     }


    inner class MessageViewHolder(
        containerView: View,
    ) : RecyclerView.ViewHolder(containerView) {
        private val chatNameView: TextView = containerView.findViewById(R.id.item_message_name)
        private val chatMessageView: TextView =  containerView.findViewById(R.id.item_message_message)

        fun bindData(message: MessageEntity) {
            //Bind data on view
            chatNameView.text = message.name
            chatMessageView.text = message.message
        }
    }
}