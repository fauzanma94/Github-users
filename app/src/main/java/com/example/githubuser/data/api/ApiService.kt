package com.example.githubuser.data.api

import com.example.githubuser.data.api.GithubListResponse
import com.example.githubuser.data.api.GithubResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    fun getAllUsers(): Call<List<GithubResponse>>

    @GET("search/users")
    fun searchUser(
        @Query("q") username: String
    ): Call<GithubListResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<GithubResponse>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<GithubResponse>>

    @GET("users/{username}/followers")
    fun getFollower(
        @Path("username") username: String
    ): Call<List<GithubResponse>>
}