package edu.ucne.erick_estrada_ap2_p1.data.huacal

import edu.ucne.erick_estrada_ap2_p1.data.mapper.toDomain
import edu.ucne.erick_estrada_ap2_p1.data.mapper.toEntity
import edu.ucne.erick_estrada_ap2_p1.domain.model.Huacal
import edu.ucne.erick_estrada_ap2_p1.domain.repository.HuacalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HuacalRepositoryImpl @Inject constructor(
    private val dao: HuacalDao
) : HuacalRepository {

    override suspend fun save(huacal: Huacal): Boolean {
        dao.save(huacal.toEntity())
        return true
    }

    override suspend fun find(id: Int): Huacal? =
        dao.find(id)?.toDomain()

    override suspend fun delete(huacal: Huacal) {
        dao.delete(huacal.toEntity())
    }

    override suspend fun getAll(): List<Huacal> =
        dao.getAll().firstOrNull()?.map { it.toDomain() } ?: emptyList()

    override fun getAllFlow(): Flow<List<Huacal>> =
        dao.getAll().map { entities -> entities.map(HuacalEntity::toDomain) }
}
