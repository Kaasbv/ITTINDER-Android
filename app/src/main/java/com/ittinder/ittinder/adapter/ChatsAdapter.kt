package com.ittinder.ittinder.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ittinder.ittinder.data.Chat
import com.ittinder.ittinder.R
import com.ittinder.ittinder.util.ImageLoader




class ChatsAdapter(
    private val layoutInflater: LayoutInflater,
    private val imageLoader: ImageLoader,
    private val onItemClicked: (Chat) -> Unit
) : RecyclerView.Adapter<ChatsAdapter.ChatViewHolder>() {
    private val chatsData = mutableListOf<Chat>()

    fun setData(chatsData: List<Chat>) {
        this.chatsData.clear()
        this.chatsData.addAll(chatsData)

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = layoutInflater.inflate(R.layout.item_chat, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val current = chatsData[position]
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bindData(current)
    }

     override fun getItemCount(): Int {
         return chatsData.size
     }


    inner class ChatViewHolder(
        containerView: View,
    ) : RecyclerView.ViewHolder(containerView) {
        val chatLastMessage: TextView =  containerView.findViewById(R.id.item_chat_last_message)
        val chatNameView: TextView = containerView.findViewById(R.id.item_chat_name)
        val chatPhotoView: ImageView = containerView.findViewById(R.id.item_chat_photo)

        fun bindData(chat: Chat) {
            imageLoader.loadImage("https://assets.ey.com/content/dam/ey-sites/ey-com/en_gl/people/m/ey-matthew-harold-meta.jpg", chatPhotoView)
            chatNameView.text = chat.initiatedUser.firstName
            chatLastMessage.text = chat.lastMessage.message.toString()
        }
    }
}