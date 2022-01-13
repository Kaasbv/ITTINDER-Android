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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ittinder.ittinder.adapter.ChatsAdapter
import com.ittinder.ittinder.adapter.MessagesAdapter
import com.ittinder.ittinder.databinding.FragmentChatBinding
import com.ittinder.ittinder.entities.MessageEntity
import com.ittinder.ittinder.repository.MessageRepository
import com.ittinder.ittinder.util.CoilImageLoader
import com.ittinder.ittinder.viewmodel.ChatViewModel
import com.ittinder.ittinder.viewmodel.MessageViewModel

class Chat : Fragment() {
    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)

        // requestie
        val viewModel: MessageViewModel by viewModels();

        // set recyclerview layout:
        val recyclerView: RecyclerView = binding.chatMessagesRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true)
            .apply { stackFromEnd = true }
        // connect adapter to recyclerview:
        val messagesAdapter =  MessagesAdapter(layoutInflater) { adapter, index ->
            viewModel.getMessageByPositionAndUserId(context!!, index, 18).observe(this) { message ->
                Log.i("FILL", index.toString())
                adapter.putData(index, message)
                recyclerView.post(Runnable { adapter.refresh() })
            }
        }

        viewModel.getMessagesCountByUserId(context!!, 18).observe(this) { count ->
            messagesAdapter.refresh(count)
        }

        recyclerView.adapter = messagesAdapter

        recyclerView.post(Runnable {
            (recyclerView.layoutManager as LinearLayoutManager).scrollToPosition(0)
        })

        val mainHandler = Handler(Looper.getMainLooper())

        mainHandler.post(object : Runnable {
            override fun run() {
                viewModel.sync(context!!, activity!!)
//                Log.i("REE", "Works?")
                mainHandler.postDelayed(this, 1000)
            }
        })

        viewModel.lastChanged.observe(this) {
            Log.i("REFRESH", "ME REFRESHING YAY")
            messagesAdapter.resetData()
            recyclerView.post(Runnable {
                (recyclerView.layoutManager as LinearLayoutManager).scrollToPosition(0)
                messagesAdapter?.refresh(viewModel.count)
            })
        }

        binding.chatSubmitButton.setOnClickListener {
            binding.chatEdittext.text
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}