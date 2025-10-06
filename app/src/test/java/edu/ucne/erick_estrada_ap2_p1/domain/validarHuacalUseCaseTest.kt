package edu.ucne.erick_estrada_ap2_p1.domain

import edu.ucne.erick_estrada_ap2_p1.domain.model.Huacal
import edu.ucne.erick_estrada_ap2_p1.domain.repository.HuacalRepository
import edu.ucne.erick_estrada_ap2_p1.domain.useCases.validarHuacalUseCase
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class validarHuacalUseCaseTest {
    private lateinit var repository: HuacalRepository
    private lateinit var useCase: validarHuacalUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = validarHuacalUseCase(repository)
    }

    @Test
    fun `validation fails when nombreCliente is empty`() = runTest {
        val huacal = Huacal(
            idEntrada = 1,
            fecha = "2025-10-01",
            nombreCliente = "",
            cantidad = 5,
            precio = 100f
        )

        val result = useCase(huacal)

        assertTrue(result.isFailure)
        assertEquals("Nombre esta vacio", result.exceptionOrNull()?.message)
    }

    @Test
    fun `validation fails when cantidad is zero`() = runTest {
        val huacal = Huacal(
            idEntrada = 2,
            fecha = "2025-10-01",
            nombreCliente = "Cliente válido",
            cantidad = 0,
            precio = 100f
        )

        val result = useCase(huacal)

        assertTrue(result.isFailure)
        assertEquals("La cantidad es incorrecta", result.exceptionOrNull()?.message)
    }

    @Test
    fun `validation fails when precio is zero`() = runTest {
        val huacal = Huacal(
            idEntrada = 3,
            fecha = "2025-10-01",
            nombreCliente = "Cliente válido",
            cantidad = 10,
            precio = 0f
        )

        val result = useCase(huacal)

        assertTrue(result.isFailure)
        assertEquals("El Precio es invalido", result.exceptionOrNull()?.message)
    }

    @Test
    fun `validation succeeds for valid huacal`() = runTest {
        val huacal = Huacal(
            idEntrada = 4,
            fecha = "2025-10-01",
            nombreCliente = "Cliente válido",
            cantidad = 10,
            precio = 250f
        )

        val result = useCase(huacal)

        assertTrue(result.isSuccess)
    }
}


