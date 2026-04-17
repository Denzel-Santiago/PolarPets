package com.polarsoft.polarpets.features.Login.domain.model

data class UserSession(
    val idUsuario: Int,
    val username: String,
    val email: String,
    val token: String,
    val idRol: Int
)
