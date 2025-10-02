package edu.ucne.erick_estrada_ap2_p1.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.erick_estrada_ap2_p1.domain.model.Huacal
import edu.ucne.erick_estrada_ap2_p1.domain.useCases.huacalesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HuacalViewModel @Inject constructor(
    private val useCases: huacalesUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(HuacalUiState.default())
    val uiState = _uiState.asStateFlow()

    init {
        getHuacales()
    }

    fun onEvent(event: HuacalEvent) {
        when (event) {
            is HuacalEvent.huacalChange -> onHuacalChange(event.IdEntrada)
            is HuacalEvent.nombreClienteChange -> onNombreChange(event.nombreCliente)
            is HuacalEvent.cantidadChange -> onCantidadChange(event.Cantidad)
            is HuacalEvent.precioChange-> onPrecioChange(event.Precio)
            is HuacalEvent.fechaChange -> onFechaChange(event.fecha)

            HuacalEvent.delete -> eliminar()
            HuacalEvent.new -> nuevo()
            HuacalEvent.save -> viewModelScope.launch { saveHuacal() }
        }
    }

    private fun nuevo() {
        _uiState.value = HuacalUiState.default()
    }

    private fun getHuacales() {
        viewModelScope.launch {
            try {
                useCases.obtenerHuacales().collectLatest { huacales ->
                    _uiState.update { it.copy(huacales = huacales) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = e.message ?: "Error desconocido") }
            }
        }
    }

    fun findHuacal(IdEntrada: Int) {
        viewModelScope.launch {
            if (IdEntrada > 0) {
                try {
                    val huacal = useCases.obtenerHuacal(IdEntrada)
                    if (huacal != null) {
                        _uiState.update {
                            it.copy(
                                IdEntrada = _uiState.value.IdEntrada,
                                NombreCliente = _uiState.value.NombreCliente,
                                Precio = _uiState.value.Precio,
                                Cantidad = _uiState.value.Cantidad
                            )
                        }
                    }
                } catch (e: Exception) {
                    _uiState.update { it.copy(errorMessage = e.message ?: "Error desconocido") }
                }
            }
        }
    }


    suspend fun saveHuacal(): Boolean {
        val currentState = _uiState.value
        val huacal = Huacal(
            idEntrada = _uiState.value.IdEntrada,
            nombreCliente = _uiState.value.NombreCliente,
            precio = _uiState.value.Precio,
            cantidad = _uiState.value.Cantidad
        )
        return try {
            val result = useCases.guardarHuacal(huacal)

            if (result.isFailure) {
                _uiState.update {
                    it.copy(
                        errorMessage = result.exceptionOrNull()?.message ?: "Error desconocido"
                    )
                }
                false
            } else {
                true
            }
        } catch (e: Exception) {
            _uiState.update { it.copy(errorMessage = e.message ?: "Error desconocido") }
            false
        }
    }


    private fun eliminar() {
        viewModelScope.launch {
            try {
                val huacal = Huacal(
                    idEntrada = _uiState.value.IdEntrada,
                    nombreCliente = _uiState.value.NombreCliente,
                    precio = _uiState.value.Precio,
                    cantidad = _uiState.value.Cantidad
                )
                useCases.eliminarHuacal(huacal)
                getHuacales()
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = e.message ?: "Error desconocido") }
            }
        }
    }


    private fun onHuacalChange(IdEntrada: Int) {
        _uiState.update { it.copy(IdEntrada = IdEntrada) }
    }

    private fun onNombreChange(nombre: String) {
        _uiState.update { it.copy(NombreCliente = nombre) }
    }
    private fun onFechaChange(fecha: String) {
        _uiState.update { it.copy(Fecha = fecha) }
    }

    private fun onPrecioChange(Precio: Float) {
        _uiState.update { it.copy(Precio = Precio) }
    }

    private fun onCantidadChange(cantidad: Int) {
        _uiState.update { it.copy(Cantidad = cantidad) }
    }




    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}
