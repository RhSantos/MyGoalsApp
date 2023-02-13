package com.rh.mygoalsapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rh.mygoalsapp.data.db.dao.AddressDao
import com.rh.mygoalsapp.data.db.dao.UserDao
import com.rh.mygoalsapp.data.models.AddressEntity
import com.rh.mygoalsapp.data.models.UserEntity
import com.rh.mygoalsapp.util.Constants.dbName

@Database(entities = [UserEntity::class, AddressEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun addressDao(): AddressDao

    companion object {
        @Volatile
        private var INSTANCE:AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE

            if (tempInstance != null) return tempInstance

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    dbName
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}