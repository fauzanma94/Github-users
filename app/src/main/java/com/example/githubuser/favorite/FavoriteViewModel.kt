package com.example.githubuser.favorite

import android.app.Application
import androidx.lifecycle.*
import com.example.githubuser.data.db.FavoriteUser
import com.example.githubuser.data.db.repository.FavoriteUserRepository

class FavoriteViewModel(application: Application): AndroidViewModel(application) {

    private val mFavoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)
    fun getAllFavorites(): LiveData<List<FavoriteUser>> = mFavoriteUserRepository.getAllUser()

}