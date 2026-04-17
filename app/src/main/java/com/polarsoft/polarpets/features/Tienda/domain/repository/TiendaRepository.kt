package com.polarsoft.polarpets.features.Tienda.domain.repository

import com.polarsoft.polarpets.features.Tienda.domain.model.Producto

interface TiendaRepository {
    suspend fun getProductos(): Result<List<Producto>>
    suspend fun verificarInventario(idUsuario: Int, idProducto: Int): Result<Boolean>
}
