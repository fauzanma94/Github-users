package com.example.githubuser.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.githubuser.R
import com.example.githubuser.data.api.GithubResponse
import com.example.githubuser.data.db.FavoriteUser
import com.example.githubuser.databinding.FragmentDetailBinding
import com.google.android.material.tabs.TabLayoutMediator


class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val detailViewModel by activityViewModels<DetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        }

        val dataUser = DetailFragmentArgs.fromBundle(arguments as Bundle).username
        detailViewModel.loadUserProfile(dataUser)

        val isFavorite = detailViewModel.isFavorite.value ?: false
        if (isFavorite) {
            binding.btnFav.icon =
                ContextCompat.getDrawable(requireContext(), R.drawable.baseline_favorite_24)
        } else {
            binding.btnFav.icon =
                ContextCompat.getDrawable(requireContext(), R.drawable.baseline_favorite_border_24)
        }

        detailViewModel.isFavorite.observe(viewLifecycleOwner) {
            if (it) {
                binding.btnFav.icon =
                    ContextCompat.getDrawable(requireContext(), R.drawable.baseline_favorite_24)
            } else {
                binding.btnFav.icon = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.baseline_favorite_border_24
                )
            }
        }
        detailViewModel.userProfile.observe(viewLifecycleOwner) { userProfile ->
            if (userProfile != null) {
                setUserData(userProfile)
            }
        }

        detailViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        val sectionsPagerAdapter = DetailSectionsPagerAdapter(this)
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tabs, position ->
            tabs.text = resources.getString(TAB_TITLES[position])
        }.attach()

        binding.btnFav.setOnClickListener {
            val isFavorite = detailViewModel.isFavorite.value ?: false
            if (isFavorite) {
                val userFav = FavoriteUser()
                userFav.login = detailViewModel.username.value.toString()
                detailViewModel.delete(userFav)

            } else {
                val userFav = FavoriteUser()
                userFav.let {
                    it.login = detailViewModel.username.value.toString()
                    it.avatarUrl = detailViewModel.userProfile.value?.avatarUrl
                }
                detailViewModel.insert(userFav)
            }
        }

    }

    private fun setUserData(userProfile: GithubResponse) {
        binding.apply {
            profileName.text = userProfile.name ?: "-"
            profileUsername.text = ("@" + userProfile.login) ?: "-"
            follower.text = (userProfile.followers.toString() + " Followers") ?: "-"
            following.text = (userProfile.following.toString() + " Following") ?: "-"
        }

        Glide.with(binding.root)
            .load(userProfile.avatarUrl)
            .placeholder(android.R.color.white)
            .into(binding.profileImg)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_1,
            R.string.tab_2
        )

    }

}
