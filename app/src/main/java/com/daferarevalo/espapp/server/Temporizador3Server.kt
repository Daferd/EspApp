package com.daferarevalo.espapp.server

data class Temporizador3Server(
    val activar: Boolean = false,
    val estado: Boolean = false,
    val h_off_rele3: Int = 0,
    val h_on_rele3: Int = 0,
    val m_off_rele3: Int = 0,
    val m_on_rele3: Int = 0
)