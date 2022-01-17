package com.ittinder.ittinder.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ittinder.ittinder.R
import com.ittinder.ittinder.data.RegisterObject
import com.ittinder.ittinder.data.User
import com.ittinder.ittinder.databinding.FragmentLoginBinding
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
            Log.i("JEM", "Jeeeemoeeeeder")
            val user = RegisterObject(
                binding.EditTextFirstName.text.toString(),
                binding.EditTextMiddleName.text.toString(),
                binding.EditTextSurname.text.toString(),
                binding.EditTextDoB.text.toString(),
                binding.EditTextEmail.text.toString(),
                binding.EditDescription.text.toString(),
                binding.EditTextPassword.text.toString()
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

}