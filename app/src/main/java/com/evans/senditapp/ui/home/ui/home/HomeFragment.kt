package com.evans.senditapp.ui.home.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.evans.senditapp.databinding.FragmentHomeBinding
import android.content.Intent
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.evans.senditapp.R
import com.evans.senditapp.ui.home.HomeActivity
import com.evans.senditapp.ui.home.MapsActivity
import com.evans.senditapp.ui.orders.Order


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val heroImge: ImageView = binding.heroimage
        val newOrder: ImageView = binding.homeadd
        val map: ImageView = binding.hometrack
        val orders: ImageView = binding.homehistory
        heroImge.setOnClickListener {
            AnimationUtils.loadAnimation(requireContext(), R.anim.hero)
        }

        newOrder.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_createOrderFragment)
        }
        map.setOnClickListener {
//            val intent = Intent(requireContext(), MapsActivity::class.java)
//            startActivity(intent)
            Toast.makeText(requireContext(), "Track package", Toast.LENGTH_SHORT).show()
        }
        orders.setOnClickListener {
            val intent = Intent(requireContext(), Order::class.java)
            startActivity(intent)
        }



//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}