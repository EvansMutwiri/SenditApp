package com.evans.senditapp.ui.onboarding.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.evans.senditapp.R
import kotlinx.android.synthetic.main.fragment_third_screen.view.*

/**
 * A simple [Fragment] subclass.
 * Use the [ThirdScreen.newInstance] factory method to
 * create an instance of this fragment.
 */
class ThirdScreen : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_third_screen, container, false)

        rootView.startBtn.setOnClickListener {
            findNavController().navigate(R.id.action_viewPagerFragment_to_registrationFragment)
        }

        return rootView
    }
}