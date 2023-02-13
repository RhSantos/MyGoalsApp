package com.rh.mygoalsapp.data.models

import com.rh.mygoalsapp.domain.models.Address


data class AddressServiceDto(
    val bairro: String,
    val cep: String,
    val complemento: String,
    val ddd: String,
    val gia: String,
    val ibge: String,
    val localidade: String,
    val logradouro: String,
    val siafi: String,
    val uf: String
)

fun AddressServiceDto.toAddress() : Address {
    return with(this) {
        Address(
            post_code = cep,
            street = logradouro,
            number = -1,
            complement = complemento,
            district = bairro,
            city = localidade
        )
    }
}