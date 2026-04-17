package com.polarsoft.polarpets.features.Login.domain.repository

import com.polarsoft.polarpets.features.Login.domain.model.UserSession

interface LoginRepository {
    suspend fun login(email: String, password: String): Result<UserSession>
    suspend fun register(username: String, email: String, password: String): Result<String>
}
