package edu.ucne.erick_estrada_ap2_p1.domain.repository

import edu.ucne.erick_estrada_ap2_p1.domain.model.Huacal
import kotlinx.coroutines.flow.Flow

 interface HuacalRepository
    {
    suspend fun save(huacal: Huacal): Boolean
    suspend fun find(id: Int): Huacal?
    suspend fun delete(huacal: Huacal)
    suspend fun getAll(): List<Huacal>
    fun getAllFlow(): Flow<List<Huacal>>
}