package pe.ralvaro.cocinacreativa.domain.search

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import pe.ralvaro.cocinacreativa.data.model.FoodDish
import pe.ralvaro.cocinacreativa.data.repository.food.DefaultFoodRepository
import pe.ralvaro.cocinacreativa.di.IoDispatcher
import pe.ralvaro.cocinacreativa.domain.UseCase
import javax.inject.Inject

/*
class LoadDefaultFoodListUseCase @Inject constructor(
    private val defaultFoodRepository: DefaultFoodRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<Unit, List<FoodDish>>(dispatcher) {
    override suspend fun execute(parameters: Unit): List<FoodDish> {
        return defaultFoodRepository.getFoods().first()
    }
}*/
