package edu.ucne.erick_estrada_ap2_p1.domain.useCases

import edu.ucne.erick_estrada_ap2_p1.domain.model.Huacal
import edu.ucne.erick_estrada_ap2_p1.domain.repository.HuacalRepository

class eliminarHuacalUseCase(
    private val repository: HuacalRepository
) {
    suspend operator fun invoke(huacal: Huacal) {
        repository.delete(huacal)
    }
}