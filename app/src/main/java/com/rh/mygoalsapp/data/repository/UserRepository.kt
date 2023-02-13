package com.rh.mygoalsapp.data.repository

import com.rh.mygoalsapp.domain.models.User

interface UserRepository {

    suspend fun login(email: String, senha: String) : User?
    suspend fun getIdByCredentials(email: String, senha: String) : Long
    suspend fun userExists(email: String): Boolean
    suspend fun register(user: User) : User
    suspend fun update(user: User)
    suspend fun delete(user: User)
}