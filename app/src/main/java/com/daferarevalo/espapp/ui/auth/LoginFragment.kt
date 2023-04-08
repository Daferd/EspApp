package com.daferarevalo.espapp.ui.auth

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.daferarevalo.espapp.R
import com.daferarevalo.espapp.core.Result
import com.daferarevalo.espapp.core.hide
import com.daferarevalo.espapp.core.show
import com.daferarevalo.espapp.data.remote.auth.AuthDataSource
import com.daferarevalo.espapp.databinding.FragmentLoginBinding
import com.daferarevalo.espapp.domain.auth.AuthRepoImpl
import com.daferarevalo.espapp.presentation.auth.AuthViewModel
import com.daferarevalo.espapp.presentation.auth.AuthViewModelFactory
import com.google.firebase.auth.FirebaseAuth


class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val viewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(
            AuthRepoImpl(
                AuthDataSource()
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentLoginBinding.bind(view)

        val user = FirebaseAuth.getInstance().currentUser

        firebaseAuth.currentUser?.let {
            findNavController().navigate(R.id.action_loginFragment_to_navigation_home)
        }

        binding.loginButton.setOnClickListener {
            Toast.makeText(
                context,
                "Se presiono",
                Toast.LENGTH_SHORT
            ).show()
            val email = binding.editTextEmail.text.toString().trim()
            val pass = binding.editTextPass.text.toString().trim()
            singIn(email,pass)

        }

        binding.registerButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }


    }
    private fun singIn(email: String, pass: String) {
        viewModel.singIn(email,pass).observe(viewLifecycleOwner,Observer{ result ->
            when(result){
                is Result.Loading -> {
                    binding.progressBar.show()
                    binding.loginButton.isEnabled = false
                    binding.registerButton.isEnabled = false
                }
                is Result.Success -> {
                    binding.progressBar.hide()
                    //Toast.makeText(context,"Bienvenido $")
                    goToHomeFragment()
                }
                is Result.Failure -> {
                    binding.progressBar.hide()
                    binding.loginButton.isEnabled = true
                    binding.registerButton.isEnabled = true
                    Toast.makeText(
                        context,
                        "Error: ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
        })
    }
    private fun goToHomeFragment() {
        findNavController().navigate(R.id.action_loginFragment_to_navigation_home)
    }
}