package com.juanmanueltorresvillota.rickymorty

data class Personaje (
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val image: String
)

data class PersonajesResponse(val results: List<Personaje>)