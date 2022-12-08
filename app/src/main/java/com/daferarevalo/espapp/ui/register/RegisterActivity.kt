package com.daferarevalo.espapp.ui.register

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.daferarevalo.espapp.databinding.ActivityRegisterBinding
import com.daferarevalo.espapp.data.model.UserServer
import com.daferarevalo.espapp.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = FirebaseAuth.getInstance()

        binding.registerRegisterButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val email = binding.emailResgisterEditText.text.toString()
            val password = binding.passwordRegisterEditText.text.toString()
            val repPassword = binding.repPasswordEditText.text.toString()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || repPassword.isEmpty()) {
                Toast.makeText(this, "Algunos campos estan vacios", Toast.LENGTH_SHORT).show()
            } else if (password != repPassword) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            } else {
                registerWithFirebase(name, email, password)
            }
        }
    }

    private fun registerWithFirebase(name: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val uid = auth.currentUser?.uid
                        createUserFirebase(name, email, uid)
                        Toast.makeText(this, "Usuario registrado", Toast.LENGTH_SHORT).show()
                        goToLoginActivity()
                    } else {
                        Toast.makeText(this, "Error en la autenticacion", Toast.LENGTH_SHORT).show()
                    }
                }
    }

    private fun createUserFirebase(name: String, email: String, uid: String?) {
        val database = FirebaseDatabase.getInstance()
        val myUserRef = database.getReference("usuarios")

        val usuario = UserServer(uid, name, email)
        uid?.let {
            myUserRef.child(uid).setValue(usuario)
        }
    }

    private fun goToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        goToLoginActivity()
    }
}