package pe.ralvaro.cocinacreativa.domain.home

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.jetbrains.annotations.VisibleForTesting
import pe.ralvaro.cocinacreativa.data.model.LastRecipesContainer
import pe.ralvaro.cocinacreativa.data.model.toLastRecipesContainer
import pe.ralvaro.cocinacreativa.data.repository.food.DefaultFoodRepository
import pe.ralvaro.cocinacreativa.di.IoDispatcher
import pe.ralvaro.cocinacreativa.domain.FlowUseCase
import pe.ralvaro.cocinacreativa.util.Result
import pe.ralvaro.cocinacreativa.util.shuffledIf
import javax.inject.Inject

// Sort descending by id, take top 40, then shuffle and return top 10
class LoadLastRecipesUseCase @Inject constructor(
    private val defaultFoodRepository: DefaultFoodRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : FlowUseCase<Unit, LastRecipesContainer>(dispatcher) {
    @VisibleForTesting
    var isTest: Boolean = false
    override fun execute(parameters: Unit): Flow<Result<LastRecipesContainer>> {
        return defaultFoodRepository.getFoods().map { completeList ->
            Result.Success(
                completeList.sortedWith(
                    compareByDescending {
                        it.id.replace(
                            regex = ("\\D+").toRegex(),
                            replacement = ""
                        ).toInt()
                    }
                ).take(20)
                    .shuffledIf(isTest)
                    .take(10).toLastRecipesContainer()
            )
        }
    }
}