package com.polarsoft.polarpets.core.network

import com.polarsoft.polarpets.features.Login.data.model.LoginRequest
import com.polarsoft.polarpets.features.Login.data.model.LoginResponse
import com.polarsoft.polarpets.features.Login.data.model.RegisterRequest
import com.polarsoft.polarpets.features.Login.data.model.RegisterResponse
import com.polarsoft.polarpets.features.Tienda.data.model.ProductoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("usuarios/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @POST("usuarios/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<RegisterResponse>

    @GET("productos/")
    suspend fun getProductos(): Response<List<ProductoResponse>>

    @GET("inventario/exists")
    suspend fun verificarInventario(
        @Query("id_usuario") idUsuario: Int,
        @Query("id_producto") idProducto: Int
    ): Response<Boolean>
}
