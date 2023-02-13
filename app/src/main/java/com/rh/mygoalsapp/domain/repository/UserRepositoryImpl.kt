package com.rh.mygoalsapp.domain.repository

import com.rh.mygoalsapp.data.db.dao.AddressDao
import com.rh.mygoalsapp.data.db.dao.UserDao
import com.rh.mygoalsapp.data.models.toAddress
import com.rh.mygoalsapp.data.models.toAddressEntity
import com.rh.mygoalsapp.data.models.toUser
import com.rh.mygoalsapp.data.models.toUserEntity
import com.rh.mygoalsapp.data.repository.UserRepository
import com.rh.mygoalsapp.domain.models.User

class UserRepositoryImpl(
    private val userDao: UserDao,
    private val addressDao: AddressDao
) :
    UserRepository {
    override suspend fun login(email: String, senha: String): User? {
        return with(userDao.login(email, senha)) {
            val address = addressDao.getAddressById(this.id).toAddress()
            toUser(address)
        }
    }

    override suspend fun getIdByCredentials(email: String, senha: String): Long {
        return userDao.getIdByCredentials(email, senha)
    }

    override suspend fun userExists(email: String): Boolean {
        return userDao.userExists(email)
    }

    override suspend fun register(user: User): User {
        val userEntity = user.toUserEntity()
        userDao.register(userEntity)
        addressDao.insert(user.endereco.toAddressEntity())

        return user
    }

    override suspend fun update(user: User) {
        val userEntity = user.toUserEntity(getIdByCredentials(user.email, user.senha))
        userDao.update(userEntity)
    }

    override suspend fun delete(user: User) {
        val userEntity = user.toUserEntity(getIdByCredentials(user.email, user.senha))
        val addressEntity = user.endereco.toAddressEntity()
        addressDao.delete(addressEntity)
        userDao.delete(userEntity)
    }


}