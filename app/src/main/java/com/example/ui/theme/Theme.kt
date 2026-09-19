package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val AgriDarkColorScheme = darkColorScheme(
    primary = AgriMintGreen,
    onPrimary = AgriDeepGreen,
    primaryContainer = AgriForestGreen,
    onPrimaryContainer = AgriLightGreen,
    secondary = AgriGoldAccent,
    onSecondary = Color(0xFF261A00),
    secondaryContainer = Color(0xFF483500),
    onSecondaryContainer = AgriGoldAccent,
    background = AgriDarkBackground,
    onBackground = AgriDarkTextPrimary,
    surface = AgriDarkSurface,
    onSurface = AgriDarkTextPrimary,
    surfaceVariant = AgriDarkSurfaceVariant,
    onSurfaceVariant = AgriDarkTextSecondary,
    outline = Color(0xFF33493A)
)

private val AgriLightColorScheme = lightColorScheme(
    primary = AgriPrimaryGreen,
    onPrimary = Color.White,
    primaryContainer = AgriLightGreen,
    onPrimaryContainer = AgriDeepGreen,
    secondary = AgriForestGreen,
    onSecondary = Color.White,
    secondaryContainer = AgriUltraLightGreen,
    onSecondaryContainer = AgriDeepGreen,
    tertiary = AgriAmber,
    background = AgriBackground,
    onBackground = AgriTextPrimary,
    surface = AgriSurface,
    onSurface = AgriTextPrimary,
    surfaceVariant = AgriSurfaceVariant,
    onSurfaceVariant = AgriTextSecondary,
    outline = AgriBorder
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Preserve AgriWise custom brand identity
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> AgriDarkColorScheme
        else -> AgriLightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
