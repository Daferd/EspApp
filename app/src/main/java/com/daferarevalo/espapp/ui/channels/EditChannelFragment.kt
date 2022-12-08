package com.daferarevalo.espapp.ui.channels

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.daferarevalo.espapp.R
import com.daferarevalo.espapp.data.model.ChannelServer
import com.daferarevalo.espapp.databinding.FragmentEditChannelBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.tasks.await

class EditChannelFragment : DialogFragment() {

    private lateinit var binding:FragmentEditChannelBinding
    val args: EditChannelFragmentArgs by navArgs()



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_channel, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentEditChannelBinding.bind(view)

        Log.d("chp","channelPin = ${args.channelPin}")
        var estado = verificarEstadoRele(args.channelPin)
        binding.titleTextView.text = "Canal ${args.channelPin}"
        binding.turnButton.text = "Canal ${args.channelPin}"
        binding.turnButton.setOnClickListener {
            when(args.channelPin){
                1 -> {
                    Log.d("chp","Estado=$estado")
                    estado = verificarEstadoRele(1)
                    encenderApagarRele1(estado)
                }
                2 -> {
                    Log.d("chp","Estado=$estado")
                    estado = verificarEstadoRele(2)
                    encenderApagarRele2(estado)
                }
                3 -> {
                    Log.d("chp","Estado=$estado")
                    estado = verificarEstadoRele(3)
                    encenderApagarRele3(estado)
                }

            }

        }
    }

    private fun verificarEstadoRele(channelPin: Int):Boolean{
        var estadoRele = false
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val uidUser = user.uid
            val database = FirebaseDatabase.getInstance()
            val myDispRef = database.getReference("users").child(uidUser).child("channels/channel${channelPin}").child("estado")

            val postListener = object : ValueEventListener {
                @SuppressLint("ResourceAsColor")
                override fun onDataChange(snapshot: DataSnapshot) {
                    estadoRele = snapshot.value as Boolean

                    if (estadoRele == false) {
                        binding.turnButton.setBackgroundColor(Color.RED)
                    } else {
                        binding.turnButton.setBackgroundColor(Color.GREEN)
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            }
            myDispRef.addValueEventListener(postListener)
        }
        return estadoRele
    }

    /*
    private fun encenderApagarRele(channelPin: Int,estadoRele: Boolean) {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val uidUser = user.uid
            val database = FirebaseDatabase.getInstance()
            val myDispRef = database.getReference("users").child(uidUser).child("channels/channel$channelPin")

            val estadoReleAux = estadoRele.not()

            val childUpdates = HashMap<String, Any>()
            childUpdates["estado"] = estadoReleAux
            myDispRef.updateChildren(childUpdates)

        }
    }
    */
    private fun encenderApagarRele(channelPin: Int,estadoRele: Boolean){
        val user = FirebaseAuth.getInstance().currentUser

        user?.let {
            val db = FirebaseDatabase.getInstance()
                .getReference("users/${user.uid}/channels/channel${channelPin}/estado")

            Log.d("chp","estadoRele = ${estadoRele}")
            db.setValue(!estadoRele)
        }
    }

    private fun encenderApagarRele3(estadoRele3: Boolean) {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val uidUser = user.uid
            val database = FirebaseDatabase.getInstance()
            val myDispRef = database.getReference("users").child(uidUser).child("channels/channel3")

            val estadoRele3Aux = estadoRele3.not()

            val childUpdates = HashMap<String, Any>()
            childUpdates["estado"] = estadoRele3Aux
            myDispRef.updateChildren(childUpdates)

        }
    }

    private fun encenderApagarRele2(estadoRele2: Boolean) {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val uidUser = user.uid
            val database = FirebaseDatabase.getInstance()
            val myDispRef = database.getReference("users").child(uidUser).child("channels/channel2")

            val estadoRele2Aux = estadoRele2.not()

            val childUpdates = HashMap<String, Any>()
            childUpdates["estado"] = estadoRele2Aux
            myDispRef.updateChildren(childUpdates)

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

}