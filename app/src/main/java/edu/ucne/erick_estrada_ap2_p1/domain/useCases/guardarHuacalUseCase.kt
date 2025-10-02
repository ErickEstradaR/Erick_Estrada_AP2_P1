package edu.ucne.erick_estrada_ap2_p1.domain.useCases

import edu.ucne.erick_estrada_ap2_p1.domain.model.Huacal
import edu.ucne.erick_estrada_ap2_p1.domain.repository.HuacalRepository

class guardarHuacalUseCase (
    private val repository: HuacalRepository,
    private val validarHuacal: validarHuacalUseCase
) {
    suspend operator fun invoke(huacal: Huacal): Result<Boolean> {

        val validacion = validarHuacal(huacal)
        if (validacion.isFailure) return Result.failure(validacion.exceptionOrNull()!!)

        val result = repository.save(huacal)
        return Result.success(result)
    }
}