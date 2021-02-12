package com.daferarevalo.espapp.ui.settings

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.daferarevalo.espapp.R
import com.daferarevalo.espapp.databinding.FragmentSettingsBinding
import com.daferarevalo.espapp.server.RiegoServer
import com.daferarevalo.espapp.server.Temporizador1Server
import com.daferarevalo.espapp.server.Temporizador2Server
import com.daferarevalo.espapp.server.Temporizador3Server
import com.daferarevalo.espapp.ui.timePicker.TimePickerFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener as ValueEventListener2

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingsBinding.bind(view)

        verificarEstadoTemporizador1()
        verificarEstadoTemporizador2()
        verificarEstadoTemporizador3()
        verificarEstadoRiego()

        binding.encendidoT1EditText.setOnClickListener {
            showTimePickerDialogOnT1()
        }

        binding.apagadoT1EditText.setOnClickListener {
            showTimePickerDialogOffT1()
        }

        binding.t1Switch.setOnClickListener {
            if (binding.t1Switch.isChecked) {
                val activar = true
                activarT1Firebase(activar)
            } else {
                val activar = false
                activarT1Firebase(activar)
            }
        }

        binding.encendidoT2EditText.setOnClickListener {
            showTimePickerDialogOnT2()
        }

        binding.apagadoT2EditText.setOnClickListener {
            showTimePickerDialogOffT2()
        }

        binding.t2Switch.setOnClickListener {
            if (binding.t2Switch.isChecked) {
                val activar = true
                activarT2Firebase(activar)
            } else {
                val activar = false
                activarT2Firebase(activar)
            }
        }

        binding.encendidoT3EditText.setOnClickListener {
            showTimePickerDialogOnT3()
        }

        binding.apagadoT3EditText.setOnClickListener {
            showTimePickerDialogOffT3()
        }

        binding.t3Switch.setOnClickListener {
            if (binding.t3Switch.isChecked) {
                val activar = true
                activarT3Firebase(activar)
            } else {
                val activar = false
                activarT3Firebase(activar)
            }
        }

        binding.encendidoRiegoEditText.setOnClickListener {
            showTimePickerDialogOnRiego()
        }
        binding.repeticionesRiegoEditText.setOnClickListener {
            val repeticiones = binding.repeticionesRiegoEditText.text.toString()
            repeticionesRiegoFirebase(repeticiones)
        }

        binding.tiempoRiegoEditText.setOnClickListener {
            val tiempo = binding.tiempoRiegoEditText.text.toString()
            tiempoRiegoFirebase(tiempo)
        }

        binding.tiempoEsperaTextView.setOnClickListener {
            val tiempo = binding.tiempoEsperaTextView.text.toString()
            tiempoEsperaFirebase(tiempo)
        }

        binding.riegoSwitch.setOnClickListener {
            if (binding.riegoSwitch.isChecked) {
                val activar = true
                activarRiegoFirebase(activar)
            } else {
                val activar = false
                activarRiegoFirebase(activar)
            }
        }

        binding.sundayCheckBox.setOnClickListener {
            val dia = 0
            if (binding.sundayCheckBox.isChecked) {
                val activar = true
                diasRiegoFirebase(dia, activar)
            } else {
                val activar = false
                diasRiegoFirebase(dia, activar)
            }
        }

        binding.mondayCheckBox.setOnClickListener {
            val dia = 1
            if (binding.mondayCheckBox.isChecked) {
                val activar = true

                diasRiegoFirebase(dia, activar)
            } else {
                val activar = false
                diasRiegoFirebase(dia, activar)
            }
        }

        binding.tuesdayCheckBox.setOnClickListener {
            val dia = 2
            if (binding.tuesdayCheckBox.isChecked) {
                val activar = true

                diasRiegoFirebase(dia, activar)
            } else {
                val activar = false
                diasRiegoFirebase(dia, activar)
            }
        }
        binding.wednesdayCheckBox.setOnClickListener {
            val dia = 3
            if (binding.wednesdayCheckBox.isChecked) {
                val activar = true

                diasRiegoFirebase(dia, activar)
            } else {
                val activar = false
                diasRiegoFirebase(dia, activar)
            }
        }
        binding.thursdayCheckBox.setOnClickListener {
            val dia = 4
            if (binding.thursdayCheckBox.isChecked) {
                val activar = true
                diasRiegoFirebase(dia, activar)
            } else {
                val activar = false
                diasRiegoFirebase(dia, activar)
            }
        }
        binding.fridayCheckBox.setOnClickListener {
            val dia = 5
            if (binding.fridayCheckBox.isChecked) {
                val activar = true

                diasRiegoFirebase(dia, activar)
            } else {
                val activar = false
                diasRiegoFirebase(dia, activar)
            }
        }
        binding.saturdayCheckBox.setOnClickListener {
            val dia = 6
            if (binding.saturdayCheckBox.isChecked) {
                val activar = true

                diasRiegoFirebase(dia, activar)
            } else {
                val activar = false
                diasRiegoFirebase(dia, activar)
            }
        }
    }

    private fun diasRiegoFirebase(dia: Int, activar: Boolean) {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val uidUser = user.uid
            val database = FirebaseDatabase.getInstance()

            when (dia) {
                0 -> {
                    val myDispRef = database.getReference("usuarios").child(uidUser)
                        .child("rele4")
                    val childUpdates = HashMap<String, Any>()
                    childUpdates["sun"] = activar
                    myDispRef.updateChildren(childUpdates)
                }

                1 -> {
                    val myDispRef = database.getReference("usuarios").child(uidUser)
                        .child("rele4")
                    val childUpdates = HashMap<String, Any>()
                    childUpdates["mon"] = activar
                    myDispRef.updateChildren(childUpdates)
                }

                2 -> {
                    val myDispRef = database.getReference("usuarios").child(uidUser)
                        .child("rele4")
                    val childUpdates = HashMap<String, Any>()
                    childUpdates["tues"] = activar
                    myDispRef.updateChildren(childUpdates)
                }
                3 -> {
                    val myDispRef = database.getReference("usuarios").child(uidUser)
                        .child("rele4")
                    val childUpdates = HashMap<String, Any>()
                    childUpdates["wed"] = activar
                    myDispRef.updateChildren(childUpdates)
                }
                4 -> {
                    val myDispRef = database.getReference("usuarios").child(uidUser)
                        .child("rele4")
                    val childUpdates = HashMap<String, Any>()
                    childUpdates["thurs"] = activar
                    myDispRef.updateChildren(childUpdates)
                }
                5 -> {
                    val myDispRef = database.getReference("usuarios").child(uidUser)
                        .child("rele4")
                    val childUpdates = HashMap<String, Any>()
                    childUpdates["fri"] = activar
                    myDispRef.updateChildren(childUpdates)
                }
                else -> {

                    val myDispRef = database.getReference("usuarios").child(uidUser)
                        .child("rele4")
                    val childUpdates = HashMap<String, Any>()
                    childUpdates["sat"] = activar
                    myDispRef.updateChildren(childUpdates)
                }
            }
        }

    }


    private fun tiempoEsperaFirebase(tiempo: String) {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val uidUser = user.uid
            val database = FirebaseDatabase.getInstance()
            val myDispRef = database.getReference("usuarios").child(uidUser).child("rele4")

            val childUpdates = HashMap<String, Any>()
            childUpdates["tiempoEspera"] = tiempo.toInt()
            myDispRef.updateChildren(childUpdates)
        }
    }

    private fun verificarEstadoRiego() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val uidUser = user.uid
            val database = FirebaseDatabase.getInstance()

            val myDispRef = database.getReference("usuarios").child(uidUser).child("rele4")

            val postListener = object : ValueEventListener2 {
                @SuppressLint("SetTextI18n")
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // Get Post object and use the values to update the UI
                    val riego = dataSnapshot.getValue(RiegoServer::class.java)
                    binding.riegoSwitch.isChecked = riego?.activar == true
                    binding.encendidoRiegoEditText.setText(riego?.h_on_rele4?.toString() + ":" + riego?.m_on_rele4?.toString())
                    binding.repeticionesRiegoEditText.setText(riego?.repeticiones.toString())
                    binding.tiempoEsperaTextView.setText(riego?.tiempoEspera.toString())
                    binding.tiempoRiegoEditText.setText(riego?.tiempoRiego.toString())
                    binding.sundayCheckBox.isChecked = riego?.sun == true
                    binding.mondayCheckBox.isChecked = riego?.mon == true
                    binding.tuesdayCheckBox.isChecked = riego?.tues == true
                    binding.fridayCheckBox.isChecked = riego?.fri == true
                    binding.wednesdayCheckBox.isChecked = riego?.wed == true
                    binding.thursdayCheckBox.isChecked = riego?.thurs == true
                    binding.saturdayCheckBox.isChecked = riego?.sat == true
                    // ...
                }

                override fun onCancelled(databaseError: DatabaseError) {

                }
            }
            myDispRef.addValueEventListener(postListener)

        }

    }

    private fun activarRiegoFirebase(activar: Boolean) {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val uidUser = user.uid
            val database = FirebaseDatabase.getInstance()
            val myDispRef = database.getReference("usuarios").child(uidUser).child("rele4")

            if (activar) {
                myDispRef.child("activar").setValue(true)
            } else {
                myDispRef.child("activar").setValue(false)
            }
        }
    }

    private fun tiempoRiegoFirebase(tiempo: String) {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val uidUser = user.uid
            val database = FirebaseDatabase.getInstance()
            val myDispRef = database.getReference("usuarios").child(uidUser).child("rele4")

            val childUpdates = HashMap<String, Any>()
            childUpdates["tiempoRiego"] = tiempo.toInt()
            myDispRef.updateChildren(childUpdates)
        }
    }

    private fun repeticionesRiegoFirebase(repeticiones: String) {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val uidUser = user.uid
            val database = FirebaseDatabase.getInstance()
            val myDispRef = database.getReference("usuarios").child(uidUser).child("rele4")

            val childUpdates = HashMap<String, Any>()
            childUpdates["repeticiones"] = repeticiones.toInt()
            myDispRef.updateChildren(childUpdates)
        }
    }

    private fun showTimePickerDialogOnRiego() {
        val timePicker = TimePickerFragment { hour, minute -> onTimeSelectedOnRiego(hour, minute) }
        fragmentManager?.let { timePicker.show(it, "time") }
    }

    @SuppressLint("SetTextI18n")
    private fun onTimeSelectedOnRiego(hour: Int, minute: Int) {
        binding.encendidoRiegoEditText.setText("$hour:$minute")
        horaOnRiegoFirebase(hour, minute)
    }

    private fun horaOnRiegoFirebase(hour: Int, minute: Int) {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val uidUser = user.uid
            val database = FirebaseDatabase.getInstance()
            val myDispRef = database.getReference("usuarios").child(uidUser).child("rele4")

            val childUpdates = HashMap<String, Any>()
            childUpdates["h_on_rele4"] = hour
            childUpdates["m_on_rele4"] = minute

            myDispRef.updateChildren(childUpdates)
        }
    }

    private fun activarT3Firebase(activar: Boolean) {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val uidUser = user.uid
            val database = FirebaseDatabase.getInstance()
            val myDispRef = database.getReference("usuarios").child(uidUser).child("rele3")

            if (activar) {
                myDispRef.child("activar").setValue(true)
            } else {
                myDispRef.child("activar").setValue(false)
            }
        }
    }

    private fun verificarEstadoTemporizador3() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val uidUser = user.uid
            val database = FirebaseDatabase.getInstance()

            val myDispRef = database.getReference("usuarios").child(uidUser).child("rele3")

            val postListener = object : ValueEventListener2 {
                @SuppressLint("SetTextI18n")
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // Get Post object and use the values to update the UI
                    val temporizador3 = dataSnapshot.getValue(Temporizador3Server::class.java)
                    binding.t3Switch.isChecked = temporizador3?.activar == true
                    binding.encendidoT3EditText.setText(temporizador3?.h_on_rele3?.toString() + ":" + temporizador3?.m_on_rele3?.toString())
                    binding.apagadoT3EditText.setText(temporizador3?.h_off_rele3?.toString() + ":" + temporizador3?.m_off_rele3?.toString())
                    // ...
                }

                override fun onCancelled(databaseError: DatabaseError) {

                }
            }
            myDispRef.addValueEventListener(postListener)

        }

    }

    private fun showTimePickerDialogOffT3() {
        val timePicker = TimePickerFragment { hour, minute -> onTimeSelectedOffT3(hour, minute) }
        fragmentManager?.let { timePicker.show(it, "time") }
    }

    @SuppressLint("SetTextI18n")
    private fun onTimeSelectedOffT3(hour: Int, minute: Int) {
        binding.apagadoT3EditText.setText("$hour:$minute")
        horaOffT3Firebase(hour, minute)
    }

    private fun horaOffT3Firebase(hour: Int, minute: Int) {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val uidUser = user.uid
            val database = FirebaseDatabase.getInstance()
            val myDispRef = database.getReference("usuarios").child(uidUser).child("rele3")

            val childUpdates = HashMap<String, Any>()
            childUpdates["h_off_rele3"] = hour
            childUpdates["m_off_rele3"] = minute

            myDispRef.updateChildren(childUpdates)
        }
    }

    private fun showTimePickerDialogOnT3() {
        val timePicker = TimePickerFragment { hour, minute -> onTimeSelectedOnT3(hour, minute) }
        fragmentManager?.let { timePicker.show(it, "time") }
    }

    @SuppressLint("SetTextI18n")
    private fun onTimeSelectedOnT3(hour: Int, minute: Int) {
        binding.encendidoT3EditText.setText("$hour:$minute")
        horaOnT3Firebase(hour, minute)
    }

    private fun horaOnT3Firebase(hour: Int, minute: Int) {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val uidUser = user.uid
            val database = FirebaseDatabase.getInstance()
            val myDispRef = database.getReference("usuarios").child(uidUser).child("rele3")

            val childUpdates = HashMap<String, Any>()
            childUpdates["h_on_rele3"] = hour
            childUpdates["m_on_rele3"] = minute

            myDispRef.updateChildren(childUpdates)
        }
    }

    private fun verificarEstadoTemporizador1() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val uidUser = user.uid
            val database = FirebaseDatabase.getInstance()

            val myDispRef = database.getReference("usuarios").child(uidUser).child("rele1")

            val postListener = object : ValueEventListener2 {
                @SuppressLint("SetTextI18n")
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // Get Post object and use the values to update the UI
                    val temporizador1 = dataSnapshot.getValue(Temporizador1Server::class.java)
                    binding.t1Switch.isChecked = temporizador1?.activar == true
                    binding.encendidoT1EditText.setText(temporizador1?.h_on_rele1?.toString() + ":" + temporizador1?.m_on_rele1?.toString())
                    binding.apagadoT1EditText.setText(temporizador1?.h_off_rele1?.toString() + ":" + temporizador1?.m_off_rele1?.toString())
                    // ...
                }

                override fun onCancelled(databaseError: DatabaseError) {

                }
            }
            myDispRef.addValueEventListener(postListener)

        }

    }

    private fun verificarEstadoTemporizador2() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val uidUser = user.uid
            val database = FirebaseDatabase.getInstance()

            val myDispRef = database.getReference("usuarios").child(uidUser).child("rele2")

            val postListener = object : ValueEventListener2 {
                @SuppressLint("SetTextI18n")
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // Get Post object and use the values to update the UI
                    val temporizador2 = dataSnapshot.getValue(Temporizador2Server::class.java)
                    binding.t2Switch.isChecked = temporizador2?.activar == true
                    binding.encendidoT2EditText.setText(temporizador2?.h_on_rele2?.toString() + ":" + temporizador2?.m_on_rele2?.toString())
                    binding.apagadoT2EditText.setText(temporizador2?.h_off_rele2?.toString() + ":" + temporizador2?.m_off_rele2?.toString())
                    // ...
                }

                override fun onCancelled(databaseError: DatabaseError) {

                }
            }
            myDispRef.addValueEventListener(postListener)

        }

    }

    private fun activarT2Firebase(activar: Boolean) {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val uidUser = user.uid
            val database = FirebaseDatabase.getInstance()
            val myDispRef = database.getReference("usuarios").child(uidUser).child("rele2")

            if (activar) {
                myDispRef.child("activar").setValue(true)
            } else {
                myDispRef.child("activar").setValue(false)
            }
        }
    }

    private fun activarT1Firebase(activar: Boolean) {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val uidUser = user.uid
            val database = FirebaseDatabase.getInstance()
            val myDispRef = database.getReference("usuarios").child(uidUser).child("rele1")

            if (activar) {
                myDispRef.child("activar").setValue(true)
            } else {
                myDispRef.child("activar").setValue(false)
            }

        }
    }

    private fun showTimePickerDialogOffT2() {
        val timePicker = TimePickerFragment { hour, minute -> onTimeSelectedOffT2(hour, minute) }
        fragmentManager?.let { timePicker.show(it, "time") }
    }

    @SuppressLint("SetTextI18n")
    private fun onTimeSelectedOffT2(hour: Int, minute: Int) {
        binding.apagadoT2EditText.setText("$hour:$minute")
        horaOffT2Firebase(hour, minute)
    }

    private fun horaOffT2Firebase(hour: Int, minute: Int) {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val uidUser = user.uid
            val database = FirebaseDatabase.getInstance()
            val myDispRef = database.getReference("usuarios").child(uidUser).child("rele2")

            val childUpdates = HashMap<String, Any>()
            childUpdates["h_off_rele2"] = hour
            childUpdates["m_off_rele2"] = minute

            myDispRef.updateChildren(childUpdates)
        }
    }

    private fun showTimePickerDialogOnT2() {
        val timePicker = TimePickerFragment { hour, minute -> onTimeSelectedOnT2(hour, minute) }
        fragmentManager?.let { timePicker.show(it, "time") }
    }

    @SuppressLint("SetTextI18n")
    private fun onTimeSelectedOnT2(hour: Int, minute: Int) {
        binding.encendidoT2EditText.setText("$hour:$minute")
        horaOnT2Firebase(hour, minute)
    }

    private fun horaOnT2Firebase(hour: Int, minute: Int) {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val uidUser = user.uid
            val database = FirebaseDatabase.getInstance()
            val myDispRef = database.getReference("usuarios").child(uidUser).child("rele2")

            val childUpdates = HashMap<String, Any>()
            childUpdates["h_on_rele2"] = hour
            childUpdates["m_on_rele2"] = minute

            myDispRef.updateChildren(childUpdates)
        }
    }

    private fun showTimePickerDialogOffT1() {
        val timePicker = TimePickerFragment { hour, minute -> onTimeSelectedOffT1(hour, minute) }
        fragmentManager?.let { timePicker.show(it, "time") }
    }

    @SuppressLint("SetTextI18n")
    private fun onTimeSelectedOffT1(hour: Int, minute: Int) {
        binding.apagadoT1EditText.setText("$hour:$minute")
        horaOffT1Firebase(hour, minute)
    }

    private fun horaOffT1Firebase(hour: Int, minute: Int) {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val uidUser = user.uid
            val database = FirebaseDatabase.getInstance()
            val myDispRef = database.getReference("usuarios").child(uidUser).child("rele1")

            val childUpdates = HashMap<String, Any>()
            childUpdates["h_off_rele1"] = hour
            childUpdates["m_off_rele1"] = minute

            myDispRef.updateChildren(childUpdates)
        }
    }

    private fun showTimePickerDialogOnT1() {
        val timePicker = TimePickerFragment { hour, minute -> onTimeSelectedOnT1(hour, minute) }
        fragmentManager?.let { timePicker.show(it, "time") }
    }

    @SuppressLint("SetTextI18n")
    private fun onTimeSelectedOnT1(hour: Int, minute: Int) {
        binding.encendidoT1EditText.setText("$hour:$minute")
        horaOnT1Firebase(hour, minute)
    }

    private fun horaOnT1Firebase(hour: Int, minute: Int) {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val uidUser = user.uid
            val database = FirebaseDatabase.getInstance()
            val myDispRef = database.getReference("usuarios").child(uidUser).child("rele1")

            val childUpdates = HashMap<String, Any>()
            childUpdates["h_on_rele1"] = hour
            childUpdates["m_on_rele1"] = minute

            myDispRef.updateChildren(childUpdates)
        }
    }
}