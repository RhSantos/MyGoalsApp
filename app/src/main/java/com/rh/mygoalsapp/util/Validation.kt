package com.rh.mygoalsapp.util

import android.util.Patterns
import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.input.TextFieldValue
import com.rh.mygoalsapp.domain.models.Address
import com.rh.mygoalsapp.domain.models.User


sealed class Validation {
    data class Sucess(val message: String, val data: Any?) : Validation()
    data class Failed(val message: String) : Validation()
}

fun validateAdressTextFieldsForApiCall (inputs : Map<MutableState<TextFieldValue>,String>) : List<String> {
    val list = mutableListOf<String>()
    inputs.forEach {
        if (it.key.value.text.isEmpty()) list.add(it.value)
    }

    return list
}

fun validateAddressInputs(address: Address, numberValue: String) : Validation {
    numberValue.let {
        if (it.isEmpty()) return Validation.Failed("O campo Número não pode ser vazio!")
    }
    address.apply {
        post_code.let {
            if (it.isEmpty()) return Validation.Failed("O campo CEP não pode ser vazio!")
            if (it.split("-")[0].length != 5 || it.split("-")[1].length != 3) return Validation.Failed("Campo CEP inválido!")
        }
        street.let {
            if (it.isEmpty()) return Validation.Failed("O campo Logradouro não pode ser vazio!")
        }
        district.let {
            if (it.isEmpty()) return Validation.Failed("O campo Logradouro não pode ser vazio!")
        }
        city.let {
            if (it.isEmpty()) return Validation.Failed("O campo Logradouro não pode ser vazio!")
        }
    }
    return Validation.Sucess("",null)
}

fun validateUserInputs(type: Authentication, user: User): Validation {
    user.apply {
        nome.let {
            nome =
                it.lowercase().split(" ").joinToString(" ") { it.replaceFirstChar { char -> char.uppercase() } }
            if (it.isEmpty() && type == Authentication.Register) return Validation.Failed("O nome não pode ser vazio")
            if (!Regex("^[a-zA-Z ]+\$").matches(it) && type == Authentication.Register) return Validation.Failed(
                "O nome contém caracteres inválidos!"
            )
        }

        senha.let {
            senha = it.trim()
            if (it.isEmpty()) return Validation.Failed("A senha não pode ser vazia")
            if (it.length < 8) return Validation.Failed("A senha deve ter pelo menos 8 caracteres!")
        }

        email.let {
            email = it.trim()
            if (it.isEmpty()) return Validation.Failed("O email não pode ser vazio")
            if (!Patterns.EMAIL_ADDRESS.matcher(it)
                    .matches()
            ) return Validation.Failed("Email inválido")
        }
    }

    return Validation.Sucess("",null)
}

