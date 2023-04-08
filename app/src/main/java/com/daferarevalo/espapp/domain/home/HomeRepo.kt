package com.daferarevalo.espapp.domain.home

import com.daferarevalo.espapp.data.model.ChannelServer

interface HomeRepo {

    suspend fun readSensor(sensorPin: Int): Double
    suspend fun addChannel(channelPin: Int)
    suspend fun checkChannel(channelPin: Int): ChannelServer
    suspend fun checkNumberChannels(): Long

    suspend fun turnChannel(channelPin: Int, stateChannel: Boolean)

    suspend fun applyChangesChannel(
        channelPin: Int,
        active: Boolean,
        state: Boolean,
        h_off: Int,
        h_on: Int,
        m_off: Int,
        m_on: Int
    )
}