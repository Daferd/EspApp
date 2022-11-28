package com.daferarevalo.espapp.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.daferarevalo.espapp.R
import com.daferarevalo.espapp.databinding.FragmentProfileBinding
import com.daferarevalo.espapp.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentProfileBinding.bind(view)

        binding.LogOutButton.setOnClickListener {
            val user = FirebaseAuth.getInstance().signOut()
            goToLoginActivity()

        }
    }

    private fun goToLoginActivity() {
        val intent = Intent(context, LoginActivity::class.java)
        startActivity(intent)
        //finish()
    }

}