package com.daferarevalo.espapp.server

data class RiegoServer(
    val activar: Boolean = false,
    val estado: Boolean = false,
    val h_on_rele4: Int = 0,
    val m_on_rele4: Int = 0,
    val repeticiones: Int = 0,
    val tiempo: Int = 0
)