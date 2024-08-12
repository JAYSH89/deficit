package feature.journal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.di.DispatcherProvider
import core.model.food.Food
import core.model.journal.JournalEntry
import core.util.now
import core.data.repository.FoodRepository
import core.data.repository.JournalRepository
import feature.journal.JournalOverviewViewModelEvent.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

class JournalOverviewViewModel(
    private val journalRepository: JournalRepository,
    private val foodRepository: FoodRepository,
    private val dispatcherProvider: DispatcherProvider,
) : ViewModel() {

    private val _state = MutableStateFlow(JournalOverviewViewModelState())
    val state: StateFlow<JournalOverviewViewModelState> = _state

    private var journalEntriesJob: Job? = null

    init {
        onEvent(GetAllFood)
        onEvent(GetJournalEntries)
    }

    fun onEvent(event: JournalOverviewViewModelEvent) {
        when (event) {
            GetAllFood -> getAllFood()
            GetJournalEntries -> getJournalEntries()
            ToggleDatePicker -> toggleDatePickerVisible()
            is SetDate -> setDate(epochMilliseconds = event.epochMilliseconds)
            is FilterFood -> _state.update { it.copy(foodQuery = event.query) }
            is SelectFood -> _state.update { it.copy(selectedFood = event.food, foodQuery = "") }
            UnSelectFood -> _state.update { it.copy(selectedFood = null) }
            is SetTime -> setTime(hour = event.hour, minute = event.minute)
            ToggleTimePicker -> toggleTimePickerVisible()
            is SaveJournalEntry -> saveJournalEntry()
            is DeleteJournalEntry -> deleteJournalEntry(id = event.id)
            is SetServing -> _state.update { it.copy(serving = event.serving) }
        }
    }

    private fun getAllFood() {
        state.combine(
            foodRepository.getAllFood()
        ) { newState, allFood ->
            val filteredFood = allFood.filter { food ->
                food.name.lowercase().contains(newState.foodQuery.lowercase())
            }

            val result = if (newState.foodQuery.isBlank())
                emptyList()
            else
                filteredFood

            newState.copy(foods = result)
        }.onEach { updatedState ->
            _state.value = updatedState
        }.launchIn(scope = viewModelScope + dispatcherProvider.io)
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
                    _state.update { it.copy(journalEntries = journalEntries) }
                }
        }
    }

    private fun toggleDatePickerVisible() {
        val visible = state.value.datePickerVisible
        _state.update { it.copy(datePickerVisible = !visible) }
    }

    private fun toggleTimePickerVisible() {
        val timePickerVisible = state.value.timePickerVisible
        _state.update { it.copy(timePickerVisible = !timePickerVisible) }
    }

    private fun setDate(epochMilliseconds: Long) {
        val newDate = Instant
            .fromEpochMilliseconds(epochMilliseconds = epochMilliseconds)
            .toLocalDateTime(timeZone = TimeZone.currentSystemDefault())

        _state.update { it.copy(date = newDate, datePickerVisible = false) }
        getJournalEntries()
    }

    private fun setTime(hour: Int, minute: Int) {
        val updatedDate = LocalDateTime(
            year = state.value.date.year,
            month = state.value.date.month,
            dayOfMonth = state.value.date.dayOfMonth,
            hour = hour,
            minute = minute,
            second = 0,
            nanosecond = 0,
        )
        _state.update { it.copy(date = updatedDate, timePickerVisible = false) }
    }

    private fun saveJournalEntry() {
        val selectedFood = state.value.selectedFood ?: return
        val foodAmount = state.value.serving.toDoubleOrNull() ?: return
        val updatedDate = LocalDateTime(
            year = state.value.date.year,
            month = state.value.date.month,
            dayOfMonth = state.value.date.dayOfMonth,
            hour = state.value.date.hour,
            minute = state.value.date.minute,
            second = 0,
            nanosecond = 0,
        )

        val journalEntry = JournalEntry(
            food = selectedFood,
            serving = foodAmount,
            date = updatedDate,
        )

        viewModelScope.launch {
            journalRepository.insertJournalEntry(journalEntry = journalEntry)
            onEvent(UnSelectFood)
        }
    }

    private fun deleteJournalEntry(id: Long) {
        viewModelScope.launch {
            journalRepository.deleteJournalEntry(id = id)
        }
    }
}

data class JournalOverviewViewModelState(
    val date: LocalDateTime = LocalDateTime.now(),
    val timePickerVisible: Boolean = false,
    val foods: List<Food> = emptyList(),
    val selectedFood: Food? = null,
    val serving: String = "",
    val foodQuery: String = "",
    val datePickerVisible: Boolean = false,
    val journalEntries: List<JournalEntry> = emptyList(),
)

sealed interface JournalOverviewViewModelEvent {
    data object GetJournalEntries : JournalOverviewViewModelEvent
    data object GetAllFood : JournalOverviewViewModelEvent
    data class SelectFood(val food: Food) : JournalOverviewViewModelEvent
    data object UnSelectFood : JournalOverviewViewModelEvent
    data class FilterFood(val query: String) : JournalOverviewViewModelEvent
    data class SetDate(val epochMilliseconds: Long) : JournalOverviewViewModelEvent
    data object ToggleDatePicker : JournalOverviewViewModelEvent
    data object ToggleTimePicker : JournalOverviewViewModelEvent
    data class SetTime(val hour: Int, val minute: Int) : JournalOverviewViewModelEvent
    data class SetServing(val serving: String) : JournalOverviewViewModelEvent
    data object SaveJournalEntry : JournalOverviewViewModelEvent
    data class DeleteJournalEntry(val id: Long) : JournalOverviewViewModelEvent
}
