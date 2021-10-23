package com.evans.senditapp.ui.orders

import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import com.evans.senditapp.PreferencesProvider
import com.evans.senditapp.R
import com.evans.senditapp.data.network.AuthApi
import com.evans.senditapp.data.network.Resource
import com.evans.senditapp.data.repository.AuthRepository
import com.evans.senditapp.databinding.FragmentCreateOrderBinding
import com.evans.senditapp.ui.auth.AuthViewModel
import com.evans.senditapp.ui.base.BaseFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate

class CreateOrderFragment : BaseFragment<AuthViewModel, FragmentCreateOrderBinding, AuthRepository>() {

    private lateinit var preferencesProvider: PreferencesProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
    @RequiresApi(Build.VERSION_CODES.O)
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
//            val pick_up: String = binding.locationInput.text.toString()

            CoroutineScope(Dispatchers.IO).launch {
                postOrders()
            }

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun postOrders() {
        val okhttpHttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpClient = OkHttpClient.Builder().addInterceptor(
            okhttpHttpLoggingInterceptor
        )

        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient.build())
            .build()
            .create(AuthApi::class.java)

        var date = LocalDate.parse("2018-12-12")

        val retrofitData = retrofitBuilder.postOrders("Bearer " + preferencesProvider.getString("access"), AuthApi.Order("bale", "moshogi", "ungwaro", "wanyee", "kinuts", "12345678", "heavy", date.toString()))

    }

    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentCreateOrderBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() = AuthRepository(remoteDataSource.buidApi(AuthApi::class.java))

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.nav_drawer_menu, menu)
    }
}