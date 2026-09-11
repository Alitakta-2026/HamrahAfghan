package com.hamrahafghan.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val HamrahColorScheme = lightColorScheme(
    primary = HamrahPrimary,
    onPrimary = HamrahOnPrimary,
    primaryContainer = HamrahPrimaryContainer,
    onPrimaryContainer = HamrahOnPrimaryContainer,
    secondary = HamrahSecondary,
    onSecondary = HamrahOnSecondary,
    secondaryContainer = HamrahSecondaryContainer,
    onSecondaryContainer = HamrahOnSecondaryContainer,
    tertiary = HamrahTertiary,
    onTertiary = HamrahOnTertiary,
    tertiaryContainer = HamrahTertiaryContainer,
    onTertiaryContainer = HamrahOnTertiaryContainer,
    error = HamrahError,
    onError = HamrahOnError,
    errorContainer = HamrahErrorContainer,
    onErrorContainer = HamrahOnErrorContainer,
    background = HamrahBackground,
    onBackground = HamrahOnBackground,
    surface = HamrahSurface,
    onSurface = HamrahOnSurface,
    surfaceVariant = HamrahSurfaceVariant,
    onSurfaceVariant = HamrahOnSurfaceVariant,
    outline = HamrahOutline
)

@Composable
fun HamrahTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = HamrahColorScheme,
        content = content
    )
}
