package com.daferarevalo.espapp.ui.home

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.daferarevalo.espapp.R
import com.daferarevalo.espapp.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private var estadoLuces = false

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

        showHumidity()
        verificarEstadoLuces()
        binding.lucesButton.setOnClickListener {
            encenderApagarLuces(estadoLuces)
            activarT1Firebase()
        }

        val navController = Navigation.findNavController(view)

        binding.temperatureCardView.setOnClickListener {
            navController.navigate(R.id.temperatureFragment)
        }
    }

    private fun encenderApagarLuces(estadoLuces: Boolean) {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val uidUser = user.uid
            val database = FirebaseDatabase.getInstance()
            val myDispRef = database.getReference("usuarios").child(uidUser).child("rele1")

            val estadoLucesAux = estadoLuces.not()

            val childUpdates = HashMap<String, Any>()
            childUpdates["estado"] = estadoLucesAux
            myDispRef.updateChildren(childUpdates)

        }
    }

    private fun activarT1Firebase() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val uidUser = user.uid
            val database = FirebaseDatabase.getInstance()
            val myDispRef = database.getReference("usuarios").child(uidUser).child("rele1")

            myDispRef.child("activar").setValue(false)


        }
    }

    private fun verificarEstadoLuces() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val uidUser = user.uid
            val database = FirebaseDatabase.getInstance()
            val myDispRef = database.getReference("usuarios")
                    .child(uidUser).child("rele1").child("estado")

            val postListener = object : ValueEventListener {
                @SuppressLint("ResourceAsColor")
                override fun onDataChange(snapshot: DataSnapshot) {
                    estadoLuces = snapshot.value as Boolean
                    if (estadoLuces == false) {
                        binding.lucesButton.setBackgroundColor(Color.RED)
                    } else {
                        binding.lucesButton.setBackgroundColor(Color.GREEN)
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            }
            myDispRef.addValueEventListener(postListener)
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
                    binding.humidityTextView.text = hum.toString() + "%"
                    binding.temperatureTextView.text = temp.toString() + "°"
                    //binding.humidityTextView.text = getString(R.string.humidity_format, hum)
                }

                override fun onCancelled(error: DatabaseError) {
                }

            }

            myDatosRef.addValueEventListener(postListener)
        }
    }
}


