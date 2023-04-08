package com.daferarevalo.espapp.domain.home

import com.daferarevalo.espapp.data.model.ChannelServer
import com.daferarevalo.espapp.data.remote.home.HomeDataSource

class HomeRepoImpl(private val dataSource: HomeDataSource):HomeRepo {
    override suspend fun readSensor(sensorPin: Int) = dataSource.readSensor(sensorPin)

    override suspend fun addChannel(channelPin: Int) = dataSource.addChannel(channelPin)
    override suspend fun checkChannel(channelPin: Int): ChannelServer =
        dataSource.checkChannel(channelPin)

    override suspend fun checkNumberChannels(): Long = dataSource.checkNumberChannels()
    override suspend fun turnChannel(channelPin: Int, stateChannel: Boolean) =
        dataSource.turnChannel(channelPin, stateChannel)

    override suspend fun applyChangesChannel(
        channelPin: Int,
        active: Boolean,
        state: Boolean,
        h_off: Int,
        h_on: Int,
        m_off: Int,
        m_on: Int
    ) = dataSource.applyChangesChannel(channelPin, active, state, h_off, h_on, m_off, m_on)

}