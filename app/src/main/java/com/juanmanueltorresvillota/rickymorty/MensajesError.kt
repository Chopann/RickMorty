package com.juanmanueltorresvillota.rickymorty

import retrofit2.HttpException
import java.io.IOException

fun mensajeDeError(e: Throwable): String = when (e) {
    is HttpException -> "El servidor respondió con un error (código ${e.code()}). Intenta más tarde."
    is IOException -> "No hay conexión a internet. Revisa tu red y reintenta."
    else -> "Ocurrió un error inesperado. Reintenta."
}