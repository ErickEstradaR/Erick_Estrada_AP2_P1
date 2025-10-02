package edu.ucne.erick_estrada_ap2_p1.presentation

import edu.ucne.erick_estrada_ap2_p1.domain.model.Huacal


data class HuacalUiState(

    val IdEntrada: Int? = null,
    val Fecha : String = "",
    val NombreCliente: String = "",
    val Cantidad: Int = 0,
    val Precio: Float = 0.toFloat(),

    val errorMessage: String? = null,
    val huacales: List<Huacal> = emptyList()
) {
    companion object {
        fun default() = HuacalUiState()
    }
}

