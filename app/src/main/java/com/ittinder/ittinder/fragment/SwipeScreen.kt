package com.ittinder.ittinder.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ittinder.ittinder.viewmodel.RandomUserStreamViewModel
import com.ittinder.ittinder.data.Swiping
import com.ittinder.ittinder.viewmodel.SwipingViewModel
import com.ittinder.ittinder.databinding.FragmentSwipeScreenBinding

class SwipeScreen : Fragment() {
    private var _binding: FragmentSwipeScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSwipeScreenBinding.inflate(inflater, container, false)

        val userStreamModel: RandomUserStreamViewModel by viewModels()
        val swipingModel: SwipingViewModel by viewModels()
        userStreamModel.RandomUserStreamResponse.observe(this) {
            binding.result.text = userStreamModel.RandomUserStreamResponse.value
        }
        binding.get.setOnClickListener {
            userStreamModel.getUserStream()
        }
        binding.like.setOnClickListener{
            val swipeRight = Swiping(9, 1)
            swipingModel.postSwipeRight(swipeRight)
        }
        binding.dislike.setOnClickListener{
            val swipeLeft = Swiping(9,1)
            swipingModel.postSwipeLeft(swipeLeft)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}