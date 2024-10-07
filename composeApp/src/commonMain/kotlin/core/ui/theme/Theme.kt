package core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun DeficitTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        typography = DeficitTypography(),
        colorScheme = DeficitLightColors(),
        content = content
    )
}
