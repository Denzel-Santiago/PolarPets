package com.polarsoft.polarpets.core.network

import com.polarsoft.polarpets.features.Login.data.model.LoginRequest
import com.polarsoft.polarpets.features.Login.data.model.LoginResponse
import com.polarsoft.polarpets.features.Login.data.model.RegisterRequest
import com.polarsoft.polarpets.features.Login.data.model.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("usuarios/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @POST("usuarios/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<RegisterResponse>
}
