package com.polarsoft.polarpets.features.Login.data.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("token") val token: String?,
    @SerializedName("usuario") val usuario: UsuarioResponse?,
    @SerializedName("message") val message: String?
)

data class UsuarioResponse(
    @SerializedName("id_usuario") val idUsuario: Int?,
    @SerializedName("username") val username: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("id_rol") val idRol: Int?
)
