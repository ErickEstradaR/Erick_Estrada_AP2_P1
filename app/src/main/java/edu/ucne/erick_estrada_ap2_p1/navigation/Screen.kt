package edu.ucne.erick_estrada_ap2_p1.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen{
    @Serializable
    data class Huacal(val Id: Int?): Screen()

    @Serializable
    data object List: Screen()
}