package feature.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.di.DispatcherProvider
import core.model.journal.JournalEntry
import core.util.now
import core.data.repository.JournalRepository
import feature.overview.OverviewViewModelEvent.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

class OverviewViewModel(
    private val journalRepository: JournalRepository,
    private val dispatcherProvider: DispatcherProvider,
) : ViewModel() {
    private val _state = MutableStateFlow(OverviewViewModelState())
    val state: StateFlow<OverviewViewModelState> = _state

    private var journalEntriesJob: Job? = null

    init {
        onEvent(GetJournalEntries)
    }

    fun onEvent(event: OverviewViewModelEvent) {
        when (event) {
            GetJournalEntries -> getJournalEntries()
            is SetDate -> setDate(epochMilliseconds = event.epochMilliseconds)
            ToggleDatePicker -> toggleDatePickerVisible()
        }
    }

    private fun getJournalEntries() {
        val start = state.value.date.date.atStartOfDayIn(TimeZone.currentSystemDefault())
        val end = start.plus(
            value = 1,
            unit = DateTimeUnit.DAY,
            timeZone = TimeZone.currentSystemDefault()
        )

        val startDate = start.toLocalDateTime(TimeZone.currentSystemDefault())
        val endDate = end.toLocalDateTime(TimeZone.currentSystemDefault())

        journalEntriesJob?.cancel()
        journalEntriesJob = viewModelScope.launch(dispatcherProvider.io) {
            journalRepository
                .getByDateRange(start = startDate, end = endDate)
                .collect { journalEntries ->
                    val totalCarbs = journalEntries.sumOf { it.carbs }.toInt()
                    val totalProteins = journalEntries.sumOf { it.proteins }.toInt()
                    val totalFats = journalEntries.sumOf { it.fats }.toInt()
                    val totalCalories = journalEntries.sumOf { it.calories }.toInt()
                    _state.update {
                        it.copy(
                            journalEntries = journalEntries,
                            totalCarbs = totalCarbs.toString(),
                            totalProteins = totalProteins.toString(),
                            totalFats = totalFats.toString(),
                            totalCalories = totalCalories.toString(),
                        )
                    }
                }
        }
    }

    private fun setDate(epochMilliseconds: Long) {
        val newDate = Instant
            .fromEpochMilliseconds(epochMilliseconds = epochMilliseconds)
            .toLocalDateTime(timeZone = TimeZone.currentSystemDefault())

        _state.update { it.copy(date = newDate, datePickerVisible = false) }
        getJournalEntries()
    }

    private fun toggleDatePickerVisible() {
        val visible = state.value.datePickerVisible
        _state.update { it.copy(datePickerVisible = !visible) }
    }
}

data class OverviewViewModelState(
    val date: LocalDateTime = LocalDateTime.now(),
    val datePickerVisible: Boolean = false,
    val totalCarbs: String = "",
    val totalProteins: String = "",
    val totalFats: String = "",
    val totalCalories: String = "",
    val journalEntries: List<JournalEntry> = emptyList(),
)

sealed interface OverviewViewModelEvent {
    data object GetJournalEntries : OverviewViewModelEvent
    data object ToggleDatePicker : OverviewViewModelEvent
    data class SetDate(val epochMilliseconds: Long) : OverviewViewModelEvent
}
