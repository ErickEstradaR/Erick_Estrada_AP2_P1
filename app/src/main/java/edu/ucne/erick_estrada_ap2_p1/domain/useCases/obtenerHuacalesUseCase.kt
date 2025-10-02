package edu.ucne.erick_estrada_ap2_p1.domain.useCases

import edu.ucne.erick_estrada_ap2_p1.domain.model.Huacal
import edu.ucne.erick_estrada_ap2_p1.domain.repository.HuacalRepository
import kotlinx.coroutines.flow.Flow

class obtenerHuacalesUseCase(
    private val repository: HuacalRepository
) {
    operator fun invoke(): Flow<List<Huacal>> {
        return repository.getAllFlow()
    }
}