package edu.ucne.erick_estrada_ap2_p1.domain.useCases

import edu.ucne.erick_estrada_ap2_p1.domain.model.Huacal
import edu.ucne.erick_estrada_ap2_p1.domain.repository.HuacalRepository
import java.time.LocalDateTime

class guardarHuacalUseCase(
    private val repository: HuacalRepository,
    private val validarHuacal: validarHuacalUseCase
) {
    suspend operator fun invoke(huacal: Huacal): Result<Boolean> {
        val validacion = validarHuacal(huacal)
        if (validacion.isFailure) return Result.failure(validacion.exceptionOrNull()!!)

        val huacalConFecha = if (huacal.fecha == null) {
            huacal.copy(fecha = LocalDateTime.now().toString())
        } else {
            huacal
        }

        val result = repository.save(huacalConFecha)
        return Result.success(result)
    }
}