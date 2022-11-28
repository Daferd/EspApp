package com.daferarevalo.espapp.domain.home

import com.daferarevalo.espapp.data.remote.home.HomeDataSource

interface HomeRepo {
    suspend fun addChannel(channelPin: Int)
    suspend fun updateChannel(channelPin:Int)
}