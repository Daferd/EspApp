package com.daferarevalo.espapp.domain.auth

import com.daferarevalo.espapp.data.remote.auth.AuthDataSource
import com.google.firebase.auth.FirebaseUser

interface AuthRepo {
    suspend fun signIn(email:String,password:String):FirebaseUser?
    suspend fun singUp(username: String, password: String,email: String, nombreRed: String, passRed: String): FirebaseUser?
}