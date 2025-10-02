package edu.ucne.erick_estrada_ap2_p1.domain.useCases

import edu.ucne.erick_estrada_ap2_p1.domain.model.Huacal
import edu.ucne.erick_estrada_ap2_p1.domain.repository.HuacalRepository

class validarHuacalUseCase(
    private val repository: HuacalRepository
) {
    suspend operator fun invoke(huacal: Huacal): Result<Unit> {

        if (huacal.NombreCliente.isBlank() || huacal.Cantidad <= 0 || huacal.Precio <= 0) {
            return Result.failure(Exception("Nombre esta vacio"))
        }

        return Result.success(Unit)
    }
}