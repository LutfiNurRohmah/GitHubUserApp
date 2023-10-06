package com.lutfi.githubuserapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.lutfi.githubuserapp.R
import com.lutfi.githubuserapp.data.response.ItemsItem
import com.lutfi.githubuserapp.databinding.ActivityMainBinding
import com.lutfi.githubuserapp.helper.SettingViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)

        val pref = SettingPreferences.getInstance(application.dataStore)
        val settingViewModel = ViewModelProvider(this, SettingViewModelFactory(pref)).get(
            SettingViewModel::class.java
        )
        settingViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.searchBar.menu.findItem(R.id.menu_favorite).setIcon(R.drawable.baseline_favorite_24_night)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.searchBar.menu.findItem(R.id.menu_favorite).setIcon(R.drawable.baseline_favorite_24)
            }
        }

        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MainViewModel::class.java]
        mainViewModel.listUser.observe(this) {
            setUserData(it)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    searchBar.text = searchView.text
                    searchView.hide()
                    mainViewModel.searchUserData(binding.searchBar.text.toString())
                    false
                }
        }

        binding.searchBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_favorite -> {
                    val intent = Intent(this, FavoriteUserActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.menu_setting -> {
                    val intent = Intent(this, SettingActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun setUserData(userGithub: List<ItemsItem>) {
        val adapter = GitHubUserAdapter()
        adapter.submitList(userGithub)
        binding.rvUser.adapter = adapter
    }
}