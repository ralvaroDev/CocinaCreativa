package pe.ralvaro.cocinacreativa.domain.search

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pe.ralvaro.cocinacreativa.data.model.Filter
import pe.ralvaro.cocinacreativa.data.model.FoodDish
import pe.ralvaro.cocinacreativa.data.repository.food.DefaultFoodRepository
import pe.ralvaro.cocinacreativa.di.IoDispatcher
import pe.ralvaro.cocinacreativa.domain.FlowUseCase
import pe.ralvaro.cocinacreativa.util.Result
import javax.inject.Inject

data class FoodSearchUseCaseParams(
    val query: String,
    val filters: List<Filter>
)

class FoodSearchUseCase @Inject constructor(
    private val defaultFoodRepository: DefaultFoodRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : FlowUseCase<FoodSearchUseCaseParams, List<FoodDish>>(dispatcher) {

    override fun execute(parameters: FoodSearchUseCaseParams): Flow<Result<List<FoodDish>>> {
        val (query, filters) = parameters

        // Initialize the matcher with user filters
        val filterMatcher = FoodFilterMatcher(filters)

        // Return the filtered foods
        return defaultFoodRepository.getFoods().map { complete ->
            val intermediateList =
                if (query.isEmpty()) complete
                else defaultFoodRepository.filterFoodsByQuery(query)

            val searchResult = intermediateList
                .filter { filterMatcher.matches(it) }
            Result.Success(searchResult)
        }
    }

}