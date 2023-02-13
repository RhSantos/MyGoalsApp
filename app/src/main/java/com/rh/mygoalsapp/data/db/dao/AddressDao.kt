package com.rh.mygoalsapp.data.db.dao

import androidx.room.*
import com.rh.mygoalsapp.data.models.AddressEntity

@Dao
interface AddressDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(address: AddressEntity) : Long

    @Query("SELECT * FROM address WHERE id = :id")
    suspend fun getAddressById(id: Long): AddressEntity

    @Update
    suspend fun update(address: AddressEntity)

    @Delete
    suspend fun delete(address: AddressEntity)
}