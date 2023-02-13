package com.rh.mygoalsapp.data.repository

import com.rh.mygoalsapp.domain.models.Address

interface AddressRepository {

    suspend fun getAddressById(id: Long): Address
    suspend fun update(address: Address)
    suspend fun delete(address: Address)

}