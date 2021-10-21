package com.evans.senditapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import com.evans.senditapp.PreferencesProvider
import com.evans.senditapp.R
import com.evans.senditapp.data.network.AuthApi
import com.evans.senditapp.data.network.Resource
import com.evans.senditapp.data.repository.AuthRepository
import com.evans.senditapp.databinding.FragmentCreateOrderBinding
import com.evans.senditapp.ui.auth.AuthViewModel
import com.evans.senditapp.ui.base.BaseFragment

class CreateOrderFragment : BaseFragment<AuthViewModel, FragmentCreateOrderBinding, AuthRepository>() {

    private lateinit var preferencesProvider: PreferencesProvider

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        preferencesProvider = PreferencesProvider(requireContext())

        viewModel.orderResponse.observe(viewLifecycleOwner, Observer {
            when(it) {
                is Resource.Success -> {
                    Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
                }

                is Resource.Failure -> {
                    if (it.isNetworkError)
                        Toast.makeText(requireContext(), "Check your internet connection", Toast.LENGTH_SHORT).show()
                    else
                        Toast.makeText(requireContext(), "Login Failed", Toast.LENGTH_SHORT).show()
                }
            }
        })
//        val vehicle = listOf("Van", "Bike", "truck")
//        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_vehicles, vehicle)
//        (vehicleInput.editableText as? AutoCompleteTextView)?.setAdapter(adapter)

        val weight = resources.getStringArray(R.array.weight_categories)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_weights, R.id.tV, weight)
        binding.weightInput.setAdapter(arrayAdapter)

//        val vehicle = resources.getStringArray(R.array.vehicle_categories)
//        val arrAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_vehicles, vehicle)
//        binding.vehicleInput.setAdapter(arrAdapter)


        binding.BtnOrder.setOnClickListener {
            val pick_up: String = binding.locationInput.text.toString()

            viewModel.postOrders(description = "bale", vehicle = "moshogi", destination = "ungwaro", pickup_location = "wanyee", reciever_name = "kinuts", reciever_number = "12345678", weight = "heavy")
        }
    }

    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentCreateOrderBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() = AuthRepository(remoteDataSource.buidApi(AuthApi::class.java))
}