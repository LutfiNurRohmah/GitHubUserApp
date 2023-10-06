package com.lutfi.githubuserapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lutfi.githubuserapp.databinding.ActivityFavoriteUserBinding
import com.lutfi.githubuserapp.helper.ViewModelFactory

class FavoriteUserActivity : AppCompatActivity() {

    private var _activityFavoriteUser: ActivityFavoriteUserBinding? = null
    private val binding get() = _activityFavoriteUser

    private lateinit var adapter: FavoriteUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityFavoriteUser = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(_activityFavoriteUser?.root)

        val favoriteUserViewModel = obtainViewModel(this@FavoriteUserActivity)
        favoriteUserViewModel.getAllFavoriteUsers().observe(this) { favUserList ->
            if (favUserList != null) {
                adapter.setListFavUser(favUserList)
            }
        }

        adapter = FavoriteUserAdapter()

        binding?.rvUser?.layoutManager = LinearLayoutManager(this)
        binding?.rvUser?.setHasFixedSize(true)
        binding?.rvUser?.adapter = adapter
    }

    private fun obtainViewModel(activity: AppCompatActivity) : FavoriteUserViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteUserViewModel::class.java]
    }
}