package com.ittinder.ittinder.fragment

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ittinder.ittinder.adapter.ChatsAdapter
import com.ittinder.ittinder.adapter.MessagesAdapter
import com.ittinder.ittinder.databinding.FragmentChatBinding
import com.ittinder.ittinder.entities.MessageEntity
import com.ittinder.ittinder.repository.MessageRepository
import com.ittinder.ittinder.util.CoilImageLoader
import com.ittinder.ittinder.util.ImageLoader
import com.ittinder.ittinder.viewmodel.ChatViewModel
import com.ittinder.ittinder.viewmodel.MessageViewModel

class Chat : Fragment() {
    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!
    private val navigationArgs: ChatArgs by navArgs()
    private val imageLoader: ImageLoader = CoilImageLoader()
    private var loopRunning = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        val viewModel: MessageViewModel by viewModels();
        val firstname = navigationArgs.firstname
        val chatId = navigationArgs.chatId
        val photoUrl = navigationArgs.photoUrl

        //Bind
        binding.chatName.text = firstname
        imageLoader.loadImage(photoUrl, binding.chatProfileImage)

        //Start main loop
        val mainHandler = Handler(Looper.getMainLooper())

        mainHandler.post(object : Runnable {
            override fun run() {
                if(!loopRunning) return
                viewModel.sync(context!!, activity!!, chatId)
                mainHandler.postDelayed(this, 1000)
            }
        })

        // set recyclerview layout:
        val recyclerView: RecyclerView = binding.chatMessagesRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true)
            .apply { stackFromEnd = true }
        // connect adapter to recyclerview:
        val messagesAdapter =  MessagesAdapter(layoutInflater) { adapter, index ->
            viewModel.getMessageByPositionAndUserId(context!!, index, chatId).observe(this) { message ->
                adapter.putData(index, message)
                recyclerView.post(Runnable {
                    adapter.refresh()
                })
            }
        }
        recyclerView.adapter = messagesAdapter

        //Get messages count for adapter
        viewModel.getMessagesCountByUserId(context!!, chatId).observe(this) { count ->
            recyclerView.post(Runnable { messagesAdapter.refresh(count) })
        }

        //Scroll down
        recyclerView.post(Runnable {
            (recyclerView.layoutManager as LinearLayoutManager).scrollToPosition(0)
        })

        //Refresh on new message
        viewModel.lastChanged.observe(this) {
            messagesAdapter.resetData()
            recyclerView.post(Runnable {//Scroll and refresh
                (recyclerView.layoutManager as LinearLayoutManager).scrollToPosition(0)
                messagesAdapter?.refresh(viewModel.count)
            })
        }

        binding.chatSubmitButton.setOnClickListener {
            viewModel.postMessage(chatId, binding.chatEdittext.text.toString(), activity!!)
        }

        return binding.root
    }

    override fun onDestroyView() {
        loopRunning = false
        super.onDestroyView()
        _binding = null
    }
}