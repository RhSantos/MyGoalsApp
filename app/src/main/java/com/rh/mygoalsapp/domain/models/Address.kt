package com.rh.mygoalsapp.domain.models

data class Address(
    val post_code: String,
    val street: String,
    var number: Int = 0,
    val complement: String,
    val district: String,
    val city: String
) {
    companion object {
        val emptyAddress = Address(
            post_code = "",
            street = "",
            complement = "",
            district = "",
            city = ""
        )
    }
}