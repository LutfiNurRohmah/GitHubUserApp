package com.lutfi.githubuserapp.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lutfi.githubuserapp.data.response.ItemsItem
import com.lutfi.githubuserapp.databinding.ItemUsersBinding

class GitHubUserAdapter : ListAdapter<ItemsItem, GitHubUserAdapter.MyViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    class MyViewHolder(private val binding: ItemUsersBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: ItemsItem){
            binding.profileText.text = user.login
            Glide.with(itemView.context)
                .load(user.avatarUrl)
                .into(binding.profileId)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)

        val username = user.login

        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailUserActivity::class.java)
            intentDetail.putExtra("user_name", username)
            holder.itemView.context.startActivity(intentDetail)
        }
    }
}