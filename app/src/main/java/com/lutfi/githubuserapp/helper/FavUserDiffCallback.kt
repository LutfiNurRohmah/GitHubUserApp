package com.lutfi.githubuserapp.helper

import androidx.recyclerview.widget.DiffUtil
import com.lutfi.githubuserapp.database.FavoriteUser

class FavUserDiffCallback(private val oldFavList: List<FavoriteUser>, private val newFavList: List<FavoriteUser>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldFavList.size

    override fun getNewListSize(): Int = newFavList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldFavList[oldItemPosition].username == newFavList[newItemPosition].username
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldFavUser = oldFavList[oldItemPosition]
        val newFavUser = newFavList[newItemPosition]
        return oldFavUser.username == newFavUser.username && oldFavUser.avatarUrl == newFavUser.avatarUrl
    }

}