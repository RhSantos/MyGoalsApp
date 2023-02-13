package com.rh.mygoalsapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.rh.mygoalsapp.data.repository.UserRepository
import com.rh.mygoalsapp.domain.models.User
import com.rh.mygoalsapp.util.Authentication
import com.rh.mygoalsapp.util.Validation
import com.rh.mygoalsapp.util.validateUserInputs
import kotlinx.coroutines.*

class UserViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    suspend fun getIdByCredentials(email: String, senha: String): Deferred<Validation> = viewModelScope.async {
        val id = userRepository.getIdByCredentials(email,senha)
        Validation.Sucess("",id)
    }

    suspend fun validateCredentials(
        user: User,
        type: Authentication
    ): Validation {

        validateUserInputs(type, user).let {
            when (it) {
                is Validation.Failed -> return it
                is Validation.Sucess -> return@let
                else -> return@let
            }
        }

        return when (type) {
            Authentication.Register -> {
                if (userRepository.userExists(user.email)) return Validation.Failed("Usuário já existente!")
                userRepository.register(user)
                return Validation.Sucess("Usuário criado com sucesso!", user)
            }
            Authentication.Login -> {
                val user = userRepository.login(user.email, user.senha) ?: return Validation.Failed(
                    "Erro durante o Login: Credenciais inválidas"
                )
                return Validation.Sucess("Usuário logado com sucesso!", user)
            }
        }
    }

    class UserViewModelFactory(private val userRepository: UserRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return UserViewModel(userRepository) as T
        }
    }
}

