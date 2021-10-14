package com.evans.senditapp.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.evans.senditapp.R
import com.evans.senditapp.data.network.AuthApi
import com.evans.senditapp.data.network.Resource
import com.evans.senditapp.data.repository.AuthRepository
import com.evans.senditapp.databinding.FragmentRegistrationBinding
import com.evans.senditapp.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_registration.*

//class RegistrationFragment : Fragment() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        val rootView = inflater.inflate(R.layout.fragment_registration, container, false)
//
//        val loginOption = rootView.findViewById<TextView>(R.id.login_option)
//
//        loginOption.setOnClickListener {
//            findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
//        }
//
//        val btnSignUp = rootView.findViewById<Button>(R.id.signUpBtn)
//        btnSignUp.setOnClickListener {
//            findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
//        }
//        return rootView
//    }
//}


class RegistrationFragment : BaseFragment<AuthViewModel, FragmentRegistrationBinding, AuthRepository>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.signupResponse.observe(viewLifecycleOwner, Observer {
            when(it) {
                is Resource.Success -> {
                    Toast.makeText(requireContext(), "login successfull", Toast.LENGTH_LONG).show()

                    findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
                }
                is Resource.Failure -> {
                    if (it.isNetworkError)
                        Toast.makeText(requireContext(), "Check your internet connection", Toast.LENGTH_SHORT).show()
                    else
                        Toast.makeText(requireContext(), "ERROR", Toast.LENGTH_LONG).show()
                }
            }
        })

        binding.signUpBtn.setOnClickListener {
            val username: String = binding.UsernameTV.text.toString().trim()
            val email: String = binding.SignUpEmail.text.toString().trim()
            val password: String = binding.PasswordSignup.text.toString()
            val passConfirm: String = binding.PasswordSignupConfirm.text.toString()

            // add validation

            if (username.isEmpty() || email.isBlank()) {
                Toast.makeText(requireContext(), "Fields cannot be empty", Toast.LENGTH_SHORT).show()
            }
            if (!email.contains("@"))
                Toast.makeText(requireContext(), "invalid email address", Toast.LENGTH_SHORT).show()
            if (password != passConfirm)
                Toast.makeText(requireContext(), "passwords don't match", Toast.LENGTH_SHORT).show()
            if (password.length < 6)
                Toast.makeText(requireContext(), "password length must be > 6", Toast.LENGTH_SHORT).show()
            else
            viewModel.signUp(username, email, password)

        }

        binding.loginOption.setOnClickListener {
            findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
        }
    }

    override fun getViewModel() =AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegistrationBinding = FragmentRegistrationBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() = AuthRepository(remoteDataSource.buidApi(AuthApi::class.java))
}