package com.example.githubuser.follow


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubuser.data.api.GithubResponse
import com.example.githubuser.databinding.ItemRowUsersBinding

class FollowAdapter(private val listFollow: List<GithubResponse>) :
    RecyclerView.Adapter<FollowAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemRowUsersBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val (login, avatar_url) = listFollow[position]

        Glide.with(viewHolder.itemView.context)
            .load(avatar_url)
            .apply(RequestOptions())
            .into(viewHolder.binding.itemPhoto)

        viewHolder.binding.itemUser.text = login

    }

    override fun getItemCount() = listFollow.size

    class ViewHolder(var binding: ItemRowUsersBinding) : RecyclerView.ViewHolder(binding.root)
}