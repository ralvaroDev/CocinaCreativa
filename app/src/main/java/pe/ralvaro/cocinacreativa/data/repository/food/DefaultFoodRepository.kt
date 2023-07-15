package pe.ralvaro.cocinacreativa.data.repository.food

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pe.ralvaro.cocinacreativa.data.FoodDataRepository
import pe.ralvaro.cocinacreativa.data.database.FoodDatabase
import pe.ralvaro.cocinacreativa.data.model.FoodDish
import timber.log.Timber
import javax.inject.Inject

/**
 *  Single point of access to food data
 */
open class DefaultFoodRepository @Inject constructor(
    private val foodDataRepository: FoodDataRepository,
    private val foodDatabase: FoodDatabase
) {

    open fun getFoods(): Flow<List<FoodDish>> = flow {
        emit(foodDataRepository.getOfflineFoodData().foodDishes)
    }

    /**
     * This function execute the full text search in room with the given [query]
     * It returns a list of IDs stored in [foodId] so the we filter the full list with it
     */
    suspend fun filterFoodsByQuery(query: String): List<FoodDish> {
        foodDataRepository.getOfflineFoodData().foodDishes.let { dishList ->
            return if (query.isEmpty()) {
                dishList
            } else {
                //Timber.d("Search query is -> $query")
                // Add '*' to search all columns that starts with it
                val foodId = foodDatabase.foodKFtsDao().getAllFoodDishes(query.plus("*")).toSet()
                // Timber.d("The result of del dao is -> $foodId")
                dishList.filter { it.id in foodId }
            }
        }
    }

}