package com.lutfi.githubuserapp.ui

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.lutfi.githubuserapp.database.FavoriteUser
import com.lutfi.githubuserapp.repository.FavoriteUserRepository

class FavoriteUserAddViewModel(application: Application) : ViewModel() {
    private val mFavoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)

    fun insert(favoriteUser: FavoriteUser) {
        mFavoriteUserRepository.insert(favoriteUser)
    }

    fun delete(favoriteUser: FavoriteUser) {
        mFavoriteUserRepository.delete(favoriteUser)
    }

    fun update(favoriteUser: FavoriteUser) {
        mFavoriteUserRepository.delete(favoriteUser)
    }

    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser> = mFavoriteUserRepository.getFavoriteUserByUsername(username)

}