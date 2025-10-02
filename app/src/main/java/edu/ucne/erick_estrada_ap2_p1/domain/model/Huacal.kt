package edu.ucne.erick_estrada_ap2_p1.domain.model

data class Huacal(

    val idEntrada: Int? = null,
    val fecha : String = "",
    val nombreCliente: String = "",
    val cantidad: Int = 0,
    val precio: Float
)