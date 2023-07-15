package pe.ralvaro.cocinacreativa.ui.food_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pe.ralvaro.cocinacreativa.data.model.FoodDish
import pe.ralvaro.cocinacreativa.domain.food_detail.FindSpecificFoodUseCase
import pe.ralvaro.cocinacreativa.util.Result
import javax.inject.Inject

@HiltViewModel
class FoodDetailViewModel @Inject constructor(
    private val saveState: SavedStateHandle,
    private val findSpecificFoodUseCase: FindSpecificFoodUseCase
) : ViewModel() {

    private val _foodDishData = MutableStateFlow<Result<FoodDish>>(Result.Loading)
    val foodDishData: StateFlow<Result<FoodDish>> = _foodDishData

    init {
        viewModelScope.launch {
            
            _foodDishData.update { _ ->
                // Obtain the args passed by navigation
                saveState.get<String>("food_id").let {
                    // Update with the result
                    if (!it.isNullOrEmpty()) {
                        findSpecificFoodUseCase(it).first()
                    } else {
                        Result.Error(Exception("Error trying to get food data"))
                    }
                }
            }
            
        }
    }

}
