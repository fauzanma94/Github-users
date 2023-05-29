package com.example.githubuser.theme

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import com.example.githubuser.databinding.FragmentSwitchBinding
import androidx.datastore.preferences.core.Preferences

import androidx.lifecycle.ViewModelProvider


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SwitchFragment : Fragment() {
    private var _binding: FragmentSwitchBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSwitchBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pref = SettingPreferences.getInstance(requireActivity().dataStore)
        val switchViewModel = ViewModelProvider(this, SwitchModelFactory(pref))[SwitchViewModel::class.java]
        switchViewModel.getThemeSettings().observe(viewLifecycleOwner, {isDarkModeActive: Boolean ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    binding.switchTheme.isChecked = true
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    binding.switchTheme.isChecked = false
                }
            })
        binding.switchTheme.setOnCheckedChangeListener{_: CompoundButton?, isChecked: Boolean ->
            switchViewModel.saveThemeSetting(isChecked)
        }
    }
}