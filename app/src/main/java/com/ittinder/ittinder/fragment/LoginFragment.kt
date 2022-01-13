package com.ittinder.ittinder.fragment


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.ittinder.ittinder.databinding.FragmentLoginBinding
import com.ittinder.ittinder.viewmodel.UserViewModel

class LoginFragment : Fragment() {
    private lateinit var userViewModel: UserViewModel
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    //set LiveData listeners
    private val emailLiveData = MutableLiveData<String>()
    private val passwordLiveData = MutableLiveData<String>()
    private val isValidLiveData = MediatorLiveData<Boolean>().apply {
        this.value = false

        //listen to email
        addSource(emailLiveData) { email ->
            val password = passwordLiveData.value
            this.value = validateForm(email, password)
        }
        //listen to password
        addSource(passwordLiveData) { password ->
            val email = emailLiveData.value
            this.value = validateForm(email, password)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

//        val viewModel: UserViewModel by viewModels();
//        viewModel.user
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val emailEditText = binding.emailAddress.editText
        val passwordEditText = binding.password.editText
        val loginButton = binding.login

        emailEditText?.doOnTextChanged { text, _, _, _ ->
            emailLiveData.value = text?.toString()
        }

        passwordEditText?.doOnTextChanged { text, _, _, _ ->
            passwordLiveData.value = text?.toString()
        }

        isValidLiveData.observe(this.viewLifecycleOwner) { isValid ->
            loginButton.isEnabled = isValid
        }

        loginButton.setOnClickListener {
            //TODO: login submitten
            Toast.makeText(context, "You clicked me.", Toast.LENGTH_SHORT).show()

           userViewModel.login(
                emailEditText?.text.toString(),
                passwordEditText?.text.toString()
            )
        }

    }

    //Form validator
    private fun validateForm(email: String?, password: String?): Boolean {
        val isValidEmail = email != null && email.isNotBlank() && email.contains("@")
        val isValidPassword = password != null && password.isNotBlank() && password.length >= 6
        return isValidEmail && isValidPassword
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}