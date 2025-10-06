package edu.ucne.erick_estrada_ap2_p1.domain

import app.cash.turbine.test
import edu.ucne.erick_estrada_ap2_p1.domain.model.Huacal
import edu.ucne.erick_estrada_ap2_p1.domain.repository.HuacalRepository
import edu.ucne.erick_estrada_ap2_p1.domain.useCases.obtenerHuacalesUseCase
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class obtenerHuacalesUseCaseTest {
    private lateinit var repository: HuacalRepository
    private lateinit var useCase: obtenerHuacalesUseCase

    @Before
    fun setup() {
        repository = mockk(relaxed = true)
        useCase = obtenerHuacalesUseCase(repository)
    }

    @Test
    fun `invoke returns all huacales from repository`() = runTest {
        val huacales = listOf(
            Huacal(
                idEntrada = 1,
                fecha = "2025-10-01",
                nombreCliente = "",
                cantidad = 5,
                precio = 100f
             ),
            Huacal(
                idEntrada = 1,
                fecha = "2025-10-01",
                nombreCliente = "",
                cantidad = 5,
                precio = 100f
             )
        )
        val flow: Flow<List<Huacal>> = flowOf(huacales)

        coEvery { repository.getAllFlow() } returns flow

        useCase().test {
            val emitted = awaitItem()
            assert(emitted == huacales)
            awaitComplete()
        }
        verify { repository.getAllFlow() }
    }
}