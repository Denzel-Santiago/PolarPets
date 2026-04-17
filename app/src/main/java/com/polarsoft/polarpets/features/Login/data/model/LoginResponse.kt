package com.polarsoft.polarpets.features.Login.data.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("token") val token: String?,
    @SerializedName("id_usuario") val idUsuario: Int?,
    @SerializedName("username") val username: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("id_rol") val idRol: Int?,
    @SerializedName("message") val message: String?
)
