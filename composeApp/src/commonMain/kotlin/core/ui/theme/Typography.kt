package core.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import deficit.composeapp.generated.resources.Res
import deficit.composeapp.generated.resources.monument_grotesk_bold
import deficit.composeapp.generated.resources.monument_grotesk_regular
import deficit.composeapp.generated.resources.source_serif_bold
import deficit.composeapp.generated.resources.source_serif_regular
import deficit.composeapp.generated.resources.source_serif_semibold
import org.jetbrains.compose.resources.Font

@Composable
fun SourceSerifFontFamily() = FontFamily(
    Font(Res.font.source_serif_regular, weight = FontWeight.Normal),
    Font(Res.font.source_serif_bold, weight = FontWeight.Bold),
    Font(Res.font.source_serif_semibold, weight = FontWeight.SemiBold),
)

@Composable
fun MonumentGroteskFontFamily() = FontFamily(
    Font(Res.font.monument_grotesk_regular, weight = FontWeight.Normal),
    Font(Res.font.monument_grotesk_bold, weight = FontWeight.Bold),
)

@Composable
fun DeficitTypography(): Typography {
    val sourceSerif = SourceSerifFontFamily()
    val monumentGrotesk = MonumentGroteskFontFamily()

    return Typography(
        displayLarge = TextStyle(
            fontFamily = sourceSerif,
            fontWeight = FontWeight.Bold,
            fontSize = 48.sp,
        ),
        displayMedium = TextStyle(
            fontFamily = sourceSerif,
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp,
        ),
        displaySmall = TextStyle(
            fontFamily = sourceSerif,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
        ),
        headlineLarge = TextStyle(
            fontFamily = sourceSerif,
            fontWeight = FontWeight.SemiBold,
            fontSize = 32.sp,
        ),
        headlineMedium = TextStyle(
            fontFamily = sourceSerif,
            fontWeight = FontWeight.SemiBold,
            fontSize = 28.sp,
        ),
        headlineSmall = TextStyle(
            fontFamily = sourceSerif,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
        ),
        titleLarge = TextStyle(
            fontFamily = monumentGrotesk,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
        ),
        titleMedium = TextStyle(
            fontFamily = monumentGrotesk,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
        ),
        titleSmall = TextStyle(
            fontFamily = monumentGrotesk,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
        ),
        bodyLarge = TextStyle(
            fontFamily = monumentGrotesk,
            fontWeight = FontWeight.Normal,
            fontSize = 20.sp,
        ),
        bodyMedium = TextStyle(
            fontFamily = monumentGrotesk,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
        ),
        bodySmall = TextStyle(
            fontFamily = monumentGrotesk,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
        ),
        labelLarge = TextStyle(
            fontFamily = monumentGrotesk,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
        ),
        labelMedium = TextStyle(
            fontFamily = monumentGrotesk,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
        ),
        labelSmall = TextStyle(
            fontFamily = monumentGrotesk,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
        ),
    )
}
