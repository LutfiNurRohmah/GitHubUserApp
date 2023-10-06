package com.lutfi.githubuserapp.ui

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lutfi.githubuserapp.database.FavoriteUser
import com.lutfi.githubuserapp.repository.FavoriteUserRepository

class FavoriteUserViewModel(application: Application) : ViewModel() {
    private val mFavoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getAllFavoriteUsers() : LiveData<List<FavoriteUser>> = mFavoriteUserRepository.getAllFavoriteUsers()
}