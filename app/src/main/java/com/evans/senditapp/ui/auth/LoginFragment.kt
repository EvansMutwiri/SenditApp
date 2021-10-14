package com.evans.senditapp.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import com.evans.senditapp.R
import com.evans.senditapp.databinding.FragmentLoginBinding
import com.evans.senditapp.data.network.AuthApi
import com.evans.senditapp.data.network.Resource
import com.evans.senditapp.data.repository.AuthRepository
import com.evans.senditapp.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseFragment<AuthViewModel, FragmentLoginBinding, AuthRepository>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)

        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success -> {
                    Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_LONG).show()
                }
                is Resource.Failure -> {
                    if (it.isNetworkError)
                        Toast.makeText(requireContext(), "Check your internet connection", Toast.LENGTH_SHORT).show()
                    else
                        Toast.makeText(requireContext(), "Login Failed", Toast.LENGTH_SHORT).show()
                }
            }
        })
        binding.containedButton.setOnClickListener {

            val email: String = binding.editEmail.text.toString().trim()
            val password = binding.editPassword.text.toString()
            val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

            //validation
            if (email.isEmpty() || email.isBlank() || password.isBlank()){
                Toast.makeText(requireContext(), "Email address or password cannot be blank", Toast.LENGTH_LONG).show()
            }
            if (!email.matches(emailPattern.toRegex())){
                Toast.makeText(requireContext(), "Enter valid email address", Toast.LENGTH_LONG).show()
            }
            if (password.length < 6){
                Toast.makeText(requireContext(), "Password must be more than 6 characters", Toast.LENGTH_LONG).show()
            }
            else {
                viewModel.login(email, password)
            }
        }

        binding.signupOption.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
        }
    }
    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLoginBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() = AuthRepository(remoteDataSource.buidApi(AuthApi::class.java))
}