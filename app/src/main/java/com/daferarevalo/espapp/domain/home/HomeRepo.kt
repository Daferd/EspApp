package com.daferarevalo.espapp.domain.home

import com.daferarevalo.espapp.data.model.ChannelServer
import com.daferarevalo.espapp.data.remote.home.HomeDataSource

interface HomeRepo {
    suspend fun addChannel(channelPin: Int)
    suspend fun checkChannel(channelPin: Int):ChannelServer
    suspend fun checkNumberChannels():Int

    suspend fun turnChannel(channelPin: Int, stateChannel:Boolean)

    suspend fun updateChannel(channelPin:Int)
}