package com.daferarevalo.espapp.ui.home

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

        showHumidity()
        verificarEstadoRele1()
        verificarEstadoRele2()
        verificarEstadoRele3()
        verificarEstadoRiego()

        binding.rele1Button.setOnClickListener {
            encenderApagarRele1(estadoRele1)
            desactivarT1Firebase()
        }

        binding.rele2Button.setOnClickListener {
            encenderApagarRele2(estadoRele2)
            desactivarT2Firebase()
        }

        binding.rele3Button.setOnClickListener {
            encenderApagarRele3(estadoRele3)
            desactivarT3Firebase()
        }

        binding.riegoButton.setOnClickListener {
            encenderApagarRele4(estadoRele4)
            desactivarT4Firebase()
        }

        val navController = Navigation.findNavController(view)
        binding.temperatureCardView.setOnClickListener {
            navController.navigate(R.id.temperatureFragment)
        }
    }

    private fun verificarEstadoRiego() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val uidUser = user.uid
            val database = FirebaseDatabase.getInstance()
            val myDispRef = database.getReference("usuarios")
                .child(uidUser).child("rele4").child("estado")

            val postListener = object : ValueEventListener {
                @SuppressLint("ResourceAsColor")
                override fun onDataChange(snapshot: DataSnapshot) {
                    estadoRele4 = snapshot.value as Boolean
                    if (estadoRele4) {
                        binding.riegoButton.setBackgroundColor(Color.GREEN)
                    } else {
                        binding.riegoButton.setBackgroundColor(Color.RED)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(context, "Error en la conección", Toast.LENGTH_SHORT).show()
                }

            }
            myDispRef.addValueEventListener(postListener)
        }
    }

    private fun encenderApagarRele4(estadoRele4: Boolean) {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val uidUser = user.uid
            val database = FirebaseDatabase.getInstance()
            val myDispRef = database.getReference("usuarios").child(uidUser).child("rele4")

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
            val myDispRef = database.getReference("usuarios").child(uidUser).child("rele4")

            myDispRef.child("activar").setValue(false)
        }
    }

    private fun verificarEstadoRele3() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val uidUser = user.uid
            val database = FirebaseDatabase.getInstance()
            val myDispRef = database.getReference("usuarios")
                .child(uidUser).child("rele3").child("estado")

            val postListener = object : ValueEventListener {
                @SuppressLint("ResourceAsColor")
                override fun onDataChange(snapshot: DataSnapshot) {
                    estadoRele3 = snapshot.value as Boolean
                    if (estadoRele3) {
                        binding.rele3Button.setBackgroundColor(Color.GREEN)
                    } else {
                        binding.rele3Button.setBackgroundColor(Color.RED)
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            }
            myDispRef.addValueEventListener(postListener)
        }
    }

    private fun encenderApagarRele3(estadoRele3: Boolean) {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val uidUser = user.uid
            val database = FirebaseDatabase.getInstance()
            val myDispRef = database.getReference("usuarios").child(uidUser).child("rele3")

            val estadoRele3Aux = estadoRele3.not()

            val childUpdates = HashMap<String, Any>()
            childUpdates["estado"] = estadoRele3Aux
            myDispRef.updateChildren(childUpdates)

        }
    }

    private fun desactivarT3Firebase() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val uidUser = user.uid
            val database = FirebaseDatabase.getInstance()
            val myDispRef = database.getReference("usuarios").child(uidUser).child("rele3")

            myDispRef.child("activar").setValue(false)
        }
    }

    private fun verificarEstadoRele2() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val uidUser = user.uid
            val database = FirebaseDatabase.getInstance()
            val myDispRef = database.getReference("usuarios")
                .child(uidUser).child("rele2").child("estado")

            val postListener = object : ValueEventListener {
                @SuppressLint("ResourceAsColor")
                override fun onDataChange(snapshot: DataSnapshot) {
                    estadoRele2 = snapshot.value as Boolean
                    if (estadoRele2) {
                        binding.rele2Button.setBackgroundColor(Color.GREEN)
                    } else {
                        binding.rele2Button.setBackgroundColor(Color.RED)
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            }
            myDispRef.addValueEventListener(postListener)
        }
    }

    private fun encenderApagarRele2(estadoRele2: Boolean) {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val uidUser = user.uid
            val database = FirebaseDatabase.getInstance()
            val myDispRef = database.getReference("usuarios").child(uidUser).child("rele2")

            val estadoRele2Aux = estadoRele2.not()

            val childUpdates = HashMap<String, Any>()
            childUpdates["estado"] = estadoRele2Aux
            myDispRef.updateChildren(childUpdates)

        }
    }

    private fun desactivarT2Firebase() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val uidUser = user.uid
            val database = FirebaseDatabase.getInstance()
            val myDispRef = database.getReference("usuarios").child(uidUser).child("rele2")

            myDispRef.child("activar").setValue(false)
        }
    }

    private fun encenderApagarRele1(estadoRele1: Boolean) {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val uidUser = user.uid
            val database = FirebaseDatabase.getInstance()
            val myDispRef = database.getReference("usuarios").child(uidUser).child("rele1")

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
            val myDispRef = database.getReference("usuarios").child(uidUser).child("rele1")

            myDispRef.child("activar").setValue(false)

        }
    }

    private fun verificarEstadoRele1() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val uidUser = user.uid
            val database = FirebaseDatabase.getInstance()
            val myDispRef = database.getReference("usuarios")
                .child(uidUser).child("rele1").child("estado")

            val postListener = object : ValueEventListener {
                @SuppressLint("ResourceAsColor")
                override fun onDataChange(snapshot: DataSnapshot) {
                    estadoRele1 = snapshot.value as Boolean
                    if (estadoRele1 == false) {
                        binding.rele1Button.setBackgroundColor(Color.RED)
                    } else {
                        binding.rele1Button.setBackgroundColor(Color.GREEN)
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
                    val humS = dataSnapshot.child("HumedadTierra").value
                    binding.temperatureTextView.text = "$temp°"
                    binding.humidityTextView.text = "$hum%"
                    binding.humiditySueloTextView.text = "$humS%"
                    val ilumin = dataSnapshot.child("fotoCelda").value
                    if (ilumin == true)
                        binding.iluminacionTextView.text = "Baja iluminación"
                    else
                        binding.iluminacionTextView.text = "Buena iluminación"

                    //binding.humidityTextView.text = getString(R.string.humidity_format, hum)
                }

                override fun onCancelled(error: DatabaseError) {
                }

            }

            myDatosRef.addValueEventListener(postListener)
        }
    }
}


