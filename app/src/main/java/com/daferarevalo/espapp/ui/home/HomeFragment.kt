package com.daferarevalo.espapp.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.daferarevalo.espapp.R
import com.daferarevalo.espapp.core.Result
import com.daferarevalo.espapp.data.remote.home.HomeDataSource
import com.daferarevalo.espapp.databinding.FragmentHomeBinding
import com.daferarevalo.espapp.domain.home.HomeRepoImpl
import com.daferarevalo.espapp.presentation.home.HomeViewModel
import com.daferarevalo.espapp.presentation.home.HomeViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<HomeViewModel> {
        HomeViewModelFactory(
            HomeRepoImpl(
                HomeDataSource()
            )
        )
    }

    private var estadoRele1 = false
    private var estadoRele2 = false
    private var estadoRele3 = false
    private var estadoRele4 = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        //showHumidity()

        viewModel.readSensorModel(1).observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Loading -> {

                }
                is Result.Success -> {
                    binding.temperatureTextView.text = result.data.toString()
                }
                is Result.Failure -> {
                    binding.temperatureTextView.text = "null"
                    Toast.makeText(
                        context,
                        "Error: ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })

        viewModel.readSensorModel(2).observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Loading -> {

                }
                is Result.Success -> {
                    binding.humiditySueloTextView.text = result.data.toString()
                }
                is Result.Failure -> {
                    binding.humiditySueloTextView.text = "null"
                    Toast.makeText(
                        context,
                        "Error: ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })

        viewModel.readSensorModel(3).observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Loading -> {

                }
                is Result.Success -> {
                    binding.humidityTextView.text = result.data.toString()
                }

                is Result.Failure -> {
                    binding.humidityTextView.text = "null"
                    Toast.makeText(
                        context,
                        "Error: ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })

        binding.rele1Button.setOnClickListener {
            encenderApagarRele1(estadoRele1)
            desactivarT1Firebase()
        }

        binding.channelsCardView.setOnClickListener {

            findNavController().navigate(R.id.action_navigation_home_to_channelsFragment)
        }

        binding.temperatureCardView.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_temperatureFragment)
        }

    }

    private fun encenderApagarRele4(estadoRele4: Boolean) {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val uidUser = user.uid
            val database = FirebaseDatabase.getInstance()
            val myDispRef = database.getReference("users").child(uidUser).child("channels/channel4")

            val estadoRele4Aux = estadoRele4.not()

            val childUpdates = HashMap<String, Any>()
            childUpdates["estado"] = estadoRele4Aux
            myDispRef.updateChildren(childUpdates)

        }
    }

    private fun desactivarT4Firebase() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val uidUser = user.uid
            val database = FirebaseDatabase.getInstance()
            val myDispRef = database.getReference("users").child(uidUser).child("channels/channel4")

            myDispRef.child("activar").setValue(false)
        }
    }

    private fun encenderApagarRele1(estadoRele1: Boolean) {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val uidUser = user.uid
            val database = FirebaseDatabase.getInstance()
            val myDispRef = database.getReference("users").child(uidUser).child("channels/channel1")

            val estadoRele1Aux = estadoRele1.not()

            val childUpdates = HashMap<String, Any>()
            childUpdates["estado"] = estadoRele1Aux
            myDispRef.updateChildren(childUpdates)

        }
    }

    private fun desactivarT1Firebase() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val uidUser = user.uid
            val database = FirebaseDatabase.getInstance()
            val myDispRef = database.getReference("users").child(uidUser).child("channels/channel1")

            myDispRef.child("activar").setValue(false)

        }
    }

    private fun showHumidity() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val uidUser = user.uid
            val database = FirebaseDatabase.getInstance()
            val myDatosRef = database.getReference("usuarios").child(uidUser).child("Sensores")

            val postListener = object : ValueEventListener {
                @SuppressLint("SetTextI18n")
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    //val sensorServer = dataSnapshot.getValue(SensorServer::class.java)
                    val hum = dataSnapshot.child("Humedad").value
                    val temp = dataSnapshot.child("Temperatura").value
                    val humS = dataSnapshot.child("HumedadTierra").value
                    binding.temperatureTextView.text = "$temp°"
                    binding.humidityTextView.text = "$hum%"
                    binding.humiditySueloTextView.text = "$humS%"
                    /*
                    val ilumin = dataSnapshot.child("fotoCelda").value
                    if (ilumin == true)
                        binding.iluminacionTextView.text = "Baja iluminación"
                    else
                        binding.iluminacionTextView.text = "Buena iluminación"

                     */

                    //binding.humidityTextView.text = getString(R.string.humidity_format, hum)
                }

                override fun onCancelled(error: DatabaseError) {
                }

            }

            myDatosRef.addValueEventListener(postListener)
        }
    }
}


