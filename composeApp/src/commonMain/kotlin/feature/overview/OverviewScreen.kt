package feature.overview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import core.ui.components.layout.DeficitHeader
import core.ui.components.picker.DeficitDatePicker
import core.ui.theme.DeficitTheme
import core.util.LocalDateTimeFormat
import deficit.composeapp.generated.resources.Res
import deficit.composeapp.generated.resources.calendar
import koinViewModel
import kotlinx.datetime.format
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
private fun OverviewScreenPreview() = DeficitTheme {
    OverviewScreenContent(state = OverviewViewModelState(), onEvent = {})
}

@Composable
fun OverviewScreen() {
    val viewModel: OverviewViewModel = koinViewModel<OverviewViewModel>()
    val state by viewModel.state.collectAsState()

    OverviewScreenContent(state = state, onEvent = viewModel::onEvent)
}

@Composable
private fun OverviewScreenContent(
    state: OverviewViewModelState,
    onEvent: (OverviewViewModelEvent) -> Unit,
) {
    DeficitDatePicker(
        visible = state.datePickerVisible,
        onConfirm = { selectedMillis -> onEvent(OverviewViewModelEvent.SetDate(selectedMillis)) },
        onDismissRequest = { onEvent(OverviewViewModelEvent.ToggleDatePicker) }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Transparent)
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        DeficitHeader(
            title = "Overview",
            trailingContent = {
                IconButton(onClick = { onEvent(OverviewViewModelEvent.ToggleDatePicker) }) {
                    Icon(
                        painter = painterResource(Res.drawable.calendar),
                        contentDescription = null,
                    )
                }
            }
        )

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = state.date.format(LocalDateTimeFormat.date),
            style = typography.displaySmall,
            textAlign = TextAlign.Start,
        )

        MacroRow(title = "Carbs:", value = state.totalCarbs)

        MacroRow(title = "Proteins:", value = state.totalProteins)

        MacroRow(title = "Fats:", value = state.totalFats)

        HorizontalDivider(modifier = Modifier.fillMaxWidth())

        MacroRow(title = "Calories:", value = state.totalCalories)
    }
}

@Composable
private fun MacroRow(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
) = Row(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically,
    content = {
        Text(text = title, style = MaterialTheme.typography.headlineMedium)
        Text(text = value, style = MaterialTheme.typography.titleLarge)
    }
)
