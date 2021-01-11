package com.daferarevalo.espapp.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.daferarevalo.espapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        showHumidity()

        binding.timeEditText.setOnClickListener {
            showTimePickerDialog()
        }

        binding.encenderButton.setOnClickListener {
            val estado = 1
            encenderLedFirebase(estado)
            Toast.makeText(this, "Led encendido", Toast.LENGTH_SHORT).show()
        }

        binding.apagarButton.setOnClickListener {
            val estado = 0
            encenderLedFirebase(estado)
            Toast.makeText(this, "Led apagado", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showTimePickerDialog() {
        val timePicker = TimePickerFragment { onTimeSelected(it) }
        timePicker.show(supportFragmentManager, "time")
    }

    private fun onTimeSelected(time: String) {
        binding.timeEditText.setText("$time")
    }

    private fun showHumidity() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val uidUser = user.uid
            val database = FirebaseDatabase.getInstance()
            val myDatosRef = database.getReference("usuarios").child(uidUser).child("Sensores").child("Humedad")
            val postListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val hum = snapshot.getValue()
                    binding.humedadTextView.text = hum.toString()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            }

            myDatosRef.addValueEventListener(postListener)
        }
    }

    private fun encenderLedFirebase(estado: Int) {
        val database = FirebaseDatabase.getInstance()
        val myLedRef = database.getReference("leds").child("led1")

        myLedRef.child("led").setValue(estado)

    }

}