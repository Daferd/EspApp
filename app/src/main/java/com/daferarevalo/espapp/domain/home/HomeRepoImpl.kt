package com.daferarevalo.espapp.domain.home

import com.daferarevalo.espapp.data.remote.home.HomeDataSource

class HomeRepoImpl(private val dataSource: HomeDataSource):HomeRepo {
    override suspend fun addChannel(channelPin: Int) = dataSource.addChannel(channelPin)

    override suspend fun updateChannel(channelPin: Int) {
        TODO("Not yet implemented")
    }
}