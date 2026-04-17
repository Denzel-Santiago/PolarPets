package com.polarsoft.polarpets.features.Tienda.domain.model

data class Producto(
    val idProducto: Int,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val idSkin: Int?,
    val idTipoMascota: Int?
)
