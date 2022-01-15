package com.ittinder.ittinder.fragment

import android.content.ContentValues.TAG
import android.icu.text.DateFormat.DAY
import android.icu.text.DateTimePatternGenerator.DAY
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.map
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBindings
import com.ittinder.ittinder.MainActivity
import com.ittinder.ittinder.R
import com.ittinder.ittinder.data.RandomUserStream
import com.ittinder.ittinder.data.Swiping
import com.ittinder.ittinder.data.User
import com.ittinder.ittinder.viewmodel.SwipingViewModel
import com.ittinder.ittinder.databinding.FragmentSwipeScreenBinding
import com.ittinder.ittinder.util.CoilImageLoader
import com.ittinder.ittinder.util.ImageLoader
import com.ittinder.ittinder.viewmodel.UserViewModel
import java.time.Year
import java.util.*
import kotlin.concurrent.thread
import kotlin.random.Random

private const val BASE_URL = "http://10.0.2.2:8080"

class SwipeScreen : Fragment(R.layout.fragment_swipe_screen) {
    private var _binding: FragmentSwipeScreenBinding? = null
    private val binding get() = _binding!!
    private val userData = mutableListOf<RandomUserStream>()
    private val ownUserData = mutableListOf<User>()
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

    fun setDataUser(user: User){
        this.ownUserData.clear()
        this.ownUserData.add(user)
    }

    fun bindData(randomUserStream: RandomUserStream, user: User) {
        val swipingModel: SwipingViewModel by viewModels()
        val user1 = user.id
        val user2 = randomUserStream.id
        binding.Name.text = randomUserStream.firstName
        binding.Name.textSize = 40F
        binding.comma.text = ","
        binding.age.text = calculateAge(randomUserStream.dateOfBirth).toString()
        binding.description.text = randomUserStream.description
        if (randomUserStream.image.isEmpty()) {
            imageLoader.loadImage("https://assets.ey.com/content/dam/ey-sites/ey-com/en_gl/people/m/ey-matthew-harold-meta.jpg", binding.imageView)
        }
        else {
            imageLoader.loadImage(BASE_URL + randomUserStream.image[0].photosImagePath, binding.imageView)
        }

        binding.like.setOnClickListener{
            swipingModel.postSwipeRight(user1.toInt(), user2)
            Thread.sleep(100)
            findNavController().navigate(SwipeScreenDirections.actionSwipeScreenLike())
        }

        binding.dislike.setOnClickListener{
            swipingModel.postSwipeLeft(user1.toInt(), user2)
            Thread.sleep(100)
            findNavController().navigate(SwipeScreenDirections.actionSwipeScreenDislike())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        _binding = FragmentSwipeScreenBinding.inflate(inflater, container, false)

        val userModel : UserViewModel by viewModels()

        userModel.getUserStream()
        userModel.RandomUserStreamResponse.observe(this) {
            if (userModel.RandomUserStreamResponse.value.isNullOrEmpty() ) {
                binding.Name.text = "No available users to swipe"
                binding.Name.textSize = 25F
                binding.comma.text = ""
                binding.description.text = ""

            }
            else {
                userModel.getUser()
                userModel.status.observe(this) {
                    val returnValue = userModel.status.value
                    if (returnValue != null) {
                        setDataUser(returnValue)
                    }
                    val json = userModel.RandomUserStreamResponse.value?.elementAt(0)
                    if (json != null) {
                        setData(json)
                        bindData(userData[0], ownUserData[0])
                    }
                }
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}