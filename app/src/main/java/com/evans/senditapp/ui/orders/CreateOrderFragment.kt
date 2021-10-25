package com.evans.senditapp.ui.orders

import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.*
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
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.confirmbottomsheet.*
import kotlinx.android.synthetic.main.fragment_create_order.*
import kotlinx.android.synthetic.main.order_layout.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.time.LocalDateTime.now

class CreateOrderFragment : BaseFragment<AuthViewModel, FragmentCreateOrderBinding, AuthRepository>() {

    private lateinit var preferencesProvider: PreferencesProvider
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    lateinit var radioButton: RadioButton
//    lateinit var medium: RadioButton
//    lateinit var heavy: RadioButton
//
//    lateinit var bike: RadioButton
//    lateinit var motorbike: RadioButton
//    lateinit var van: RadioButton
//    lateinit var truck: RadioButton

    var radioGroup: RadioGroup? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet)

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



        binding.BtnOrder.setOnClickListener {

            if (binding.descriptionInput.text.toString().isEmpty()) {
                descriptionTextField.error = "Field is required"
                return@setOnClickListener
            }
            else if (binding.locationInput.text.toString().isEmpty()) {
                locationTextField.error = "Field is required"
                return@setOnClickListener
            }
            else if (binding.destinationInput.text.toString().isEmpty()) {
                destinationTextField.error = "Field is required"
            }
            else if (binding.receiverNameInput.text.toString().isEmpty()) {
                receiverTextField.error = "Field is required!"
            }
            else if (binding.receiverNumberInput.text.toString().isEmpty()) {
                recivernumberTextField.error = "Field is required!"
            }
            else {
                if (bottomSheetBehavior.state==BottomSheetBehavior.STATE_EXPANDED)
                {
                    bottomSheetBehavior.state=BottomSheetBehavior.STATE_COLLAPSED
                } else {
                    bottomSheetBehavior.state=BottomSheetBehavior.STATE_EXPANDED
                    buttonConfirm.setOnClickListener {
                        CoroutineScope(Dispatchers.IO).launch {
                            postOrders()
                        }
                    }
                }
            }

        }
        buttonCancel.setOnClickListener {
            bottomSheetBehavior.state=BottomSheetBehavior.STATE_COLLAPSED
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

//        var heavy: String = binding.heavy.toString()
//        var date = LocalDate.parse("2021-10-25")
        var date = LocalDate.parse(now().toLocalDate().toString())
        var description: String = binding.descriptionInput.text.toString()
        var pickup_location: String = binding.locationInput.text.toString()
        var destination: String = binding.destinationInput.text.toString()
        var reciever_name: String = binding.receiverNameInput.text.toString()
        var reciever_number: String = binding.receiverNameInput.text.toString()
        val weight = "light"


        val retrofitData = retrofitBuilder.postOrders("Bearer " + preferencesProvider.getString("access"), AuthApi.Order(
            weight = "light",
            vehicle = "van",
            date = date.toString(),
            pickup_location = locationInput.text.toString(),
            destination = destinationInput.text.toString(),
            description = descriptionInput.text.toString(),
            reciever_name = receiverNameInput.text.toString(),
            reciever_number = receiverNumberInput.text.toString()
        ))

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