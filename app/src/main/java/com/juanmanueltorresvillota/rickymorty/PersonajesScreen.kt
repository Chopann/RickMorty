package com.juanmanueltorresvillota.rickymorty

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun PersonajesScreen(
    onPersonajeClick: (Int) -> Unit,
    viewModel: PersonajesViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Button(onClick = viewModel::cargar) {
            Text("Recargar")
        }

        Spacer(Modifier.height(8.dp))

        when (val s = state) {
            is UiState.Loading -> CircularProgressIndicator()

            is UiState.Success -> LazyColumn {
                items (s.items, key = { it.id }) { p ->
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .clickable{ onPersonajeClick(p.id) }
                            .padding(vertical = 12.dp)
                    ) {
                        Text(p.name, style = MaterialTheme.typography.titleMedium)
                        Text(p.status)
                    }
                    HorizontalDivider()
                }
            }

            is UiState.Error -> Column {
                Text(s.message)
                Button(onClick = viewModel::cargar) {
                    Text("Reintentar")
                }
            }
        }
    }
}