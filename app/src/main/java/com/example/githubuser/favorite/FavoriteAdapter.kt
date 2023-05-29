package com.example.githubuser.favorite

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubuser.R
import com.example.githubuser.data.db.FavoriteUser
import com.example.githubuser.databinding.ItemRowUsersBinding

class FavoriteAdapter(private var items: List<FavoriteUser>) :
    RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemRowUsersBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (login, avatar_url) = items[position]

        Glide.with(holder.itemView.context)
            .load(avatar_url)
            .apply(RequestOptions())
            .into(holder.imageView)
        holder.textView.text = login

        holder.binding.clUserCard.setOnClickListener {
            val toFragmentDetail =
                FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment()
            toFragmentDetail.username = login
            holder.itemView.findNavController().navigate(toFragmentDetail)

        }

    }

    inner class ViewHolder(var binding: ItemRowUsersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val imageView: ImageView = binding.itemPhoto
        val textView: TextView = binding.itemUser
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<FavoriteUser>) {
        this.items = items
        notifyDataSetChanged()
    }
}