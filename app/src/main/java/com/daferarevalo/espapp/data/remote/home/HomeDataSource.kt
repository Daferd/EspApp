package com.daferarevalo.espapp.data.remote.home

import android.util.Log
import com.daferarevalo.espapp.data.model.ChannelServer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class HomeDataSource{

    suspend fun addChannel(channelPin:Int) {
        withContext(Dispatchers.IO) {
            val user = FirebaseAuth.getInstance().currentUser

            user?.let {
                val db = FirebaseDatabase.getInstance()
                    .getReference("users/${user.uid}/channels/channel${channelPin}")
                val channel = ChannelServer(false, false, 15, 12, 0, 0)
                db.setValue(channel).await()
            }

        }
    }



    suspend fun updateChanel(channelPin:Int):ChannelServer?{
        var channel=ChannelServer()
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val db = FirebaseDatabase.getInstance().getReference("users/${user.uid}/channels/channel${channelPin}").get().await()
            channel = db.getValue(ChannelServer::class.java)!!
            Log.d("channel","$channel")
        }
        return channel
    }
}