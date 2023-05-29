package com.example.githubuser.data.db.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.githubuser.data.db.FavoriteUser
import com.example.githubuser.data.db.FavoriteUserDao
import com.example.githubuser.data.db.FavoriteUserRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepository (application: Application) {
    private val mFavoriteUserDao: FavoriteUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteUserRoomDatabase.getDatabase(application)
        mFavoriteUserDao = db.favoriteUserDao()
    }

    fun getAllUser(): LiveData<List<FavoriteUser>> = mFavoriteUserDao.getAllUser()

    fun insert(favoriteUser: FavoriteUser){
        executorService.execute{mFavoriteUserDao.insert(favoriteUser)}
    }

    fun delete(favoriteUser: FavoriteUser){
        executorService.execute{mFavoriteUserDao.delete(favoriteUser)}
    }

    fun getFavoriteUser(username:String): List<FavoriteUser> = mFavoriteUserDao.getFavoriteUser(username)
}