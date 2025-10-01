package edu.ucne.erick_estrada_ap2_p1.data.huacal

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Huacales")

data class HuacalEntity(
    @PrimaryKey
    val IdEntrada: Int? = null,
    val Fecha : String = "",
    val NombreCliente: String = "",
    val Cantidad: Int = 0,
    val Precio: Float
)