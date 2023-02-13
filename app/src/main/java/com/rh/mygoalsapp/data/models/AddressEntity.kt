package com.rh.mygoalsapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rh.mygoalsapp.domain.models.Address


@Entity(tableName = "address")
data class AddressEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val post_code: String,
    val street: String,
    val number: Int,
    val complement: String,
    val district: String,
    val city: String
)

fun Address.toAddressEntity() : AddressEntity {
    return with(this) {
        AddressEntity(
            post_code = this.post_code,
            street = this.street,
            number = this.number,
            complement = this.complement,
            district = this.district,
            city = this.city
        )
    }
}

fun AddressEntity.toAddress() : Address {
    return with(this) {
        Address(
            this.post_code,
            this.street,
            this.number,
            this.complement,
            this.district,
            this.city
        )
    }
}