package com.daferarevalo.espapp.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.daferarevalo.espapp.core.Result
import com.daferarevalo.espapp.domain.auth.AuthRepo
import kotlinx.coroutines.Dispatchers

class AuthViewModel(private val repo:AuthRepo):ViewModel() {
    fun singIn(email:String,pass:String)= liveData(viewModelScope.coroutineContext + Dispatchers.IO){
        emit(Result.Loading())
        try {
            emit(Result.Success(repo.signIn(email,pass)))
        }catch (e: Exception){
            emit(Result.Failure(e))
        }
    }

    fun signUp(username: String, password: String,email: String, nombreRed: String, passRed: String) = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            emit(Result.Success(repo.singUp(username,password,email,nombreRed,passRed)))
        }catch (e: Exception){
            emit(Result.Failure(e))
        }
    }
}

class AuthViewModelFactory(private val repo: AuthRepo): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AuthViewModel(repo) as T
    }
}