package pe.ralvaro.cocinacreativa.domain.home

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.jetbrains.annotations.VisibleForTesting
import pe.ralvaro.cocinacreativa.data.model.FastestRecipesContainer
import pe.ralvaro.cocinacreativa.data.model.toFastestRecipesContainer
import pe.ralvaro.cocinacreativa.data.repository.food.DefaultFoodRepository
import pe.ralvaro.cocinacreativa.di.IoDispatcher
import pe.ralvaro.cocinacreativa.domain.FlowUseCase
import pe.ralvaro.cocinacreativa.util.Result
import pe.ralvaro.cocinacreativa.util.shuffledIf
import javax.inject.Inject

// We sort ascending by time and in case of equals by id, we take the first 20, then we shuffle and return the first 10
class LoadRecipesByMinorTimeUseCase @Inject constructor(
    private val defaultFoodRepository: DefaultFoodRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : FlowUseCase<Unit, FastestRecipesContainer>(dispatcher) {

    @VisibleForTesting
    var isTest: Boolean = false
    override fun execute(parameters: Unit): Flow<Result<FastestRecipesContainer>> {
        return defaultFoodRepository.getFoods().map { completeList ->
            Result.Success(
                completeList.sortedWith(
                    compareBy(
                        ({ it.time }),
                        ({ it.id.replace(("\\D+").toRegex(), "").toInt() })
                    )
                ).take(20)
                    .shuffledIf(isTest)
                    .take(10).toFastestRecipesContainer()
            )
        }
    }
}