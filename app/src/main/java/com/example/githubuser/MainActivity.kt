package com.example.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.githubuser.databinding.ActivityMainBinding
import com.example.githubuser.databinding.FragmentSwitchBinding
import com.example.githubuser.theme.SettingPreferences
import com.example.githubuser.theme.SwitchModelFactory
import com.example.githubuser.theme.SwitchViewModel
import com.example.githubuser.theme.dataStore
import com.google.android.material.internal.ContextUtils.getActivity


class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private val _binding get() = binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        getThemeSettings()
    }

    private fun getThemeSettings(){
        val bindingSetting = FragmentSwitchBinding.inflate(layoutInflater)
        val pref = SettingPreferences.getInstance(dataStore)
        val switchViewModel = ViewModelProvider(this, SwitchModelFactory(pref)).get(
            SwitchViewModel::class.java
        )
        switchViewModel.getThemeSettings().observe(this, {isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                bindingSetting.switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                bindingSetting.switchTheme.isChecked = false
            }
        })
        bindingSetting.switchTheme.setOnCheckedChangeListener{_: CompoundButton?, isChecked: Boolean ->
            switchViewModel.saveThemeSetting(isChecked)
        }
    }






}


