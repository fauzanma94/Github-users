package com.example.githubuser.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SwitchModelFactory(private val preference: SettingPreferences): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T: ViewModel> create(modelClass: Class<T>): T{
        if(modelClass.isAssignableFrom(SwitchViewModel::class.java)){
            return SwitchViewModel(preference) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: "+ modelClass.name)
    }
}