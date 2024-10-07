package core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun DeficitLightColors() = MaterialTheme.colorScheme.copy(
    primary = Color(color = 0xFF4F5080),
    onPrimary = Color(color = 0xFFFFFBAB),
    primaryContainer = Color(color = 0xFF4F5080),
    onPrimaryContainer = Color(color = 0xFFFFFBAB),
    inversePrimary = Color(color = 0xFFFFFBAB),
    secondary = Color.White,
    onSecondary = Color.Black,
    secondaryContainer = Color.White,
    onSecondaryContainer = Color.Black,
    tertiary = Color(color = 0xFFFFFBAB),
    onTertiary = Color.Black,
    tertiaryContainer = Color(color = 0xFFFFFBAB),
    onTertiaryContainer = Color.Black,
    background = Color.White,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
    surfaceVariant = Color.White,
    onSurfaceVariant = Color.Black,
    surfaceTint = Color.White,
    inverseSurface = Color.Black,
    inverseOnSurface = Color.White,
    error = Color(color = 0xFFFF005C),
    onError = Color.White,
    errorContainer = Color(color = 0xFFFF005C),
    onErrorContainer = Color.White,
    outline = Color.Black,
    outlineVariant = Color.Black,
    scrim = Color.Black,
    surfaceBright = Color.White,
    surfaceDim = Color.White,
    surfaceContainer = Color.White,
    surfaceContainerHigh = Color.White,
    surfaceContainerHighest = Color.White,
    surfaceContainerLow = Color.White,
    surfaceContainerLowest = Color.White,
)