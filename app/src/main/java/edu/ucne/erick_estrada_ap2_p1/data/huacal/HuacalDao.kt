package edu.ucne.erick_estrada_ap2_p1.data.huacal

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface HuacalDao{
    @Upsert
    suspend fun save(huacal: HuacalEntity )

    @Query("""
            SELECT *
            FROM Huacales
            WHERE IdEntrada =:id
            Limit 1
    """)
    suspend fun find(id: Int): HuacalEntity?

    @Delete
    suspend fun delete(huacal: HuacalEntity)

    @Query("SELECT * FROM Huacales")
    fun getAll(): Flow<List<HuacalEntity>>
}