package com.example.rentproject.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.rentproject.domain.model.Settings
import com.example.rentproject.presentation.rooms.RoomsViewModel
import com.example.rentproject.presentation.settings.SettingsViewModel
import com.example.rentproject.ui.theme.RentProjectTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition {
            false
        }
        setContent {
            val settingsViewModel by viewModels<SettingsViewModel>()
            val settings = settingsViewModel.settings
            var darkTheme = settings.darkMode
            RentProjectTheme(darkTheme = darkTheme) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(
                        darkTheme = darkTheme,
                        onThemeUpdated = {
                            darkTheme = !darkTheme
                            settingsViewModel.upsertSettings(
                                settings.copy(
                                    darkMode = darkTheme
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}