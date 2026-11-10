package com.juanmanueltorresvillota.rickymorty

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun DetalleScreen(
    onVolver: () -> Unit,
    viewModel: DetalleViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Column(Modifier.fillMaxSize().padding(16.dp)) {

        Button(onClick = onVolver) {
            Text("Volver")
        }

        Spacer(Modifier.height(16.dp))

        when (val s = state) {
            is DetalleUiState.Loading -> CircularProgressIndicator()

            is DetalleUiState.Success -> {
                Text(s.personaje.name, style = MaterialTheme.typography.headlineMedium)
                Spacer(Modifier.height(8.dp))
                Text("Especie: ${s.personaje.species}")
                Text("Estado: ${s.personaje.status}")
            }

            is DetalleUiState.Error -> Column {
                Text(s.message)
                Button(onClick = viewModel::cargar) {
                    Text("Reintentar")
                }
            }
        }
    }
}