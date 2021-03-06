package com.ittinder.ittinder.fragment

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ittinder.ittinder.MainActivity
import com.ittinder.ittinder.data.User
import com.ittinder.ittinder.databinding.FragmentProfileBinding
import com.ittinder.ittinder.util.CoilImageLoader
import com.ittinder.ittinder.util.ImageLoader
import com.ittinder.ittinder.viewmodel.UserViewModel
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*

private const val BASE_URL = "http://10.0.2.2:8080"

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val imageLoader: ImageLoader = CoilImageLoader()
    private lateinit var currentPhotoPath: String
    private val REQUEST_IMAGE_CAPTURE = 200
    val userViewModel : UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        //Get user
        userViewModel.getUser(activity!!)

        //Set on click listener for update burron
        binding.updateProfileButton.setOnClickListener {
            //Bind field values on user
            val user: User = userViewModel.user.value!!
            user.firstName = binding.EditTextFirstName.text.toString()
            user.middleName = binding.EditTextMiddleName.text.toString()
            user.surname = binding.EditTextSurname.text.toString()
            user.dateOfBirth = binding.EditTextDoB.text.toString()
            user.description = binding.EditDescription.text.toString()
            user.email = binding.EditTextEmail.text.toString()
            user.password = binding.EditTextPassword.text.toString()
            user.middleName = binding.EditTextMiddleName.text.toString()
            user.gender = checkGender()
            user.interestedInGender = checkGenderPreference()
            //Run update
            userViewModel.updateUser(user, activity!!)
        }

        //Observer for get user and bind if it works
        userViewModel.user.observe(this) {
            val returnValue = userViewModel.user.value
            if (returnValue != null) {
                bindData(returnValue)
            }
        }

        //Add event listener for upload image
        binding.profileImageButton.setOnClickListener {
            capturePhoto()
        }

        return binding.root
    }

    private fun bindData(user: User){//Bind data on fields
        binding.EditTextFirstName.setText(user.firstName)
        binding.EditTextMiddleName.setText(user.middleName)
        binding.EditTextSurname.setText(user.surname)
        binding.EditTextDoB.setText(user.dateOfBirth)
        binding.EditDescription.setText(user.description)
        binding.EditTextEmail.setText(user.email)
        genderRadioButtons(user)
        genderPrefRadioButtons(user)

        if (user.image == null) {
            imageLoader.loadImage("https://assets.ey.com/content/dam/ey-sites/ey-com/en_gl/people/m/ey-matthew-harold-meta.jpg", binding.profileImageView)
        }
        else {
            imageLoader.loadImage(BASE_URL + user.image, binding.profileImageView)
        }
    }

    private fun capturePhoto() {
        //Create intent for capturing photo
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            val photoFile: File? = try {
                createImageFile()
            } catch (ex: IOException) {
                null
            }
            photoFile?.also {
                val photoURI: Uri = FileProvider.getUriForFile(
                    requireContext(),
                    "com.ittinder.ittinder.fileprovider",
                    it
                )
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }

    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        //Create temporary file for image
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            currentPhotoPath = absolutePath
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //Handler for camera response
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_IMAGE_CAPTURE && data != null) {
            //Grab input stream
            val selectedImageUri = Uri.fromFile(File(currentPhotoPath))
            val ims: InputStream? =
                requireContext().contentResolver.openInputStream(selectedImageUri)

            if (ims != null) {
                //send input stream to upload image
                userViewModel.uploadImage(requireActivity(), ims).observe(this){ user ->
                    //If image is updated rebind data
                    bindData(user)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkGender(): String {//Grab gender from input fields
        var gender = ""

        if (binding.radioButtonMen.isChecked) {
            gender = "Male"
        } else if (binding.radioButtonWomen.isChecked) {
            gender = "Female"
        } else if (binding.radioButtonOther.isChecked) {
            gender = "Non Binary"
        }
        return gender
    }

    private fun genderRadioButtons(user : User) {//set radio buttons for gender
        if (user.gender == "Male"){
            binding.radioButtonMen.isChecked = true
        }
        else if (user.gender == "Female"){
            binding.radioButtonWomen.isChecked = true
        }
        else if (user.gender == "Non Binary"){
            binding.radioButtonOther.isChecked = true
        }
    }

    private fun checkGenderPreference(): String {//Grab gender preference from radio buttons
        var genderPreference = ""

        if (binding.radioButtonMenPref.isChecked) {
            genderPreference = "Male"
        }
        else if (binding.radioButtonWomenPref.isChecked) {
            genderPreference = "Female"
        }
        else if (binding.radioButtonOtherPref.isChecked) {
            genderPreference = "Non Binary"
        }
        else if (binding.radioButtonDoesntMatterPref.isChecked) {
            genderPreference = "Doesn't matter"
        }
        return genderPreference
    }

    private fun genderPrefRadioButtons(user : User) {//Set gender preference
        if (user.interestedInGender == "Male"){
            binding.radioButtonMenPref.isChecked = true
        }
        else if (user.interestedInGender == "Female"){
            binding.radioButtonWomenPref.isChecked = true
        }
        else if (user.interestedInGender == "Non Binary"){
            binding.radioButtonOtherPref.isChecked = true
        }
        else if (user.interestedInGender == "Doesn't matter"){
            binding.radioButtonDoesntMatterPref.isChecked = true
        }
    }
}