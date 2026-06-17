package com.saweena.mylotto2.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val ErrorContainer = Error.copy(alpha = 0.24f)

private val DarkColorScheme = darkColorScheme(
    primary = Primary,
    onPrimary = TextOnPrimary,
    primaryContainer = SurfaceGreen,
    onPrimaryContainer = TextPrimary,
    secondary = Secondary,
    onSecondary = TextPrimary,
    secondaryContainer = DarkSurfaceVariant,
    onSecondaryContainer = DarkTextPrimary,
    tertiary = Info,
    onTertiary = TextOnPrimary,
    background = DarkBackground,
    onBackground = DarkTextPrimary,
    surface = DarkSurface,
    onSurface = DarkTextPrimary,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkTextSecondary,
    error = Error,
    onError = TextOnPrimary,
    errorContainer = ErrorContainer,
    onErrorContainer = DarkTextPrimary,
    outline = DarkBorder
)

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = TextOnPrimary,
    primaryContainer = SurfaceGreen,
    onPrimaryContainer = TextPrimary,
    secondary = Secondary,
    onSecondary = TextPrimary,
    secondaryContainer = SurfaceYellow,
    onSecondaryContainer = TextPrimary,
    tertiary = Info,
    onTertiary = TextOnPrimary,
    background = Background,
    onBackground = TextPrimary,
    surface = Surface,
    onSurface = TextPrimary,
    surfaceVariant = SurfaceGreen,
    onSurfaceVariant = TextSecondary,
    error = Error,
    onError = TextOnPrimary,
    errorContainer = Error.copy(alpha = 0.14f),
    onErrorContainer = Error,
    outline = Border
)

@Composable
fun MyLotto2Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
