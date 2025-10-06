package edu.ucne.erick_estrada_ap2_p1.presentation

import app.cash.turbine.test
import edu.ucne.erick_estrada_ap2_p1.domain.model.Huacal
import edu.ucne.erick_estrada_ap2_p1.domain.useCases.huacalesUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class HuacalViewModelTest {

    private lateinit var fakeUseCases: huacalesUseCase
    private lateinit var viewModel: HuacalViewModel

    @Before
    fun setup() {

        fakeUseCases = mockk(relaxed = true)


        coEvery { fakeUseCases.obtenerHuacales() } returns flowOf(
            listOf(
                Huacal(idEntrada = 1, fecha = "2025-10-01", nombreCliente = "Prueba", cantidad = 2, precio = 100f)
            )
        )

        coEvery { fakeUseCases.validarHuacal(any()) } returns Result.success(Unit)
        coEvery { fakeUseCases.guardarHuacal(any()) } returns Result.success(true)

        viewModel = HuacalViewModel(fakeUseCases)
    }

    @Test
    fun `saveHuacal should update state when validation fails`() = runTest {
        coEvery { fakeUseCases.validarHuacal(any()) } returns Result.failure(Exception("Nombre esta vacio"))

        viewModel.onEvent(HuacalEvent.nombreClienteChange(""))

        val result = viewModel.saveHuacal()

        assertTrue(!result)
        assertEquals("Nombre esta vacio", viewModel.uiState.value.errorMessage)
    }

    @Test
    fun `saveHuacal should succeed with valid huacal`() = runTest {
        viewModel.onEvent(HuacalEvent.nombreClienteChange("Cliente válido"))
        viewModel.onEvent(HuacalEvent.precioChange(200f))
        viewModel.onEvent(HuacalEvent.cantidadChange(3))

        val result = viewModel.saveHuacal()

        assertTrue(result)
        assertEquals(null, viewModel.uiState.value.errorMessage)
    }

    @Test
    fun `getHuacales should emit huacales list`() = runTest {
        viewModel.uiState.test {
            val state = awaitItem()
            assertTrue(state.huacales.isNotEmpty())
            assertEquals("Prueba", state.huacales.first().nombreCliente)
        }
    }
}