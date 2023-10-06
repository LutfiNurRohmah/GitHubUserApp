package com.lutfi.githubuserapp.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.lutfi.githubuserapp.database.FavoriteUser
import com.lutfi.githubuserapp.database.FavoriteUserDao
import com.lutfi.githubuserapp.database.FavoriteUserDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepository(application: Application) {
    private val mFavoriteUser: FavoriteUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteUserDatabase.getDatabase(application)
        mFavoriteUser = db.favoriteUserDao()
    }

    fun getAllFavoriteUsers(): LiveData<List<FavoriteUser>> = mFavoriteUser.getAllFavoriteUsers()

    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser> = mFavoriteUser.getFavoriteUserByUsername(username)

    fun insert(favoriteUser: FavoriteUser) {
        executorService.execute { mFavoriteUser.insert(favoriteUser) }
    }

    fun delete(favoriteUser: FavoriteUser) {
        executorService.execute { mFavoriteUser.delete(favoriteUser) }
    }

    fun update(favoriteUser: FavoriteUser) {
        executorService.execute { mFavoriteUser.update(favoriteUser) }
    }
}