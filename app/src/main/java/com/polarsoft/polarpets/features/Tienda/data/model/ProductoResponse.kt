package com.polarsoft.polarpets.features.Tienda.data.model

import com.google.gson.annotations.SerializedName

data class ProductoResponse(
    @SerializedName("id_producto") val idProducto: Int,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("descripcion") val descripcion: String,
    @SerializedName("tipo") val tipo: String,
    @SerializedName("precio") val precio: Double,
    @SerializedName("id_skin") val idSkin: Int?,
    @SerializedName("id_tipo_mascota") val idTipoMascota: Int?
)
