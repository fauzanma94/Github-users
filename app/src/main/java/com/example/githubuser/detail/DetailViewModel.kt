package com.example.githubuser.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.githubuser.data.api.ApiConfig
import com.example.githubuser.data.api.GithubResponse
import com.example.githubuser.data.db.FavoriteUser
import com.example.githubuser.data.db.FavoriteUserDao
import com.example.githubuser.data.db.FavoriteUserRoomDatabase
import com.example.githubuser.data.db.repository.FavoriteUserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private val _userProfile = MutableLiveData<GithubResponse?>()
    val userProfile: LiveData<GithubResponse?> = _userProfile

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listFollowing = MutableLiveData<List<GithubResponse>>()
    val listFollowing: LiveData<List<GithubResponse>> = _listFollowing

    private val _listFollowers = MutableLiveData<List<GithubResponse>>()
    val listFollower: LiveData<List<GithubResponse>> = _listFollowers

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private val mFavoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)

    private val listFavorite = mFavoriteUserRepository.getAllUser()
    private val _isFavorite = MediatorLiveData<Boolean>(false)
    val isFavorite: LiveData<Boolean> = _isFavorite

    private var userDao: FavoriteUserDao?
    private var userDb: FavoriteUserRoomDatabase?

    init {
        userDb = FavoriteUserRoomDatabase.getDatabase(application)
        userDao = userDb?.favoriteUserDao()
        _isFavorite.addSource(listFavorite){ listOfFavorite ->
            listOfFavorite.find {
                it.login == username.value
            }?.let {
                _isFavorite.value = true
            } ?: run {
                _isFavorite.value = false
            }
        }
        _isFavorite.addSource(_username){
            if(it.isNotBlank() || it != null) {
                viewModelScope.launch(Dispatchers.IO) {
                    val listFavorite = mFavoriteUserRepository.getFavoriteUser(it)
                    _isFavorite.postValue(listFavorite.isNotEmpty())
                }
            }
        }
    }


    fun loadUserProfile(username: String = " ") {
        _isLoading.value = true
        _userProfile.value = null

        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _userProfile.value = responseBody
                        _username.value = _userProfile.value?.login
                    }
                } else {
                    Log.e(TAG, "OnFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun loadFollowing(username: String = "") {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<List<GithubResponse>> {
            override fun onResponse(
                call: Call<List<GithubResponse>>,
                response: Response<List<GithubResponse>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listFollowing.value = response.body()
                    }
                } else {
                    Log.e(TAG, "OnFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<GithubResponse>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG_FOLLOWING, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun loadFollower(username: String = "") {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getFollower(username)
        client.enqueue(object : Callback<List<GithubResponse>> {
            override fun onResponse(
                call: Call<List<GithubResponse>>,
                response: Response<List<GithubResponse>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listFollowers.value = response.body()
                    }
                } else {
                    Log.e(TAG, "OnFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<GithubResponse>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG_FOLLOWER, "onFailure: ${t.message.toString()}")
            }
        })

    }

    fun insert(favoriteUser: FavoriteUser){
        mFavoriteUserRepository.insert(favoriteUser)
    }

    fun delete(favoriteUser: FavoriteUser){
        mFavoriteUserRepository.delete(favoriteUser)
    }


    companion object {
        private const val TAG = "DetailViewModel"
        private const val TAG_FOLLOWING = "ListFollowing"
        private const val TAG_FOLLOWER = "ListFollower"
    }


}

