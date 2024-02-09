package com.example.rentproject.presentation.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rentproject.domain.model.Settings
import com.example.rentproject.domain.use_case.RoomUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val roomUseCases: RoomUseCases,
) : ViewModel() {

    var settings by mutableStateOf(Settings(
        id = 0,
        darkMode = false,
        currency = false
    ))

    init {
        getSettings(0)
    }
    fun upsertSettings(settings: Settings){
        viewModelScope.launch {
            roomUseCases.upsertSettings(settings)
        }
    }

    private fun getSettings(id: Int){
        viewModelScope.launch {
            roomUseCases.getSettingsById(id).collect{
                if (it != null)
                settings = it
            }
        }
    }
}