package com.polarsoft.polarpets.features.Login.data.model

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("username") val username: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("id_rol") val idRol: Int = 2,
    @SerializedName("id_mascota_activa") val idMascotaActiva: Int? = null
)
