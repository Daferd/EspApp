package com.daferarevalo.espapp.ui.channels

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.daferarevalo.espapp.R
import com.daferarevalo.espapp.core.Result
import com.daferarevalo.espapp.data.model.ChannelServer
import com.daferarevalo.espapp.data.remote.home.HomeDataSource
import com.daferarevalo.espapp.databinding.FragmentEditChannelBinding
import com.daferarevalo.espapp.domain.home.HomeRepoImpl
import com.daferarevalo.espapp.presentation.home.HomeViewModel
import com.daferarevalo.espapp.presentation.home.HomeViewModelFactory
import com.daferarevalo.espapp.ui.timePicker.TimePickerFragment

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

    private var channelStatus = ChannelServer()
    private var h_on = 10
    private var h_off = 15
    private var m_on = 30
    private var m_off = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_channel, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentEditChannelBinding.bind(view)

        viewModel.checkChannelModel(args.channelPin)
            .observe(viewLifecycleOwner, Observer { result ->
                when (result) {
                    is Result.Loading -> {
                        //binding.progressBar.show()
                        //binding.addChanelButton.isEnabled = false
                    }
                    is Result.Success -> {
                        channelStatus = result.data

                        //binding.encendidoT1EditText.text = "${channelStatus.h_on_rele1}:${channelStatus.m_on_rele1}"
                        binding.activeSwitch.isChecked = channelStatus.activar == true
                        binding.turnOnChannelEditText.setText("${channelStatus.h_on}:${channelStatus.m_on}")
                        binding.turnOffChannelEditTtext.setText("${channelStatus.h_off}:${channelStatus.m_off}")
                        //binding.progressBar.hide()
                        //binding.addChanelButton.isEnabled = true
                        //Toast.makeText(context,"Bienvenido $")
                        if (channelStatus.estado) {
                            binding.turnOnOffButton.setBackgroundColor(Color.GREEN)
                        } else {
                            binding.turnOnOffButton.setBackgroundColor(Color.RED)
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
        when (args.channelPin) {
            1 -> {
                binding.titleTextView.text = "Canal 1"
                binding.turnOnOffButton.text = "Canal 1"
            }
            2 -> {
                binding.titleTextView.text = "Canal 2"
                binding.turnOnOffButton.text = "Canal 2"
            }
            3 -> {
                binding.titleTextView.text = "Canal 3"
                binding.turnOnOffButton.text = "Canal 3"
            }
        }

        binding.turnOnChannelEditText.setOnClickListener {
            showTimePickerDialogOn(true)
        }

        binding.turnOffChannelEditTtext.setOnClickListener {
            showTimePickerDialogOn(false)
        }

        binding.turnOnOffButton.setOnClickListener {
            val state = !channelStatus.estado
            viewModel.turnChannelModel(args.channelPin, state)
                .observe(viewLifecycleOwner, Observer { result ->
                    when (result) {
                        is Result.Loading -> {
                            //binding.progressBar.show()
                            //binding.addChanelButton.isEnabled = false
                        }
                        is Result.Success -> {
                            if (state) {
                                binding.turnOnOffButton.setBackgroundColor(Color.GREEN)
                        }else{
                                binding.turnOnOffButton.setBackgroundColor(Color.RED)
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

        binding.activeSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            //Se debe organizar la logica del suiche respecto a su actualización en tiempo real en la base de datos
            //  y el color (verde/rojo) del boton de encendido
            if (isChecked) {
                binding.turnOnOffButton.isEnabled = false
                binding.turnOnOffButton.setBackgroundColor(Color.GRAY)
                //Se actualiza los parametros del canal en la base de datos de Firebase
                viewModel.applyChangesChannelModel(
                    args.channelPin,
                    true,
                    channelStatus.estado,
                    h_off,
                    h_on,
                    m_off,
                    m_on
                ).observe(viewLifecycleOwner, Observer { result ->
                    when (result) {
                        is Result.Loading -> {
                            Toast.makeText(context, "cargando..", Toast.LENGTH_SHORT).show()
                        }
                        is Result.Success -> {
                            Toast.makeText(context, "OK", Toast.LENGTH_SHORT).show()
                        }
                        is Result.Failure -> {


                        }
                    }
                })
            } else {
                binding.turnOnOffButton.isEnabled = true
                if (channelStatus.estado) {
                    binding.turnOnOffButton.setBackgroundColor(Color.GREEN)
                } else {
                    binding.turnOnOffButton.setBackgroundColor(Color.RED)
                }
                viewModel.applyChangesChannelModel(
                    args.channelPin,
                    false,
                    channelStatus.estado,
                    h_off,
                    h_on,
                    m_off,
                    m_on
                ).observe(viewLifecycleOwner, Observer { result ->
                    when (result) {
                        is Result.Loading -> {
                            Toast.makeText(context, "cargando..", Toast.LENGTH_SHORT).show()
                        }
                        is Result.Success -> {
                            Toast.makeText(context, "OK", Toast.LENGTH_SHORT).show()
                        }
                        is Result.Failure -> {


                        }
                    }
                })

            }
        }
    }

    private fun showTimePickerDialogOn(modo: Boolean) {
        val timePicker = TimePickerFragment { hour, minute -> onTimeSelectedOn(modo, hour, minute) }
        fragmentManager?.let { timePicker.show(it, "time") }
    }

    @SuppressLint("SetTextI18n")
    private fun onTimeSelectedOn(modo: Boolean, hour: Int, minute: Int) {
        if (modo) {
            h_on = hour
            m_on = minute
            binding.turnOnChannelEditText.setText("$hour:$minute")
        } else {
            h_off = hour
            m_off = minute
            binding.turnOffChannelEditTtext.setText("$hour:$minute")
        }
    }

}