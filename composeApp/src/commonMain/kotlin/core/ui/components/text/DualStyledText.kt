package core.ui.components.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

@Composable
fun DualStyledText(
    modifier: Modifier = Modifier,
    leadingText: String,
    leadingTextStyle: TextStyle = MaterialTheme.typography.titleMedium,
    trailingText: String,
    trailingTextStyle: TextStyle = MaterialTheme.typography.bodySmall,
) {
    Text(
        modifier = modifier,
        text = buildAnnotatedString {
            withStyle(style = leadingTextStyle.toSpanStyle()) {
                append(leadingText)
            }

            append(" ")

            withStyle(style = trailingTextStyle.toSpanStyle()) {
                append(trailingText)
            }
        },
    )
}