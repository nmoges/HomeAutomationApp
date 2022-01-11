package com.homeautomationapp.database.dao

import androidx.room.*
import com.homeautomationapp.database.entities.UserEntity

/**
 * DAO interface to access database "user" table.
 */
@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUserData(userEntity: UserEntity): Long

    @Update
    suspend fun updateUserData(userEntity: UserEntity)

    @Query("SELECT * FROM user WHERE id = 0")
    suspend fun getUserData(): UserEntity
}