package com.ittinder.ittinder.fragment

import android.icu.text.DateFormat.DAY
import android.icu.text.DateTimePatternGenerator.DAY
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.map
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBindings
import com.ittinder.ittinder.MainActivity
import com.ittinder.ittinder.R
import com.ittinder.ittinder.data.Chat
import com.ittinder.ittinder.data.RandomUserStream
import com.ittinder.ittinder.viewmodel.RandomUserStreamViewModel
import com.ittinder.ittinder.data.Swiping
import com.ittinder.ittinder.viewmodel.SwipingViewModel
import com.ittinder.ittinder.databinding.FragmentSwipeScreenBinding
import com.ittinder.ittinder.util.CoilImageLoader
import com.ittinder.ittinder.util.ImageLoader
import java.time.Year
import java.util.*
import kotlin.random.Random

private const val BASE_URL = "http://10.0.2.2:8080"

class SwipeScreen : Fragment(R.layout.fragment_swipe_screen) {
    private val chatsData = mutableListOf<Chat>()
    private var _binding: FragmentSwipeScreenBinding? = null
    private val binding get() = _binding!!
    private val userData = mutableListOf<RandomUserStream>()
    private val imageLoader: ImageLoader = CoilImageLoader()



    fun calculateAge(dateOfBirth : String): Int {
        val year = Calendar.getInstance().get(Calendar.YEAR)
        val month = Calendar.getInstance().get(Calendar.MONTH)
        val day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        val userYear = Integer.parseInt(dateOfBirth.subSequence(0, 4) as String)
        val userMonth = Integer.parseInt(dateOfBirth.subSequence(6, 7) as String)
        val userDay = Integer.parseInt(dateOfBirth.subSequence(9, 10) as String)

        if ((userMonth-1) <= month &&  (userDay <= day)) {
            return year - userYear
        }
        return (year - userYear) - 1
    }

    fun setData(userStream: RandomUserStream){
        this.userData.clear()
        this.userData.add(userStream)
    }
    fun bindData(randomUserStream: RandomUserStream) {
        binding.Name.text = randomUserStream.firstName
        binding.age.text = calculateAge(randomUserStream.dateOfBirth).toString()
        binding.description.text = randomUserStream.description
        if (randomUserStream.image.isEmpty()) {
            imageLoader.loadImage("https://assets.ey.com/content/dam/ey-sites/ey-com/en_gl/people/m/ey-matthew-harold-meta.jpg", binding.imageView)

        }
        else {
            imageLoader.loadImage(BASE_URL + randomUserStream.image[0].photosImagePath, binding.imageView)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSwipeScreenBinding.inflate(inflater, container, false)

        val userStreamModel: RandomUserStreamViewModel by viewModels()
        val swipingModel: SwipingViewModel by viewModels()
        userStreamModel.RandomUserStreamResponse.observe(this) {
            val json = userStreamModel.RandomUserStreamResponse.value?.elementAt(0)
            if (json != null) {
                setData(json)
                bindData(userData[0])
            }
        }
        binding.get.setOnClickListener {
            userStreamModel.getUserStream()
        }
        binding.like.setOnClickListener{
            val swipeRight = Swiping(9, 1)
            swipingModel.postSwipeRight(swipeRight)
            userStreamModel.getUserStream()
        }
        binding.dislike.setOnClickListener{
            val swipeLeft = Swiping(9,1)
            swipingModel.postSwipeLeft(swipeLeft)
            userStreamModel.getUserStream()
        }

        return binding.root
    }
/*
    override fun onViewCreated (view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //SwipeScreenDirections.actionSwipeScreenToChatList()



    }
*/



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}