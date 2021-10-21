package com.evans.senditapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.evans.senditapp.Constants.KEY_EMAIL
import com.evans.senditapp.Constants.USERNAME
import com.evans.senditapp.PreferencesProvider
import com.evans.senditapp.R
import com.evans.senditapp.data.network.AuthApi
import com.evans.senditapp.data.repository.AuthRepository
import com.evans.senditapp.databinding.FragmentUserProfileBinding
import com.evans.senditapp.ui.auth.AuthViewModel
import com.evans.senditapp.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_user_profile.*

class UserProfileFragment : BaseFragment<AuthViewModel, FragmentUserProfileBinding, AuthRepository>() {

    private lateinit var preferencesProvider: PreferencesProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        preferencesProvider = PreferencesProvider(requireContext())
        val email = preferencesProvider.getString("useremail")



        customeremail.setText(email)
    }

    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentUserProfileBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() = AuthRepository(remoteDataSource.buidApi(AuthApi::class.java))
}