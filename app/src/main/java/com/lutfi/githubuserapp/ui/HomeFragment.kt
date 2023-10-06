package com.lutfi.githubuserapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lutfi.githubuserapp.data.response.ItemsItem
import com.lutfi.githubuserapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private var position: Int = 0
    private var username: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFollow.layoutManager = layoutManager

        val detailUserViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DetailUserViewModel::class.java]

        if (position == 1) {
            detailUserViewModel.getListFollowers(username)
        } else {
            detailUserViewModel.getListFollowing(username)
        }

        detailUserViewModel.isLoading2.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        detailUserViewModel.listFollowers.observe(viewLifecycleOwner) {
            setFollow(it)
        }

        detailUserViewModel.listFollowing.observe(viewLifecycleOwner) {
            setFollow(it)
        }

    }

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }

    private fun setFollow(userGithub: List<ItemsItem>) {
        val adapter = GitHubUserAdapter()
        adapter.submitList(userGithub)
        binding.rvFollow.adapter = adapter
    }

    private fun showLoading(state: Boolean) { binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE }
}