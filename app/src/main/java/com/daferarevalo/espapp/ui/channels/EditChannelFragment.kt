package com.daferarevalo.espapp.ui.channels

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.daferarevalo.espapp.R
import androidx.lifecycle.Observer
import com.daferarevalo.espapp.core.Result
import com.daferarevalo.espapp.core.hide
import com.daferarevalo.espapp.core.show
import com.daferarevalo.espapp.data.model.ChannelServer
import com.daferarevalo.espapp.data.remote.home.HomeDataSource
import com.daferarevalo.espapp.databinding.FragmentEditChannelBinding
import com.daferarevalo.espapp.domain.home.HomeRepoImpl
import com.daferarevalo.espapp.presentation.home.HomeViewModel
import com.daferarevalo.espapp.presentation.home.HomeViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.tasks.await

class EditChannelFragment : DialogFragment() {

    private lateinit var binding:FragmentEditChannelBinding
    val args: EditChannelFragmentArgs by navArgs()
    private val viewModel by viewModels<HomeViewModel> {
        HomeViewModelFactory(
            HomeRepoImpl(
            HomeDataSource()
        )
        )
    }

    private var channelStatus=ChannelServer()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_channel, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentEditChannelBinding.bind(view)

        when(args.channelPin){
            1->{
               binding.turnButton.text = "Canal 1"
            }
            2->{
                binding.turnButton.text = "Canal 2"
            }
            3->{
                binding.turnButton.text = "Canal 3"
            }
        }
        viewModel.checkChannelModel(args.channelPin).observe(viewLifecycleOwner, Observer{ result ->
            when(result){
                is Result.Loading -> {
                    //binding.progressBar.show()
                    //binding.addChanelButton.isEnabled = false
                }
                is Result.Success -> {
                    channelStatus = result.data

                    //binding.encendidoT1EditText.text = "${channelStatus.h_on_rele1}:${channelStatus.m_on_rele1}"
                    binding.t1Switch.isChecked = channelStatus.activar == true
                    binding.encendidoT1EditText.setText("${channelStatus.h_on_rele1}:${channelStatus.m_on_rele1}")
                    binding.apagadoT1EditText.setText("${channelStatus.h_off_rele1}:${channelStatus.m_off_rele1}")
                    //binding.progressBar.hide()
                    //binding.addChanelButton.isEnabled = true
                    //Toast.makeText(context,"Bienvenido $")
                    if(channelStatus.estado){
                        binding.turnButton.setBackgroundColor(Color.GREEN)
                    }else{
                        binding.turnButton.setBackgroundColor(Color.RED)
                    }
                }
                is Result.Failure -> {
                    //binding.progressBar.hide()
                    //binding.addChanelButton.isEnabled = true
                    Toast.makeText(
                        context,
                        "Error: ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
        })
        binding.turnButton.setOnClickListener {
            val state = !channelStatus.estado
            viewModel.turnChannelModel(args.channelPin,state).observe(viewLifecycleOwner, Observer{ result ->
                when(result){
                    is Result.Loading -> {
                        //binding.progressBar.show()
                        //binding.addChanelButton.isEnabled = false
                    }
                    is Result.Success -> {
                        if (state){
                            binding.turnButton.setBackgroundColor(Color.GREEN)
                        }else{
                            binding.turnButton.setBackgroundColor(Color.RED)
                        }

                    }
                    is Result.Failure -> {
                        //binding.progressBar.hide()
                        //binding.addChanelButton.isEnabled = true
                        Toast.makeText(
                            context,
                            "Error: ${result.exception}",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                }
            })
        }
        /*Log.d("chp","channelPin = ${args.channelPin}")
        var estado = verificarEstadoRele(args.channelPin)
        binding.titleTextView.text = "Canal ${args.channelPin}"
        binding.turnButton.text = "Canal ${args.channelPin}"
        binding.turnButton.setOnClickListener {

            when(args.channelPin){

                1 -> {
                    Log.d("chp","Estado=$estado")
                    estado = verificarEstadoRele(1)
                    encenderApagarRele1(!estado)
                }
                2 -> {
                    Log.d("chp","Estado=$estado")
                    estado = verificarEstadoRele(2)
                    encenderApagarRele2(!estado)
                }
                3 -> {
                    Log.d("chp","Estado=$estado")
                    estado = verificarEstadoRele(3)
                    encenderApagarRele3(estado)
                }

            }

        }*/
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

            val childUpdates = HashMap<String, Any>()
            childUpdates["estado"] = estadoRele1.not()
            myDispRef.updateChildren(childUpdates)

        }
    }

}