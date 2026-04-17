package com.polarsoft.polarpets.features.Login.data.model

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("id_usuario") val idUsuario: Int?,
    @SerializedName("username") val username: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("message") val message: String?
)
