package com.example.githubuser.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubuser.data.api.GithubResponse
import com.example.githubuser.databinding.ItemRowUsersBinding


class MenuAdapter(private val listUser: List<GithubResponse>) :
    RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemRowUsersBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val (login, avatar_url) = listUser[position]

        Glide.with(viewHolder.itemView.context)
            .load(avatar_url)
            .apply(RequestOptions())
            .into(viewHolder.binding.itemPhoto)

        viewHolder.binding.itemUser.text = login

        viewHolder.itemView.setOnClickListener {
            val toFragmentDetail = MenuFragmentDirections.actionMenuFragmentToDetailFragment()
            toFragmentDetail.username = login
            viewHolder.itemView.findNavController().navigate(toFragmentDetail)
        }
    }

    override fun getItemCount() = listUser.size

    class ViewHolder(var binding: ItemRowUsersBinding) : RecyclerView.ViewHolder(binding.root)
}