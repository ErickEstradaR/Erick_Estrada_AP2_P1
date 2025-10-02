package edu.ucne.erick_estrada_ap2_p1.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch


@Composable
fun HuacalScreen(
    IdEntrada: Int?,
    viewModel: HuacalViewModel = hiltViewModel(),
    goback: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()

    LaunchedEffect(IdEntrada) {
        IdEntrada?.takeIf { it > 0 }?.let {
            viewModel.findHuacal(it)
        }
    }

    BodyScreen(
        uiState = uiState,
        onAction = viewModel::onEvent,
        goback = goback,
        saveHuacal = {
            scope.launch {
                if (viewModel.saveHuacal()) goback()
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BodyScreen(
    uiState: HuacalUiState,
    onAction: (HuacalEvent) -> Unit,
    goback: () -> Unit,
    saveHuacal: suspend () -> Unit
) {
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (uiState.IdEntrada != null && uiState.IdEntrada != 0)
                            "Editar "
                        else
                            "Nuevo"
                    )
                },
                navigationIcon = {
                    IconButton(onClick = goback) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = uiState.NombreCliente,
                onValueChange = { onAction(HuacalEvent.nombreClienteChange(it)) },
                label = { Text("Nombre del Cliente") },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = uiState.Cantidad.toString(),
                onValueChange = {
                    val id = it.toIntOrNull() ?: 0
                    onAction(HuacalEvent.cantidadChange(id))
                },
                label = { Text("Cantidad de Huacales") },
                modifier = Modifier.fillMaxWidth(),
                isError = !uiState.errorMessage.isNullOrEmpty()
            )
            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = uiState.Precio.toString(),
                onValueChange = {
                    val precio = it.toFloatOrNull() ?: 0
                    onAction(HuacalEvent.precioChange(precio.toFloat()))
                },
                label = { Text("Precio del huacal") },
                modifier = Modifier.fillMaxWidth(),
                isError = !uiState.errorMessage.isNullOrEmpty()
            )
            Spacer(Modifier.height(16.dp))




            uiState.errorMessage?.takeIf { it.isNotEmpty() }?.let {
                Spacer(Modifier.height(8.dp))
                Text(text = it, color = MaterialTheme.colorScheme.error)
            }

            Spacer(Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                OutlinedButton(onClick = { onAction(HuacalEvent.new) }) {
                    Text("Limpiar")
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(Icons.Default.Refresh, "Limpiar")
                }

                Spacer(modifier = Modifier.width(16.dp))

                OutlinedButton(onClick = { scope.launch { saveHuacal() } }) {
                    Text("Guardar")
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(Icons.Default.Edit, "Guardar")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    BodyScreen(
        uiState = HuacalUiState(
            IdEntrada = 1,
            Fecha = "11-2-2025",
            Precio = 0.1.toFloat(),
            Cantidad = 12,
            NombreCliente = "Erick",
            ),
        onAction = {},
        goback = {},
        saveHuacal = {}
    )
}