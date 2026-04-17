package com.polarsoft.polarpets.features.Login.data.repository

import com.polarsoft.polarpets.core.network.ApiService
import com.polarsoft.polarpets.features.Login.data.model.LoginRequest
import com.polarsoft.polarpets.features.Login.data.model.RegisterRequest
import com.polarsoft.polarpets.features.Login.domain.model.UserSession
import com.polarsoft.polarpets.features.Login.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : LoginRepository {

    override suspend fun login(email: String, password: String): Result<UserSession> {
        return try {
            val response = apiService.login(LoginRequest(email, password))
            if (response.isSuccessful) {
                val body = response.body()
                if (body?.token != null && body.usuario != null) {
                    Result.success(
                        UserSession(
                            idUsuario = body.usuario.idUsuario ?: 0,
                            username = body.usuario.username ?: "",
                            email = body.usuario.email ?: email,
                            token = body.token,
                            idRol = body.usuario.idRol ?: 2
                        )
                    )
                } else {
                    Result.failure(Exception("Credenciales inválidas"))
                }
            } else {
                when (response.code()) {
                    401 -> Result.failure(Exception("Correo o contraseña incorrectos"))
                    404 -> Result.failure(Exception("Usuario no encontrado"))
                    else -> Result.failure(Exception("Error del servidor: ${response.code()}"))
                }
            }
        } catch (e: Exception) {
            Result.failure(Exception("Sin conexión a internet"))
        }
    }

    override suspend fun register(
        username: String,
        email: String,
        password: String
    ): Result<String> {
        return try {
            val response = apiService.register(
                RegisterRequest(username = username, email = email, password = password)
            )
            if (response.isSuccessful) {
                Result.success("Registro exitoso")
            } else {
                when (response.code()) {
                    409 -> Result.failure(Exception("El correo ya está registrado"))
                    400 -> Result.failure(Exception("Datos inválidos"))
                    else -> Result.failure(Exception("Error al registrar: ${response.code()}"))
                }
            }
        } catch (e: Exception) {
            Result.failure(Exception("Sin conexión a internet"))
        }
    }
}
