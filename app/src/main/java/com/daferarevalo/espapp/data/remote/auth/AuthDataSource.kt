package com.daferarevalo.espapp.data.remote.auth

import android.net.Uri
import com.daferarevalo.espapp.data.model.UserServer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class AuthDataSource {
    suspend fun singIn(email: String, password: String): FirebaseUser?{
        val authResult = FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).await()
        return authResult.user
    }

    suspend fun singUp(username: String, password: String,email: String, nombreRed: String, passRed: String): FirebaseUser? {
        val authResult = FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).await()
        authResult.user?.let { user ->
            //user.sendEmailVerification()

            var profileDates = UserProfileChangeRequest.Builder()
                .setDisplayName(username).setPhotoUri(Uri.parse("https://firebasestorage.googleapis.com/v0/b/goinnapp.appspot.com/o/usuario.png?alt=media&token=2ce8121f-9ca7-49b4-bc27-f905f193dce2"))
                .build()

            user.updateProfile(profileDates).await()

            var db = FirebaseFirestore.getInstance()

            db.collection("users").document(user.uid).set(
                UserServer(
                    user.uid,
                    username,
                    email,
                    "Aqui url de la foto",
                    nombreRed,
                    passRed,
                    false
                ),
            )

            val database = Firebase.database
            var myRef = database.getReference("usersUID/${password}")
            myRef.setValue(user.uid)

            myRef = database.getReference("users/${user.uid}/channels/numberChannels")
            myRef.setValue(0)
        }

        return authResult.user
    }

}