package pe.ralvaro.cocinacreativa.domain.food_detail

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pe.ralvaro.cocinacreativa.data.model.FoodDish
import pe.ralvaro.cocinacreativa.data.repository.food.DefaultFoodRepository
import pe.ralvaro.cocinacreativa.di.IoDispatcher
import pe.ralvaro.cocinacreativa.domain.FlowUseCase
import pe.ralvaro.cocinacreativa.util.Result
import javax.inject.Inject

class FindSpecificFoodUseCase @Inject constructor(
    private val defaultFoodRepository: DefaultFoodRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : FlowUseCase<String, FoodDish>(dispatcher) {

    override fun execute(parameters: String): Flow<Result<FoodDish>> {
        return defaultFoodRepository.getFoods().map { list ->
            Result.Success(list.first { it.id == parameters })
        }
    }

}
