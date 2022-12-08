package com.daferarevalo.espapp.ui.channels

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.daferarevalo.espapp.R
import com.daferarevalo.espapp.databinding.FragmentChannelsBinding


class ChannelsFragment : Fragment(R.layout.fragment_channels) {

    private lateinit var binding: FragmentChannelsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentChannelsBinding.bind(view)

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