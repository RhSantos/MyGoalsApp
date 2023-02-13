package com.rh.mygoalsapp.data.db.dao

import androidx.room.*
import com.rh.mygoalsapp.data.models.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun register(user: UserEntity)

    @Query("SELECT EXISTS (SELECT * FROM user WHERE email=:email)")
    suspend fun userExists(email:String) : Boolean

    @Query("SELECT * FROM user WHERE email = :email AND senha = :senha")
    suspend fun login(email:String,senha:String) : UserEntity

    @Query("SELECT id FROM user WHERE email = :email AND senha = :senha")
    suspend fun getIdByCredentials(email: String, senha: String) : Long

    @Update
    suspend fun update(user: UserEntity)

    @Delete
    suspend fun delete(user: UserEntity)
}