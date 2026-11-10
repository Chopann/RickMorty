package com.juanmanueltorresvillota.rickymorty

import android.os.Message
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface DetalleUiState {
    data object  Loading : DetalleUiState
    data class Success(val personaje: Personaje) : DetalleUiState
    data class Error(val message: String) : DetalleUiState
}

class DetalleViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val repository = PersonajesRepository(RetrofitInstance.api)

    private val id: Int = checkNotNull(savedStateHandle["id"])

    private val _uiState = MutableStateFlow<DetalleUiState>(DetalleUiState.Loading)
    val uiState: StateFlow<DetalleUiState> = _uiState.asStateFlow()

    init {
        cargar()
    }

    fun cargar() {
        viewModelScope.launch {
            _uiState.value = DetalleUiState.Loading
            _uiState.value = repository.obtenerPersonaje(id).fold(
                onSuccess = { DetalleUiState.Success(it) },
                onFailure = { DetalleUiState.Error(mensajeDeError(it)) }
            )
        }
    }
}