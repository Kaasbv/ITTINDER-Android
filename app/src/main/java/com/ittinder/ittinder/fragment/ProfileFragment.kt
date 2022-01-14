package com.ittinder.ittinder.fragment

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ittinder.ittinder.data.RandomUserStream
import com.ittinder.ittinder.data.User
import com.ittinder.ittinder.databinding.FragmentProfileBinding
import com.ittinder.ittinder.viewmodel.UserViewModel

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val UserData = mutableListOf<User>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val userModel : UserViewModel by viewModels()
        userModel.getUser()


        fun setDataUser(user: User){
            this.UserData.clear()
            this.UserData.add(user)
        }

        fun bindData(user: User){
            binding.EditTextFirstName.setText(user.firstName)
            binding.EditTextMiddleName.setText(user.middleName)
            binding.EditTextSurname.setText(user.surname)
            binding.EditTextDoB.setText(user.dateOfBirth)
            binding.EditDescription.setText(user.description)
            binding.EditTextEmail.setText(user.email)
        }

        fun bindDataAfterSend(user: User) {
            user.firstName = binding.EditTextFirstName.text.toString()
            user.middleName = binding.EditTextMiddleName.text.toString()
            user.surname = binding.EditTextSurname.text.toString()
            user.dateOfBirth = binding.EditTextDoB.text.toString()
            user.description = binding.EditDescription.text.toString()
            user.email = binding.EditTextEmail.text.toString()
            user.password = binding.EditTextPassword.text.toString()
            user.middleName = binding.EditTextMiddleName.text.toString()
            setDataUser(user)


        }


            userModel.status.observe(this) {
                val returnValue = userModel.status.value
                if (returnValue != null) {
                    setDataUser(returnValue)
                    bindData(returnValue)
                    binding.login.setOnClickListener {
                        bindDataAfterSend(returnValue)
                        userModel.updateUser(UserData[0])

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