package edu.ucne.erick_estrada_ap2_p1.data.mapper

import edu.ucne.erick_estrada_ap2_p1.data.huacal.HuacalEntity
import edu.ucne.erick_estrada_ap2_p1.domain.model.Huacal

fun HuacalEntity.toDomain() = Huacal(
    idEntrada = IdEntrada,
    fecha = Fecha,
    nombreCliente = NombreCliente,
    cantidad = Cantidad,
    precio = Precio
)

fun Huacal.toEntity() = HuacalEntity(
    IdEntrada = idEntrada,
    Fecha = fecha,
    NombreCliente = nombreCliente,
    Cantidad = cantidad,
    Precio = precio
)

