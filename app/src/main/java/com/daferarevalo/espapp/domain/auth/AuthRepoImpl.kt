package com.daferarevalo.espapp.domain.auth

import com.daferarevalo.espapp.data.remote.auth.AuthDataSource
import com.google.firebase.auth.FirebaseUser

class AuthRepoImpl(private var dataSource:AuthDataSource):AuthRepo {
    override suspend fun signIn(email: String, password: String): FirebaseUser? = dataSource.singIn(email,password)
    override suspend fun singUp(
        username: String,
        password: String,
        email: String,
        nombreRed: String,
        passRed: String
    ): FirebaseUser? =
        dataSource.singUp(username,password,email,nombreRed,passRed)
}