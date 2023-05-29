package com.example.githubuser.follow

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.data.api.GithubResponse
import com.example.githubuser.databinding.FragmentFollowBinding
import com.example.githubuser.detail.DetailViewModel

class FollowFragment : Fragment() {
    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private val detailViewModel by activityViewModels<DetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val follow = arguments?.getString("follow")
        if (follow == "following") {
            detailViewModel.listFollowing.observe(viewLifecycleOwner) { listFollowing ->
                setListUserFollowing(listFollowing)
            }
            detailViewModel.userProfile.observe(viewLifecycleOwner) { userProfile ->
                detailViewModel.loadFollowing(userProfile?.login ?: "")
            }
            detailViewModel.isLoading.observe(viewLifecycleOwner) {
                showLoad(it)
            }
        }
        if (follow == "follower") {
            detailViewModel.listFollower.observe(viewLifecycleOwner) { listFollower ->
                setListUserFollower(listFollower)
            }
            detailViewModel.userProfile.observe(viewLifecycleOwner) { userProfile ->
                detailViewModel.loadFollower(userProfile?.login ?: "")
            }
            detailViewModel.isLoading.observe(viewLifecycleOwner) {
                showLoad(it)
            }
        }
        val layoutManager = LinearLayoutManager(activity)
        binding.rvUsers.layoutManager = layoutManager
        binding.rvUsers.setHasFixedSize(true)
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun setListUserFollowing(listFollowing: List<GithubResponse>?) {
        val adapter = listFollowing?.let { FollowAdapter(it) }
        binding.rvUsers.adapter = adapter
        adapter?.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setListUserFollower(listFollower: List<GithubResponse>) {
        val adapter = listFollower.let { FollowAdapter(it) }
        binding.rvUsers.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun showLoad(isLoad: Boolean) {
        binding.progressBar.visibility = if (isLoad) View.VISIBLE else View.INVISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}