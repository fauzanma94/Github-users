package com.example.githubuser.menu

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.data.api.ApiConfig
import com.example.githubuser.data.api.GithubListResponse
import com.example.githubuser.data.api.GithubResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuViewModel : ViewModel() {

    private val _listUser = MutableLiveData<List<GithubResponse>>()
    val listUser: LiveData<List<GithubResponse>> = _listUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        loadListUser()
    }

    private fun loadListUser() {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getAllUsers()
        client.enqueue(object : Callback<List<GithubResponse>> {
            override fun onResponse(
                call: Call<List<GithubResponse>>,
                response: Response<List<GithubResponse>>
            ) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listUser.value = response.body()
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<GithubResponse>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun searchUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().searchUser(username)
        client.enqueue(object : Callback<GithubListResponse> {
            override fun onResponse(
                call: Call<GithubListResponse>,
                response: Response<GithubListResponse>
            ) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listUser.value = response.body()?.gitHubListResponse
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubListResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}