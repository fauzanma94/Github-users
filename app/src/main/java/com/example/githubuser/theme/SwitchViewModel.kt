package com.example.githubuser.theme

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SwitchViewModel(private val preference: SettingPreferences) : ViewModel() {
    fun getThemeSettings(): LiveData<Boolean> {
        return preference.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            preference.saveThemeSetting(isDarkModeActive)
        }
    }
}