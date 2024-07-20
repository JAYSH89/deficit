package feature.overview.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview


@Preview
@Composable
private fun OverviewScreenPreview() {
    OverviewScreenContent()
}

@Composable
fun OverviewScreen() {
    OverviewScreenContent()
}

@Composable
private fun OverviewScreenContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Overview",
            style = MaterialTheme.typography.h4,
        )
    }
}
