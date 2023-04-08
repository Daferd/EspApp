package com.daferarevalo.espapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.daferarevalo.espapp.core.Result
import com.daferarevalo.espapp.domain.home.HomeRepo
import kotlinx.coroutines.Dispatchers

class HomeViewModel(private val repo: HomeRepo):ViewModel() {

    fun readSensorModel(sensorPin: Int) =
        liveData(viewModelScope.coroutineContext + Dispatchers.Main) {
            emit(Result.Loading())
            try {
                emit(Result.Success(repo.readSensor(sensorPin)))
            } catch (e: Exception) {
                emit(Result.Failure(e))
            }
        }

    fun addChannelModel(channelPin: Int) =
        liveData(viewModelScope.coroutineContext + Dispatchers.Main) {
            emit(Result.Loading())
            try {
                emit(Result.Success(repo.addChannel(channelPin)))
            } catch (e: Exception) {
                emit(Result.Failure(e))
            }
        }

    fun checkChannelModel(channelPin: Int) =
        liveData(viewModelScope.coroutineContext + Dispatchers.Main) {
        emit(Result.Loading())
        try {
            emit(Result.Success(repo.checkChannel(channelPin)))
        }catch (e: Exception){
            emit(Result.Failure(e))
        }
    }

    fun checkNumberChannelsModel()= liveData(viewModelScope.coroutineContext + Dispatchers.Main){
        emit(Result.Loading())
        try {
            emit(Result.Success(repo.checkNumberChannels()))
        }catch (e: Exception){
            emit(Result.Failure(e))
        }
    }

    fun turnChannelModel(channelPin: Int, stateChannel: Boolean) =
        liveData(viewModelScope.coroutineContext + Dispatchers.Main) {
            emit(Result.Loading())
            try {
                emit(Result.Success(repo.turnChannel(channelPin, stateChannel)))
            } catch (e: Exception) {
                emit(Result.Failure(e))
            }
        }

    fun applyChangesChannelModel(
        channelPin: Int,
        active: Boolean,
        state: Boolean,
        h_off: Int,
        h_on: Int,
        m_off: Int,
        m_on: Int
    ) = liveData(viewModelScope.coroutineContext + Dispatchers.Main) {
        emit(Result.Loading())
        try {
            emit(
                Result.Success(
                    repo.applyChangesChannel(
                        channelPin,
                        active,
                        state,
                        h_off,
                        h_on,
                        m_off,
                        m_on
                    )
                )
            )
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }
}

class HomeViewModelFactory(private val repo: HomeRepo): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(HomeRepo::class.java).newInstance(repo)
    }

}