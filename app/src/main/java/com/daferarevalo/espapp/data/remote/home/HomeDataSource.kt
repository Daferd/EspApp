package com.daferarevalo.espapp.data.remote.home

import com.daferarevalo.espapp.data.model.ChannelServer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class HomeDataSource{

    suspend fun readSensor(sensorPin: Int): Double {
        var value = 0.0
        withContext(Dispatchers.IO) {
            val user = FirebaseAuth.getInstance().currentUser

            user?.let {
                val database = FirebaseDatabase.getInstance().reference
                database.keepSynced(true)

                val auxValue =
                    database.child("users/${user.uid}/sensors/sensor${sensorPin}").get().await()

                auxValue.let { dato ->
                    value = dato.value as Double
                }
            }
        }

        return value
    }

    suspend fun addChannel(channelPin: Int) {
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

                val state =
                    database.child("users/${user.uid}/channels/channel${channelPin}").get().await()

                state.let { dato ->
                    channelState = dato.getValue(ChannelServer::class.java) as ChannelServer
                }
            }
        }
        return channelState
    }

    suspend fun checkNumberChannels(): Long {
        var numberChannels: Long = 0
        withContext(Dispatchers.IO) {
            val user = FirebaseAuth.getInstance().currentUser
            user?.let {

                val db = FirebaseDatabase.getInstance().reference
                db.keepSynced(true)

                val state = db.child("users/${user.uid}/channels/numberChannels").get().await()

                state.let { dato ->
                    numberChannels = dato.value as Long
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

    suspend fun applyChangesChannel(
        channelPin: Int,
        active: Boolean,
        state: Boolean,
        h_off: Int,
        h_on: Int,
        m_off: Int,
        m_on: Int
    ) {
        withContext(Dispatchers.IO) {
            val user = FirebaseAuth.getInstance().currentUser

            user?.let {
                var db = FirebaseDatabase.getInstance()
                    .getReference("users/${user.uid}/channels/channel${channelPin}")
                val channel = ChannelServer(active, state, h_off, h_on, m_off, m_on)
                db.setValue(channel).await()
            }
        }
    }

}