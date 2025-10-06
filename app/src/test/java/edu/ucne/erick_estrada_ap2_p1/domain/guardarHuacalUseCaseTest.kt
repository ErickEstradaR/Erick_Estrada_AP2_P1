package edu.ucne.erick_estrada_ap2_p1.domain

import edu.ucne.erick_estrada_ap2_p1.domain.model.Huacal
import edu.ucne.erick_estrada_ap2_p1.domain.repository.HuacalRepository
import edu.ucne.erick_estrada_ap2_p1.domain.useCases.guardarHuacalUseCase
import edu.ucne.erick_estrada_ap2_p1.domain.useCases.validarHuacalUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class guardarHuacalUseCaseTest {
    private lateinit var repository: HuacalRepository
    private lateinit var validarHuacal: validarHuacalUseCase
    private lateinit var useCase: guardarHuacalUseCase

    @Before
    fun setup() {
        repository = mockk(relaxed = true)
        validarHuacal = mockk()
        useCase = guardarHuacalUseCase(repository, validarHuacal)
    }

    @Test
    fun `invoke saves huacal when validation succeeds`() = runTest {
        val huacal = Huacal(
            idEntrada = 1,
            fecha = "2025-10-01",
            nombreCliente = "Erick",
            cantidad = 5,
            precio = 100f
        )
        coEvery { validarHuacal(huacal) } returns Result.success(Unit)
        coEvery { repository.save(huacal) } returns true 
        val result = useCase(huacal)
        assertTrue(result.isSuccess)
        assertEquals(true, result.getOrNull())

        coVerify { validarHuacal(huacal) }
        coVerify { repository.save(huacal) }
    }

    @Test
    fun `invoke returns failure when validation fails`() = runTest {
        val huacal = Huacal(
            idEntrada = 2,
            fecha = "2025-10-01",
            nombreCliente = "",
            cantidad = 5,
            precio = 100f
        )

        val exception = IllegalArgumentException("Nombre no puede estar vacío")
        coEvery { validarHuacal(huacal) } returns Result.failure(exception)

        val result = useCase(huacal)

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())

        coVerify { validarHuacal(huacal) }
        coVerify(exactly = 0) { repository.save(huacal) }
    }
}