package com.evans.senditapp.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.evans.senditapp.Constants
import com.evans.senditapp.PreferencesProvider
import com.evans.senditapp.R
import com.evans.senditapp.data.network.AuthApi
import com.evans.senditapp.data.network.Resource
import com.evans.senditapp.data.repository.AuthRepository
import com.evans.senditapp.databinding.FragmentRegistrationBinding
import com.evans.senditapp.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_registration.*


class RegistrationFragment : BaseFragment<AuthViewModel, FragmentRegistrationBinding, AuthRepository>() {

    private lateinit var preferencesProvider: PreferencesProvider

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        preferencesProvider = PreferencesProvider(requireContext())

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

            preferencesProvider.putString(Constants.KEY_EMAIL, SignUpEmail.text.toString())
            preferencesProvider.putString(Constants.USERNAME, UsernameTV.text.toString())

            // add validation

            if (username.isEmpty() || email.isEmpty()) {
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