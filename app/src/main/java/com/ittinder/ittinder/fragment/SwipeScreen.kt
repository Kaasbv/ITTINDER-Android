package com.ittinder.ittinder.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ittinder.ittinder.MainActivity
import com.ittinder.ittinder.R
import com.ittinder.ittinder.data.User
import com.ittinder.ittinder.viewmodel.SwipingViewModel
import com.ittinder.ittinder.databinding.FragmentSwipeScreenBinding
import com.ittinder.ittinder.util.CoilImageLoader
import com.ittinder.ittinder.util.GPSUtils
import com.ittinder.ittinder.util.ImageLoader
import com.ittinder.ittinder.viewmodel.UserViewModel
import java.util.*

private const val BASE_URL = "http://10.0.2.2:8080"

class SwipeScreen : Fragment(R.layout.fragment_swipe_screen)  {
    private var _binding: FragmentSwipeScreenBinding? = null
    private val binding get() = _binding!!
    private val userData = mutableListOf<User>()
    private val ownUserData = mutableListOf<User>()
    private val imageLoader: ImageLoader = CoilImageLoader()

    //Small function to calculate age bases on current date and date of birth
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

    fun getLocation(user: User){
        //usage of gps sensors to add location to user and update it to the DB
        val userModel : UserViewModel by viewModels()
        val oldLat = user.latitude
        val oldLong = user.longitude
        activity?.let { it1 -> GPSUtils.findDeviceLocation(it1) }
        val longitude = GPSUtils.longitude
        val latitude = GPSUtils.latitude

        if (oldLong != longitude && oldLat != latitude) {
            if (longitude != null) {
                user.longitude = longitude
            }
            if (latitude != null) {
                user.latitude = latitude
            }
            setDataUser(user)
            //Update user with lat lon
            userModel.updateUser(ownUserData[0], requireActivity())
        }
    }

    private fun setData(user: User){
        this.userData.clear()
        this.userData.add(user)
    }

    private fun setDataUser(user: User){
        this.ownUserData.clear()
        this.ownUserData.add(user)
    }

    private fun bindData(otherUser: User, user: User) {
        val swipingModel: SwipingViewModel by viewModels()
        val user1 = user.id
        val user2 = otherUser.id

        binding.Name.text = otherUser.firstName
        binding.Name.textSize = 40F
        binding.comma.text = ","
        binding.age.text = calculateAge(otherUser.dateOfBirth).toString()
        binding.description.text = otherUser.description
        if (otherUser.image == null) {
            imageLoader.loadImage("https://assets.ey.com/content/dam/ey-sites/ey-com/en_gl/people/m/ey-matthew-harold-meta.jpg", binding.imageView)
        }
        else {
            imageLoader.loadImage(BASE_URL + otherUser.image, binding.imageView)
        }

        binding.like.setOnClickListener{
            swipingModel.postSwipeRight(user1, user2)
            Thread.sleep(100)
            findNavController().navigate(SwipeScreenDirections.actionSwipeScreenLike())
        }

        binding.dislike.setOnClickListener{
            swipingModel.postSwipeLeft(user1, user2)
            Thread.sleep(100)
            findNavController().navigate(SwipeScreenDirections.actionSwipeScreenDislike())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        _binding = FragmentSwipeScreenBinding.inflate(inflater, container, false)

        //Set menu on swipescreen
        (activity as MainActivity).showMenu = true
        requireActivity().invalidateMenu()

        val userModel : UserViewModel by viewModels()
        //Grab userstreams
        userModel.getUserStream(activity!!)
        //Observer user stream response
        userModel.randomUserStreamResponse.observe(this) {
            if (userModel.randomUserStreamResponse.value.isNullOrEmpty() ) {//If no users found
                binding.Name.text = "No available users to swipe"
                binding.Name.textSize = 25F
                binding.comma.text = ""
                binding.description.text = ""
            }
            else {
                //Grab own user as well
                userModel.getUser(activity!!)
                userModel.user.observe(this) {
                    val returnValue = userModel.user.value
                    if (returnValue != null) {
                        setDataUser(returnValue)
                        getLocation(returnValue)
                    }
                    val json = userModel.randomUserStreamResponse.value?.elementAt(0)
                    if (json != null) {
                        setData(json)
                        //Bind data to view
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