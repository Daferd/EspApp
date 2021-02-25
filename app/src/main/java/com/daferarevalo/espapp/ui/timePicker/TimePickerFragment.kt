package com.daferarevalo.espapp.ui.timePicker

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class TimePickerFragment(val listener: (hour: Int, minute: Int) -> Unit) : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val dialog = TimePickerDialog(context, this, hour, minute, true)
        //val dialog = TimePickerDialog(context, R.style.TimePicker, this, hour, minute, true)
        return dialog
    }

    override fun onTimeSet(view: TimePicker?, hourOfday: Int, minute: Int) {
        listener(hourOfday, minute)
        //horaFirebase(hourOfday, minute)
    }

    private fun horaFirebase(hourOfday: Int, minute: Int) {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val uidUser = user.uid
            val database = FirebaseDatabase.getInstance()
            val myLedRef = database.getReference("usuarios").child(uidUser).child("rele1")

            myLedRef.child("h_on_rele1").setValue(hourOfday)
            myLedRef.child("h_off_rele1").setValue(minute)
        }
    }
}