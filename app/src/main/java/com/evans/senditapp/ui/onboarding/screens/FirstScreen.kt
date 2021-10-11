package com.evans.senditapp.ui.onboarding.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.evans.senditapp.R
import kotlinx.android.synthetic.main.fragment_first_screen.view.*
import kotlinx.android.synthetic.main.fragment_view_pager.*

/**
 * A simple [Fragment] subclass.
 * Use the [FirstScreen.newInstance] factory method to
 * create an instance of this fragment.
 */
class FirstScreen : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_first_screen, container, false)

        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)

        rootView.next1.setOnClickListener {
            if (viewPager != null) {
                viewPager.currentItem=1
            }
        }

        return rootView
    }
}