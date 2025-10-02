package edu.ucne.erick_estrada_ap2_p1.presentation

sealed interface HuacalEvent {
    data class huacalChange(val IdEntrada: Int) : HuacalEvent
    data class fechaChange(val fecha: String) : HuacalEvent
    data class nombreClienteChange(val nombreCliente: String) : HuacalEvent
    data class cantidadChange(val Cantidad: Int) : HuacalEvent
    data class precioChange(val Precio:Float): HuacalEvent

    data object save : HuacalEvent
    data object delete : HuacalEvent
    data object new : HuacalEvent
}

