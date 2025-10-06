package edu.ucne.erick_estrada_ap2_p1.data

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import edu.ucne.erick_estrada_ap2_p1.data.huacal.HuacalDao
import edu.ucne.erick_estrada_ap2_p1.data.huacal.HuacalEntity
import edu.ucne.erick_estrada_ap2_p1.data.huacal.HuacalRepositoryImpl
import edu.ucne.erick_estrada_ap2_p1.domain.model.Huacal
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HuacalRepositoryTest {

    private lateinit var dao: HuacalDao
    private lateinit var repository: HuacalRepositoryImpl

    @Before
    fun setUp() {
        dao = mockk(relaxed = true)
        repository = HuacalRepositoryImpl(dao)
    }

    @Test
    fun getAllFlow_emitsMappedDomainModels() = runTest {
        val shared = MutableSharedFlow<List<HuacalEntity>>()
        every { dao.getAll() } returns shared

        val job = launch {
            repository.getAllFlow().test {

                shared.emit(listOf(HuacalEntity(IdEntrada = 1,Fecha = "11-2-2025",Precio = 0.1.toFloat(),Cantidad = 12)))
                val first = awaitItem()
                assertThat(first).containsExactly(Huacal(idEntrada = 1,fecha = "11-2-2025",precio = 0.1.toFloat(),cantidad = 12)
                )

                shared.emit(
                    listOf(
                        HuacalEntity(IdEntrada = 2,Fecha = "11-2-2025",Precio = 0.1.toFloat(),Cantidad = 12),
                        HuacalEntity(IdEntrada = 3,Fecha = "11-2-2025",Precio = 0.1.toFloat(),Cantidad = 12)
                    )
                )

                val second = awaitItem()
                assertThat(second).containsExactly(
                    Huacal(idEntrada = 2,fecha = "11-2-2025",precio = 0.1.toFloat(),cantidad = 12),
                    Huacal(idEntrada = 3,fecha = "11-2-2025",precio = 0.1.toFloat(),cantidad = 12)
                )

                cancelAndIgnoreRemainingEvents()
            }
        }
        job.join()
    }

    @Test
    fun find_returnsMappedDomainModel_whenEntityExists() = runTest {
        coEvery { dao.find(5) } returns HuacalEntity(IdEntrada = 5,Fecha = "11-2-2025",Precio = 0.1.toFloat(),Cantidad = 12)

        val result = repository.find(5)

        assertThat(result).isEqualTo(Huacal(idEntrada = 5,fecha = "11-2-2025",precio = 0.1.toFloat(),cantidad = 12))
    }

    @Test
    fun find_returnsNull_whenEntityMissing() = runTest {
        coEvery { dao.find(42) } returns null

        val result = repository.find(42)

        assertThat(result).isNull()
    }

    @Test
    fun save_callsDaoInsert_andReturnsTrueWhenSuccess() = runTest {
        coEvery { dao.save(any()) } returns Unit
        val huacal = Huacal(idEntrada = 10,fecha = "11-2-2025",precio = 0.1.toFloat(),cantidad = 12)

        val success = repository.save(huacal)

        assertThat(success).isTrue()
        coVerify { dao.save(HuacalEntity(IdEntrada = 10,Fecha = "11-2-2025",Precio = 0.1.toFloat(),Cantidad = 12)) }
    }

    @Test
    fun delete_callsDaoDelete() = runTest {
        coEvery { dao.delete(any()) } just runs
        val huacal = Huacal(idEntrada = 11,fecha = "11-2-2025",precio = 0.1.toFloat(),cantidad = 12)

        repository.delete(huacal)

        coVerify { dao.delete(HuacalEntity(IdEntrada = 11,Fecha = "11-2-2025",Precio = 0.1.toFloat(),Cantidad = 12)) }
    }

    @Test
    fun getAll_returnsMappedDomainModels() = runTest {
        coEvery { dao.getAll() } returns flowOf(
            listOf(
                HuacalEntity(IdEntrada = 2,Fecha = "11-2-2025",Precio = 0.1.toFloat(),Cantidad = 12),
                HuacalEntity(IdEntrada = 3,Fecha = "11-2-2025",Precio = 0.1.toFloat(),Cantidad = 12)
            )
        )
        val result = repository.getAllFlow().first()
        assertThat(result).isEqualTo(
            listOf(
                Huacal(idEntrada = 2,fecha = "11-2-2025",precio = 0.1.toFloat(),cantidad = 12),
                Huacal(idEntrada = 3,fecha = "11-2-2025",precio = 0.1.toFloat(),cantidad = 12)
            )
        )
    }
}