package com.example.githubuser

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.githubuser.data.db.FavoriteUser
import com.example.githubuser.data.db.FavoriteUserDao
import com.example.githubuser.data.db.FavoriteUserRoomDatabase
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class FavoriteDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    lateinit var db: FavoriteUserRoomDatabase
    lateinit var favoriteDao: FavoriteUserDao


    @Before
    fun setup(){
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            FavoriteUserRoomDatabase::class.java
        ).allowMainThreadQueries().build()
        favoriteDao = db.favoriteUserDao()
    }

    @After
    fun teardown(){
        db.close()
    }

    @Test
    fun insertFavoriteUser() = runBlockingTest{
        val favoriteUser = FavoriteUser("username","url")
        favoriteDao.insert(favoriteUser)

        val items = favoriteDao.getAllUser().getOrAwaitValue()

        assertThat(items).contains(favoriteUser)
    }

}