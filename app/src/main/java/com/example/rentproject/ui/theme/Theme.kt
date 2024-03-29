package com.example.rentproject.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Grey45,
    onPrimary = Grey20,
    primaryContainer = Grey45,
    onPrimaryContainer = Grey90,
    inversePrimary = Grey45,
    secondary = Grey60,
    onSecondary = white,
    secondaryContainer = Grey60,
    onSecondaryContainer = Grey90,
    error = Red80,
    onError = Red20,
    errorContainer = Red30,
    onErrorContainer = Red90,
    background = Grey10,
    onBackground = Grey90,
    surface = Grey30,
    onSurface = Grey99,
    inverseSurface = Grey90,
    inverseOnSurface = Grey10,
    surfaceVariant = Grey30,
    onSurfaceVariant = Grey80,
    outline = Grey80
)

private val LightColorScheme = lightColorScheme(
    primary = yellow40,
    onPrimary = white,
    primaryContainer = yellow90,
    onPrimaryContainer = yellow10,
    inversePrimary = yellow80,
    secondary = DarkYellow40,
    onSecondary = white,
    secondaryContainer = DarkYellow90,
    onSecondaryContainer = DarkYellow10,
    error = Red40,
    onError = white,
    errorContainer = Red90,
    onErrorContainer = Red10,
    background = Grey99,
    onBackground = Grey10,
    surface = YellowGrey90,
    onSurface = YellowGrey30,
    inverseSurface = Grey20,
    inverseOnSurface = Grey90,
    surfaceVariant = YellowGrey90,
    onSurfaceVariant = YellowGrey30,
    outline = YellowGrey50
)

@Composable
fun RentProjectTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}