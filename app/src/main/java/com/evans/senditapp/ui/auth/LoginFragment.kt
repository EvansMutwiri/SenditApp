package com.evans.senditapp.ui.auth

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.evans.senditapp.PreferencesProvider
import com.evans.senditapp.R
import com.evans.senditapp.databinding.FragmentLoginBinding
import com.evans.senditapp.data.network.AuthApi
import com.evans.senditapp.data.network.Resource
import com.evans.senditapp.data.repository.AuthRepository
import com.evans.senditapp.ui.base.BaseFragment
import com.evans.senditapp.ui.home.HomeActivity
import com.evans.senditapp.ui.orders.Order
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseFragment<AuthViewModel, FragmentLoginBinding, AuthRepository>() {

    private lateinit var progressBar: LottieAnimationView

    private lateinit var preferencesProvider: PreferencesProvider

//    private lateinit var editEmail: TextInputEditText
//    private lateinit var editPassword: EditText
//
//    init {
//        editEmail.also { this.editEmail = it }
//        editPassword.also { this.editPassword = it }
//
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)
        preferencesProvider = PreferencesProvider(requireContext())

        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success -> {
//                    Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()

                    PreferenceManager.getDefaultSharedPreferences(activity).edit().putString("access",
                        it.value.access).apply()
                    var tok = PreferenceManager.getDefaultSharedPreferences(activity).getString("access", it.value.access)
//                    Toast.makeText(requireContext(), tok, Toast.LENGTH_SHORT).show()
                    preferencesProvider.putString("access", it.value.access)

                    val email: String = binding.editEmail.text.toString().trim()
                    preferencesProvider.putString("useremail", email)

                    Toast.makeText(requireContext(), email, Toast.LENGTH_SHORT).show()

                        val intent = Intent(requireContext(), Order::class.java)
                        startActivity(intent)
//                    findNavController().navigate(R.id.action_registrationFragment_to_userProfileFragment2)

//                    if (findNavController().currentDestination?.id == R.id.registrationFragment) {
//                        findNavController().navigate(R.id.action_registrationFragment_to_mapsActivity)
//                    }
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

            val editEmail = binding.editEmail
            val editPassword = binding.editPassword

            //validation
            if (email.isEmpty()){
                editEmail.error = "Cannot be blank"
                return@setOnClickListener
            }
            else if (!email.contains("@")){
                editEmail.error = "Enter valid email address"
                return@setOnClickListener
            }
            else if (password.length < 6){
                editPassword.error = "Password too weak"
                return@setOnClickListener
            }
            else if (password.isEmpty()){
                editPassword.error = "Password required"
                return@setOnClickListener
            }
            else {
                progressBar = binding.progressBar
                progressBar.visibility  = View.VISIBLE
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