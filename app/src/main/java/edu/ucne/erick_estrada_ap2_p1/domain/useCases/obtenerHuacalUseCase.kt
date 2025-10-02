package edu.ucne.erick_estrada_ap2_p1.domain.useCases

import edu.ucne.erick_estrada_ap2_p1.domain.model.Huacal
import edu.ucne.erick_estrada_ap2_p1.domain.repository.HuacalRepository

class obtenerHuacalUseCase (
    private val repository: HuacalRepository
) {
    suspend operator fun invoke(id: Int): Huacal? {
        return repository.find(id)
    }
}