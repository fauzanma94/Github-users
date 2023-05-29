package com.example.githubuser.data.api

import com.google.gson.annotations.SerializedName

data class GithubListResponse(
	@field:SerializedName("items")
	val gitHubListResponse: List<GithubResponse>
)

data class GithubResponse(
	@field:SerializedName("login")
	val login: String ="",

	@field:SerializedName("avatar_url")
	val avatarUrl: String = "",

	@field:SerializedName("name")
	var name: String ="",

	@field:SerializedName("following")
	val following: Int = 0 ,

	@field:SerializedName("followers")
	val followers: Int = 0
)
