package feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.data.repository.ProfileRepository
import core.model.weight.Weight
import core.util.now
import feature.settings.SettingsViewModelEvent.*
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime

class SettingsViewModel(
    private val profileRepository: ProfileRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(SettingsViewModelState())
    val state: StateFlow<SettingsViewModelState> = _state

    init {
        onEvent(GetLatestWeight)
    }

    fun onEvent(event: SettingsViewModelEvent) {
        when (event) {
            GetLatestWeight -> getLatestWeight()
            is SaveWeight -> saveWeight()
            is InputWeight -> inputWeight(weight = event.weight)
        }
    }

    private fun inputWeight(weight: String) {
        _state.update { it.copy(inputWeight = weight) }
    }

    private fun getLatestWeight() {
        profileRepository
            .getLatestWeight()
            .onEach { weight -> _state.update { it.copy(latestWeight = weight) } }
            .launchIn(viewModelScope)
    }

    private fun saveWeight() {
        val weight = state.value.inputWeight.toDoubleOrNull() ?: return

        viewModelScope.launch {
            profileRepository.insertWeight(
                weight = Weight(weight = weight, date = LocalDateTime.now()),
            )
        }
    }
}

data class SettingsViewModelState(
    val inputWeight: String = "",
    val latestWeight: Weight? = null,
)

sealed interface SettingsViewModelEvent {
    data object GetLatestWeight : SettingsViewModelEvent
    data object SaveWeight : SettingsViewModelEvent
    data class InputWeight(val weight: String) : SettingsViewModelEvent
}
