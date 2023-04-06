package com.daferarevalo.espapp.data.remote.home

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import com.daferarevalo.espapp.data.model.ChannelServer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class HomeDataSource{

    suspend fun addChannel(channelPin:Int) {
        withContext(Dispatchers.IO) {
            val user = FirebaseAuth.getInstance().currentUser

            user?.let {
                var db = FirebaseDatabase.getInstance()
                    .getReference("users/${user.uid}/channels/channel${channelPin}")
                val channel = ChannelServer(false, false, 15, 12, 0, 0)
                db.setValue(channel).await()

                db = FirebaseDatabase.getInstance()
                    .getReference("users/${user.uid}/channels/numberChannels")

                db.setValue(channelPin).await()
            }
        }
    }

    suspend fun checkChannel(channelPin: Int):ChannelServer{
        var channelState=ChannelServer()
        withContext(Dispatchers.IO){
            val user = FirebaseAuth.getInstance().currentUser

            user?.let {
                val database = FirebaseDatabase.getInstance().reference
                database.keepSynced(true)

                val state = database.child("users/${user.uid}/channels/channel${channelPin}").get().await()

                state.let { dato ->
                    channelState = dato.getValue(ChannelServer::class.java) as ChannelServer
                }
            }
        }
        return channelState
    }

    suspend fun checkNumberChannels():Int{
        var numberChannels:Int = 0
        withContext(Dispatchers.IO) {
            val user = FirebaseAuth.getInstance().currentUser
            user?.let {

                val db = FirebaseDatabase.getInstance().reference
                db.keepSynced(true)

                val state = db.child("users/${user.uid}/channels/numberChannels").get().await()

                state.let { dato->
                    numberChannels = dato.value as Int
                }
            }
        }
        return numberChannels
    }

    suspend fun turnChannel(channelPin: Int, stateChannel:Boolean){
        withContext(Dispatchers.IO) {
            val user = FirebaseAuth.getInstance().currentUser

            user?.let {
                var db = FirebaseDatabase.getInstance()
                    .getReference("users/${user.uid}/channels/channel${channelPin}/estado")
                db.setValue(stateChannel).await()
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