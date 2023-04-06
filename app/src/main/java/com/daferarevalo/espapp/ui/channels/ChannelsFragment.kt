package com.daferarevalo.espapp.ui.channels

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.daferarevalo.espapp.R
import com.daferarevalo.espapp.core.Result
import com.daferarevalo.espapp.core.hide
import com.daferarevalo.espapp.core.show
import com.daferarevalo.espapp.data.remote.home.HomeDataSource
import com.daferarevalo.espapp.databinding.FragmentChannelsBinding
import com.daferarevalo.espapp.domain.home.HomeRepoImpl
import com.daferarevalo.espapp.presentation.home.HomeViewModel
import com.daferarevalo.espapp.presentation.home.HomeViewModelFactory


class ChannelsFragment : Fragment(R.layout.fragment_channels) {

    private lateinit var binding: FragmentChannelsBinding
    private val viewModel by viewModels<HomeViewModel> {
        HomeViewModelFactory(HomeRepoImpl(
            HomeDataSource()
        )
        )
    }

    private var ch = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentChannelsBinding.bind(view)

        viewModel.checkNumberChannelsModel().observe(viewLifecycleOwner, Observer{ result ->
            when(result){
                is Result.Loading -> {
                    binding.progressBar.show()
                    binding.addChanelButton.isEnabled = false
                }
                is Result.Success -> {
                    binding.progressBar.hide()
                    binding.addChanelButton.isEnabled = true
                    ch = result.data
                    //Toast.makeText(context,"Bienvenido $")
                    when(ch){
                        1-> {
                            binding.Channel1CardView.show()
                        }
                        2-> {
                            binding.Channel1CardView.show()
                            binding.Channel2CardView.show()
                        }
                        3-> {
                            binding.Channel1CardView.show()
                            binding.Channel2CardView.show()
                            binding.Channel3CardView.show()
                        }
                        /*4-> {
                            binding.Channel4CardView.show()
                        }*/
                    }
                }
                is Result.Failure -> {
                    binding.progressBar.hide()
                    binding.addChanelButton.isEnabled = true
                    Log.d("ERROR","Error: ${result.exception}")
                    Toast.makeText(
                        context,
                        "Error: ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
        })

        binding.addChanelButton.setOnClickListener {
            ch += 1

            if(ch>3){
                Toast.makeText(context,"No se puede agregar mas canales",Toast.LENGTH_SHORT).show()
            }else{
                viewModel.addChannelModel(ch).observe(viewLifecycleOwner, Observer{ result ->
                    when(result){
                        is Result.Loading -> {
                            binding.progressBar.show()
                            binding.addChanelButton.isEnabled = false
                        }
                        is Result.Success -> {
                            binding.progressBar.hide()
                            binding.addChanelButton.isEnabled = true
                            //Toast.makeText(context,"Bienvenido $")
                            when(ch){
                                1-> {
                                    binding.Channel1CardView.show()
                                }
                                2-> {
                                    binding.Channel2CardView.show()
                                }
                                3-> {
                                    binding.Channel3CardView.show()
                                }
                                /*4-> {
                                    binding.Channel4CardView.show()
                                }*/
                            }
                        }
                        is Result.Failure -> {
                            binding.progressBar.hide()
                            binding.addChanelButton.isEnabled = true
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

        binding.Channel1CardView.setOnClickListener {
            val action = ChannelsFragmentDirections.actionChannelsFragmentToEditChannelFragment(1)
            findNavController().navigate(action)
        }

        binding.Channel2CardView.setOnClickListener {
            val action = ChannelsFragmentDirections.actionChannelsFragmentToEditChannelFragment(2)
            findNavController().navigate(action)
        }

        binding.Channel3CardView.setOnClickListener {
            val action = ChannelsFragmentDirections.actionChannelsFragmentToEditChannelFragment(3)
            findNavController().navigate(action)
        }

    }
    

}