package com.lutfi.githubuserapp.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lutfi.githubuserapp.database.FavoriteUser
import com.lutfi.githubuserapp.databinding.ItemUsersBinding
import com.lutfi.githubuserapp.helper.FavUserDiffCallback

class FavoriteUserAdapter: RecyclerView.Adapter<FavoriteUserAdapter.FavoriteUserViewHolder>() {

    private val listFavUser = ArrayList<FavoriteUser>()

    fun setListFavUser(listFavUser: List<FavoriteUser>) {
        val diffCallback = FavUserDiffCallback(this.listFavUser, listFavUser)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavUser.clear()
        this.listFavUser.addAll(listFavUser)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteUserViewHolder {
        val binding = ItemUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteUserViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: FavoriteUserAdapter.FavoriteUserViewHolder,
        position: Int
    ) {
        val user = listFavUser[position]
        holder.bind(user)

        val username = user.username

        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailUserActivity::class.java)
            intentDetail.putExtra("user_name", username)
            holder.itemView.context.startActivity(intentDetail)
        }

    }

    override fun getItemCount(): Int = listFavUser.size

    inner class FavoriteUserViewHolder(private val binding: ItemUsersBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favoriteUser: FavoriteUser) {
            with(binding) {
                binding.profileText.text = favoriteUser.username
                Glide.with(itemView.context)
                    .load(favoriteUser.avatarUrl)
                    .into(binding.profileId)
            }
        }
    }

}