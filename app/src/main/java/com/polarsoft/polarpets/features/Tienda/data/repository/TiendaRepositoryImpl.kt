package com.polarsoft.polarpets.features.Tienda.data.repository

import com.polarsoft.polarpets.core.network.ApiService
import com.polarsoft.polarpets.features.Tienda.domain.model.Producto
import com.polarsoft.polarpets.features.Tienda.domain.repository.TiendaRepository
import javax.inject.Inject

class TiendaRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : TiendaRepository {

    override suspend fun getProductos(): Result<List<Producto>> {
        return try {
            val response = apiService.getProductos()
            if (response.isSuccessful) {
                val productos = response.body()?.map {
                    Producto(
                        idProducto = it.idProducto,
                        nombre = it.nombre,
                        descripcion = it.descripcion,
                        precio = it.precio,
                        idSkin = it.idSkin,
                        idTipoMascota = it.idTipoMascota
                    )
                } ?: emptyList()
                Result.success(productos)
            } else {
                Result.failure(Exception("Error al cargar productos"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Sin conexión"))
        }
    }

    override suspend fun verificarInventario(idUsuario: Int, idProducto: Int): Result<Boolean> {
        return try {
            val response = apiService.verificarInventario(idUsuario, idProducto)
            if (response.isSuccessful) {
                Result.success(response.body() ?: false)
            } else {
                Result.success(false) // Si no existe, retorna false sin error
            }
        } catch (e: Exception) {
            Result.success(false) // No romper la app si falla
        }
    }
}
