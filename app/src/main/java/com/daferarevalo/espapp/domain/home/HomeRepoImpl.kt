package com.daferarevalo.espapp.domain.home

import com.daferarevalo.espapp.data.model.ChannelServer
import com.daferarevalo.espapp.data.remote.home.HomeDataSource

class HomeRepoImpl(private val dataSource: HomeDataSource):HomeRepo {
    override suspend fun addChannel(channelPin: Int) = dataSource.addChannel(channelPin)
    override suspend fun checkChannel(channelPin: Int): ChannelServer =dataSource.checkChannel(channelPin)
    override suspend fun checkNumberChannels(): Int = dataSource.checkNumberChannels()
    override suspend fun turnChannel(channelPin: Int, stateChannel: Boolean) = dataSource.turnChannel(channelPin,stateChannel)

    override suspend fun updateChannel(channelPin: Int) {
        TODO("Not yet implemented")
    }
}