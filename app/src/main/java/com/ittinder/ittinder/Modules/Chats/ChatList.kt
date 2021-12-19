package com.ittinder.ittinder.Modules.Chats

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ittinder.ittinder.Modules.Chats.adapter.CatsAdapter
import com.ittinder.ittinder.Modules.Chats.data.TestDatasources
import com.ittinder.ittinder.Modules.Chats.util.CoilImageLoader
import com.ittinder.ittinder.Modules.Chats.viewmodel.ChatViewModel
import com.ittinder.ittinder.databinding.FragmentChatListBinding
import com.ittinder.ittinder.R

class ChatList : Fragment() {

    private var _binding: FragmentChatListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        val navHostFragment = parentFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatListBinding.inflate(inflater, container, false)

        // set recyclerview layout:
        val recyclerView: RecyclerView = binding.chatRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        // connect adapter to recyclerview:
        val catsAdapter = CatsAdapter(layoutInflater, CoilImageLoader())
        catsAdapter.setData(TestDatasources().loadCats())
        recyclerView.adapter = catsAdapter

        // requestie
        val viewModel: ChatViewModel by viewModels();
        viewModel.listChats()

        binding.button.setOnClickListener {
            navController.navigateUp()
        }

        return binding.root
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
////
////        binding.showDescriptionBtn.setOnClickListener {
////            val recipe = binding.recipeSelectionSpn.selectedItem.toString()
////            binding.descriptionTextview.text = getRecipeDescription(recipe)
////        }
////
////        binding.recipeDetailsBtn.setOnClickListener {
////            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
////        }
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}