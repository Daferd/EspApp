package com.daferarevalo.espapp.data.model

data class RiegoServer(
    val activar: Boolean = false,
    val estado: Boolean = false,
    val h_on_rele4: Int = 0,
    val m_on_rele4: Int = 0,
    val repeticiones: Int = 0,
    val tiempoEspera: Int = 0,
    val tiempoRiego: Int = 0,
    val sun: Boolean = false,
    val mon: Boolean = false,
    val tues: Boolean = false,
    val wed: Boolean = false,
    val thurs: Boolean = false,
    val fri: Boolean = false,
    val sat: Boolean = false
)