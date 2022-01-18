package com.ittinder.ittinder.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ittinder.ittinder.R
import com.ittinder.ittinder.data.RegisterObject
import com.ittinder.ittinder.databinding.FragmentRegisterBinding
import com.ittinder.ittinder.viewmodel.UserViewModel


class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        val viewModel : UserViewModel by viewModels()


        binding.registerSubmit.setOnClickListener {
            checkGender()
            val user = RegisterObject(
                binding.EditTextFirstName.text.toString(),
                binding.EditTextMiddleName.text.toString(),
                binding.EditTextSurname.text.toString(),
                binding.EditTextDoB.text.toString(),
                binding.EditTextEmail.text.toString(),
                binding.EditDescription.text.toString(),
                binding.EditTextPassword.text.toString(),
                checkGender(),
                checkGenderPreference()
            )

            viewModel.register(user).observe(this) { successful ->
                if (successful) {
                    Toast.makeText(context, "Successfully registered", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                } else {
                    Toast.makeText(context, "Invalid credentials", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return binding.root
    }

    fun checkGender(): String {
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

    fun checkGenderPreference(): String {
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

}