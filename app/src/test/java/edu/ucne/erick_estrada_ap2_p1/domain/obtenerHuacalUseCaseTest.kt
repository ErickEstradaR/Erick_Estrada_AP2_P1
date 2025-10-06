package edu.ucne.erick_estrada_ap2_p1.domain

import edu.ucne.erick_estrada_ap2_p1.domain.model.Huacal
import edu.ucne.erick_estrada_ap2_p1.domain.repository.HuacalRepository
import edu.ucne.erick_estrada_ap2_p1.domain.useCases.obtenerHuacalUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class obtenerHuacalUseCaseTest {
    private lateinit var repository: HuacalRepository
    private lateinit var useCase: obtenerHuacalUseCase

    @Before
    fun setup() {
        repository = mockk(relaxed = true)
        useCase = obtenerHuacalUseCase(repository)
    }

    @Test
    fun `invoke returns huacal when found`() = runTest {
        val huacal = Huacal(
            idEntrada = 1,
            fecha = "2025-10-01",
            nombreCliente = "",
            cantidad = 5,
            precio = 100f
        )
        coEvery { repository.find(1) } returns huacal

        val result = useCase(1)

        assertEquals(huacal, result)
        coVerify { repository.find(1) }
    }

    @Test
    fun `invoke returns null when huacal not found`() = runTest {
        coEvery { repository.find(2) } returns null

        val result = useCase(2)

        assertNull(result)
        coVerify { repository.find(2) }
    }
}