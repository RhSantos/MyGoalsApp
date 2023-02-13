package com.rh.mygoalsapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rh.mygoalsapp.domain.models.Address
import com.rh.mygoalsapp.domain.models.User
import com.rh.mygoalsapp.util.ImageFiles

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nome: String,
    val email: String,
    val senha: String,
    val data_nascimento: String,
    val foto: ByteArray,
    val addressId: Long
)

fun UserEntity.toUser(address: Address): User {
    return with(this) {
        User(
            nome = this.nome,
            email = this.email,
            senha = this.senha,
            data_nascimento = this.data_nascimento,
            foto = ImageFiles.decodeToBitmap(this.foto),
            endereco = address
        )
    }
}

fun User.toUserEntity(): UserEntity {
    return with(this) {
        UserEntity(
            nome = this.nome,
            email = this.email,
            senha = this.senha,
            data_nascimento = this.data_nascimento,
            foto = ImageFiles.encodeToByteArray(this.foto),
            addressId = 0
        )
    }
}

fun User.toUserEntity(addressId: Long): UserEntity {
    return with(this) {
        UserEntity(
            nome = this.nome,
            email = this.email,
            senha = this.senha,
            data_nascimento = this.data_nascimento,
            foto = ImageFiles.encodeToByteArray(this.foto),
            addressId = addressId
        )
    }
}