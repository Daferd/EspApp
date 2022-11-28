package com.daferarevalo.espapp.data.model

data class Temporizador2Server(
        val activar: Boolean = false,
        val estado: Boolean = false,
        val h_off_rele2: Int = 0,
        val h_on_rele2: Int = 0,
        val m_off_rele2: Int = 0,
        val m_on_rele2: Int = 0
)