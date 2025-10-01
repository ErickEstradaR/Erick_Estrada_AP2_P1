package edu.ucne.erick_estrada_ap2_p1.data.mapper

import edu.ucne.erick_estrada_ap2_p1.data.huacal.HuacalEntity
import edu.ucne.erick_estrada_ap2_p1.domain.model.Huacal

fun HuacalEntity.toDomain() = Huacal(
    IdEntrada = IdEntrada,
    Fecha = Fecha,
    NombreCliente = NombreCliente,
    Cantidad = Cantidad,
    Precio = Precio
)

fun Huacal.toEntity() = HuacalEntity(
    IdEntrada = IdEntrada,
    Fecha = Fecha,
    NombreCliente = NombreCliente,
    Cantidad = Cantidad,
    Precio = Precio
)

