package com.example.rentproject.presentation.settings

import androidx.lifecycle.ViewModel
import com.example.rentproject.domain.use_case.RoomUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val roomUseCases: RoomUseCases
): ViewModel() {


}