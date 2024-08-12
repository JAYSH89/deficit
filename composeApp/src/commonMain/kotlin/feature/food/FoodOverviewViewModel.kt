package feature.food

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.di.DispatcherProvider
import core.model.food.Food
import core.data.repository.FoodRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FoodOverviewViewModel(
    private val repository: FoodRepository,
    private val dispatcher: DispatcherProvider,
) : ViewModel() {

    private val _state = MutableStateFlow(FoodOverviewViewModelState())
    val state: StateFlow<FoodOverviewViewModelState> = _state

    private var foodJob: Job? = null

    init {
        onEvent(event = FoodOverviewViewModelEvent.GetFood)
    }

    fun onEvent(event: FoodOverviewViewModelEvent) {
        when (event) {
            FoodOverviewViewModelEvent.GetFood -> {
                getFood()
            }

            is FoodOverviewViewModelEvent.SelectFood -> {
                _state.update { it.copy(selectedFoodId = event.id) }
            }

            FoodOverviewViewModelEvent.OnNavigate -> {
                _state.update { it.copy(selectedFoodId = null, createFood = false) }
            }

            FoodOverviewViewModelEvent.CreateFood -> {
                _state.update { it.copy(createFood = true) }
            }

            is FoodOverviewViewModelEvent.FilterFood -> {
                filterFood(query = event.query)
            }
        }
    }

    private fun getFood() {
        foodJob?.cancel()
        foodJob = viewModelScope.launch {
            repository
                .getAllFood()
                .map { allFood ->
                    allFood
                        .sortedBy { it.name.lowercase() }
                        .filter { it.name.lowercase().contains(state.value.foodQuery.lowercase()) }
                }
                .collect { allFood -> _state.update { it.copy(food = allFood) } }
        }
    }

    private fun filterFood(query: String) {
        _state.update { it.copy(foodQuery = query) }
        getFood()
    }
}

data class FoodOverviewViewModelState(
    val selectedFoodId: Long? = null,
    val createFood: Boolean = false,
    val food: List<Food> = emptyList(),
    val foodQuery: String = "",
)

sealed interface FoodOverviewViewModelEvent {
    data class SelectFood(val id: Long) : FoodOverviewViewModelEvent
    data object CreateFood : FoodOverviewViewModelEvent
    data class FilterFood(val query: String) : FoodOverviewViewModelEvent
    data object OnNavigate : FoodOverviewViewModelEvent
    data object GetFood : FoodOverviewViewModelEvent
}
