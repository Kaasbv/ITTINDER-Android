package com.ittinder.ittinder.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.ViewModelProvider
import com.ittinder.ittinder.databinding.FragmentLoginBinding
import com.ittinder.ittinder.viewmodel.LoginViewModel

class LoginFragment : Fragment() {
    private lateinit var loginViewModel: LoginViewModel
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val emailEditText = binding.emailAddress
        val passwordEditText = binding.password
        val loginButton = binding.login

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}