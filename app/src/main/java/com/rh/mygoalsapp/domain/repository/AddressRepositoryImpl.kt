package com.rh.mygoalsapp.domain.repository

import com.rh.mygoalsapp.data.db.dao.AddressDao
import com.rh.mygoalsapp.data.models.toAddress
import com.rh.mygoalsapp.data.models.toAddressEntity
import com.rh.mygoalsapp.data.repository.AddressRepository
import com.rh.mygoalsapp.domain.models.Address

class AddressRepositoryImpl(
    private val addressDao: AddressDao
) : AddressRepository {

    override suspend fun getAddressById(id: Long): Address {
        return addressDao.getAddressById(id).toAddress()
    }

    override suspend fun update(address: Address) {
        val addressEntity = address.toAddressEntity()
        return addressDao.update(addressEntity)
    }

    override suspend fun delete(address: Address) {
        val addressEntity = address.toAddressEntity()
        addressDao.delete(addressEntity)
    }

}

