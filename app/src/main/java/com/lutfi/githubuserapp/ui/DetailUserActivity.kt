package com.lutfi.githubuserapp.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.lutfi.githubuserapp.R
import com.lutfi.githubuserapp.data.response.DetailUserResponse
import com.lutfi.githubuserapp.database.FavoriteUser
import com.lutfi.githubuserapp.databinding.ActivityDetailUserBinding
import com.lutfi.githubuserapp.helper.ViewModelFactory

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding

    private val detailUserViewModel by viewModels<DetailUserViewModel>()

    private val favoriteUserAddViewModel by viewModels<FavoriteUserAddViewModel> {
        ViewModelFactory.getInstance(application)
    }

    private var favoriteUser: FavoriteUser = FavoriteUser()
    private var isFavorite: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val userName = intent.getStringExtra("user_name")

        detailUserViewModel.getDetail(userName.toString())

        detailUserViewModel.detailUser.observe(this) {
            setDetailUser(it)
        }

        detailUserViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        val sectionPagerAdapter = SectionPagerAdapter(this)
        sectionPagerAdapter.username = userName.toString()

        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionPagerAdapter

        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f
    }

    private fun addFavoriteUser(user: DetailUserResponse) {

        favoriteUserAddViewModel.getFavoriteUserByUsername(user.login).observe(this) { favorite ->
            if (favorite != null) {
                isFavorite = true
                binding.fabAddFavorite.setImageResource(R.drawable.baseline_favorite_24)
            } else {
                isFavorite = false
                binding.fabAddFavorite.setImageResource(R.drawable.baseline_favorite_border_24)
            }
        }

        binding.fabAddFavorite.setOnClickListener {
            val username = user.login
            val avatar = user.avatarUrl
            favoriteUser.let { favoriteUser ->
                favoriteUser.username = username
                favoriteUser.avatarUrl = avatar
            }

            if (isFavorite == true) {
                isFavorite = false
                favoriteUserAddViewModel.delete(favoriteUser)
                binding.fabAddFavorite.setImageResource(R.drawable.baseline_favorite_border_24)
                showToast("Berhasil menghapus dari favorit")

            } else {
                isFavorite = true
                favoriteUserAddViewModel.insert(favoriteUser)
                binding.fabAddFavorite.setImageResource(R.drawable.baseline_favorite_24)
                showToast("Berhasil menambahkan ke favorit")
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(state: Boolean) { binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE }

    private fun setDetailUser(user: DetailUserResponse) {
        with(binding) {
            tvNama.text = user.name?.toString()
            tvUsername.text = user.login
            tvFollowers.text = resources.getString(R.string.followers, user.followers)
            tvFollowing.text = resources.getString(R.string.following, user.following)
            Glide.with(this@DetailUserActivity)
                .load(user.avatarUrl)
                .into(profileImg)
        }
        addFavoriteUser(user)
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}