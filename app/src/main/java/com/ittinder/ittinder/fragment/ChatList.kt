package com.ittinder.ittinder.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ittinder.ittinder.adapter.ChatsAdapter
import com.ittinder.ittinder.util.CoilImageLoader
import com.ittinder.ittinder.viewmodel.ChatViewModel
import com.ittinder.ittinder.databinding.FragmentChatListBinding
import com.ittinder.ittinder.viewmodel.BaseViewModel

class ChatList : Fragment() {

    private var _binding: FragmentChatListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatListBinding.inflate(inflater, container, false)

        val pref = activity!!.getPreferences(Context.MODE_PRIVATE)
        val userId: Long = pref.getLong("user_id", 0)

        // set recyclerview layout:
        val recyclerView: RecyclerView = binding.chatRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        // connect adapter to recyclerview:
        val chatsAdapter = ChatsAdapter(layoutInflater, CoilImageLoader(), userId) {
            val action =
                ChatListDirections.actionChatListToChat(it.id, if (it.initiatedUser.id != userId) it.initiatedUser.firstName else it.affectedUser.firstName, "lol")
            this.findNavController().navigate(action)
        }

        recyclerView.adapter = chatsAdapter

        // requestie
        val viewModel: ChatViewModel by viewModels();
        viewModel.listChats(activity!!)
        viewModel.result.observe(this) {
            chatsAdapter.setData(viewModel.result.value!!)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}