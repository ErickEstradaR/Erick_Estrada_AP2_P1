package edu.ucne.erick_estrada_ap2_p1.domain

import edu.ucne.erick_estrada_ap2_p1.domain.model.Huacal
import edu.ucne.erick_estrada_ap2_p1.domain.repository.HuacalRepository
import edu.ucne.erick_estrada_ap2_p1.domain.useCases.eliminarHuacalUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class eliminarHuacalUseCaseTest {

    private lateinit var repository: HuacalRepository
    private lateinit var useCase : eliminarHuacalUseCase

    @Before
    fun setup() {
        repository = mockk(relaxed = true)
        useCase = eliminarHuacalUseCase(repository)
    }

    @Test
    fun `calls repository delete with huacal`() = runTest {
        val huacal = Huacal(idEntrada = 10,fecha = "11-2-2025",precio = 0.1.toFloat(),cantidad = 12)

        coEvery { repository.delete(huacal) } just runs

        useCase(huacal)

        coVerify { repository.delete(huacal) }
    }
}
