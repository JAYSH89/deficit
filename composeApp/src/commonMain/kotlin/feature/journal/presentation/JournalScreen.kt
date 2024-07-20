package feature.journal.presentation

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
private fun JournalScreenPreview() {
    JournalScreenContent()
}

@Composable
fun JournalScreen() {
    JournalScreenContent()
}

@Composable
private fun JournalScreenContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        ) {
        Text(
            text = "Journal",
            style = MaterialTheme.typography.h4,
        )
    }
}