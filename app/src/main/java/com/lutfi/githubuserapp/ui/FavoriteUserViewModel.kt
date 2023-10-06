package com.lutfi.githubuserapp.ui

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.lutfi.githubuserapp.database.FavoriteUser
import com.lutfi.githubuserapp.repository.FavoriteUserRepository

class FavoriteUserViewModel(application: Application) : ViewModel() {
    private val mFavoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)

    fun getAllFavoriteUsers() : LiveData<List<FavoriteUser>> = mFavoriteUserRepository.getAllFavoriteUsers()
}