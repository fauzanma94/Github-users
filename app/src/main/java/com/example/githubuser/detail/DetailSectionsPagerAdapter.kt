package com.example.githubuser.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubuser.follow.FollowFragment

class DetailSectionsPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    var username: String = ""
    override fun createFragment(position: Int): Fragment {
        val fragment = FollowFragment()
        val bundle = Bundle()
        when (position) {
            0 -> {
                bundle.putString("follow", "following")
            }
            1 -> {
                bundle.putString("follow", "follower")
            }
        }
        fragment.arguments = bundle
        return fragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}