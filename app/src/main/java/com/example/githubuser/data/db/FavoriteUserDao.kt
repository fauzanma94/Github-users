package com.example.githubuser.data.db

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface FavoriteUserDao{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(username: FavoriteUser)

    @Delete
    fun delete(username: FavoriteUser)

    @Query("SELECT * FROM FavoriteUser")
    fun getAllUser(): LiveData<List<FavoriteUser>>

    @Query("SELECT * FROM FavoriteUser WHERE login = :username")
    fun getFavoriteUser(username: String): List<FavoriteUser>
}