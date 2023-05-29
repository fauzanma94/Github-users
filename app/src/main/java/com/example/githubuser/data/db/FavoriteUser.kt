package com.example.githubuser.data.db

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class FavoriteUser (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name="login")
    var login: String ="",

    @ColumnInfo(name="avatarUrl")
    var avatarUrl: String? = null
) : Parcelable