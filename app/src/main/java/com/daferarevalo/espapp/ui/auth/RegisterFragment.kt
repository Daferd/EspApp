package com.daferarevalo.espapp.ui.auth

import android.content.Intent
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
import com.daferarevalo.espapp.core.hide
import com.daferarevalo.espapp.core.hideKeyboard
import com.daferarevalo.espapp.core.show
import com.daferarevalo.espapp.data.remote.auth.AuthDataSource
import com.daferarevalo.espapp.databinding.FragmentRegisterBinding
import com.daferarevalo.espapp.domain.auth.AuthRepoImpl
import com.daferarevalo.espapp.presentation.auth.AuthViewModel
import com.daferarevalo.espapp.presentation.auth.AuthViewModelFactory
import java.util.regex.Matcher
import java.util.regex.Pattern
import com.daferarevalo.espapp.core.Result


class RegisterFragment : Fragment(R.layout.fragment_register) {
    private lateinit var binding: FragmentRegisterBinding
    private val viewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(
            AuthRepoImpl(
                AuthDataSource()
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRegisterBinding.bind(view)

        singUp()


    }

    private fun singUp(){

        binding.registerButton.setOnClickListener {
            val username = binding.editTextUsername.text.toString().trim()
            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()
            val confirmPassword = binding.editTextConfirmPassword.text.toString().trim()
            it.hideKeyboard()
            if (validateUserData(username,email,password,confirmPassword)) return@setOnClickListener
            createUser(username,password,email,"","")
        }
    }

    private fun validateUserData(
        username: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        if (username.isEmpty()) {
            binding.editTextUsername.error = "Username is empty"
            return true
        }
        if (email.isEmpty()) {
            binding.editTextEmail.error = "email is empty"
            return true
        }
        if(!esCorreo(email)){
            binding.editTextEmail.error = "El correo no es valido"
            return true
        }
        if (password.isEmpty()) {
            binding.editTextPassword.error = "Password is empty"
            return true
        }
        if (confirmPassword.isEmpty()) {
            binding.editTextConfirmPassword.error = "Confirm password is empty"
            return true
        }

        if(password.length < 6){
            binding.editTextPassword.error = "La contraseña debe contener minimo 6 caracteres"
            return true
        }

        if (password != confirmPassword) {
            binding.editTextPassword.error = "Las contraseñas no coinciden"
            binding.editTextConfirmPassword.error = "Las contraseñas no coinciden"
            return true
        }
        return false
    }

    fun esCorreo(texto:String):Boolean{
        val patroncito: Pattern =Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
        val comparador: Matcher =patroncito.matcher(texto)
        return comparador.find()
    }

    private fun createUser(username: String, password: String,email: String, nombreRed: String, passRed: String) {
        viewModel.signUp(username,password,email,nombreRed,passRed).observe(viewLifecycleOwner, Observer { result ->
            when(result){
                is Result.Loading ->{
                    binding.progressBar.show()
                    binding.registerButton.isEnabled = false
                }
                is Result.Success -> {
                    binding.progressBar.hide()
                    //Toast.makeText(context,"Verifica el registro en tu cuenta de correo",Toast.LENGTH_LONG).show()
                    findNavController().navigate(R.id.action_registerFragment_to_navigation_home)
                    //goToBluetoothActivity()
                }
                is Result.Failure -> {
                    binding.progressBar.hide()
                    binding.registerButton.isEnabled = true
                    Toast.makeText(requireContext(),"Error: ${result.exception}",Toast.LENGTH_LONG).show()
                    Log.d("Que","Error: ${result.exception}")
                }
            }
        })
    }

    private fun goToBluetoothActivity() {
        //val intent = Intent(context, BluetoothActivity::class.java)
        //startActivity(intent)
        //finish()
    }
}