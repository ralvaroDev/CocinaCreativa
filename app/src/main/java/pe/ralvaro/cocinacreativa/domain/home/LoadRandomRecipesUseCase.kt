package pe.ralvaro.cocinacreativa.domain.home

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pe.ralvaro.cocinacreativa.data.model.RecommendedRecipesContainer
import pe.ralvaro.cocinacreativa.data.model.toRecommendedRecipesContainer
import pe.ralvaro.cocinacreativa.data.repository.food.DefaultFoodRepository
import pe.ralvaro.cocinacreativa.di.IoDispatcher
import pe.ralvaro.cocinacreativa.domain.FlowUseCase
import pe.ralvaro.cocinacreativa.util.Result
import javax.inject.Inject

class LoadRandomRecipesUseCase @Inject constructor(
    private val defaultFoodRepository: DefaultFoodRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : FlowUseCase<Unit, RecommendedRecipesContainer>(dispatcher) {
    override fun execute(parameters: Unit): Flow<Result<RecommendedRecipesContainer>> {
        return defaultFoodRepository.getFoods().map { completeList ->
            Result.Success(
                completeList.sortedWith(
                    compareByDescending {
                        it.id.replace(
                            regex = ("\\D+").toRegex(),
                            replacement = ""
                        ).toInt()
                    }
                )
                    .shuffled()
                    .take(10).toRecommendedRecipesContainer()
            )
        }
    }
}