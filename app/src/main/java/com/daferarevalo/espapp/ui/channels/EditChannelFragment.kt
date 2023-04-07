package com.daferarevalo.espapp.ui.channels

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
                        binding.t1Switch.isChecked = channelStatus.activar == true
                        binding.encendidoT1EditText.setText("${channelStatus.h_on_rele1}:${channelStatus.m_on_rele1}")
                        binding.apagadoT1EditText.setText("${channelStatus.h_off_rele1}:${channelStatus.m_off_rele1}")
                        //binding.progressBar.hide()
                        //binding.addChanelButton.isEnabled = true
                        //Toast.makeText(context,"Bienvenido $")
                        if (channelStatus.estado) {
                            binding.turnButton.setBackgroundColor(Color.GREEN)
                        } else {
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
        when (args.channelPin) {
            1 -> {
                binding.turnButton.text = "Canal 1"
            }
            2 -> {
                binding.turnButton.text = "Canal 2"
            }
            3 -> {
                binding.turnButton.text = "Canal 3"
            }
        }

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

    }


}