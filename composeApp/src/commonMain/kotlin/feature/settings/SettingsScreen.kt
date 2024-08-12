package feature.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import core.ui.components.button.DeficitButton
import core.ui.components.layout.DeficitHeader
import core.ui.components.modifier.noRippleClickable
import core.ui.components.textfield.DeficitTextField
import core.ui.theme.DeficitTheme
import feature.settings.SettingsViewModelEvent.InputWeight
import feature.settings.SettingsViewModelEvent.SaveWeight
import koinViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
private fun SettingsScreenPreview() = DeficitTheme {
    SettingsScreenContent(state = SettingsViewModelState(), onEvent = {})
}

@Composable
fun SettingsScreen() {
    val viewModel: SettingsViewModel = koinViewModel<SettingsViewModel>()
    val state by viewModel.state.collectAsState()

    SettingsScreenContent(state = state, onEvent = viewModel::onEvent)
}

@Composable
private fun SettingsScreenContent(
    state: SettingsViewModelState,
    onEvent: (SettingsViewModelEvent) -> Unit,
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .noRippleClickable { focusManager.clearFocus() }
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        DeficitHeader(title = "Settings", trailingContent = {})

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Weight:",
                style = MaterialTheme.typography.headlineMedium,
            )

            Text(
                text = "${state.latestWeight?.weight ?: 0.0}",
                style = MaterialTheme.typography.titleLarge,
            )
        }

        DeficitTextField(
            label = "Weight",
            value = state.inputWeight,
            placeholder = "Weight",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Next,
            ),
            onDone = { focusManager.clearFocus() },
            onValueChange = { weight -> onEvent(InputWeight(weight = weight)) },
        )

        DeficitButton(
            text = "Save",
            onClick = {
                onEvent(SaveWeight)
                focusManager.clearFocus()
            }
        )
    }
}
