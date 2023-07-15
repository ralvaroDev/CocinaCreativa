package pe.ralvaro.cocinacreativa.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pe.ralvaro.cocinacreativa.data.model.FoodDish
import pe.ralvaro.cocinacreativa.util.FakeDefaultFoodRepository
import pe.ralvaro.cocinacreativa.util.TestData

/**
 * This repo overrides getFood function
 */
class DefaultSecondFakeRepo(dispatcher: CoroutineScope) :
    FakeDefaultFoodRepository(dispatcher) {
    override fun getFoods(): Flow<List<FoodDish>> = flow {
        emit(
            listOf(
                TestData.foodDish3,
                TestData.foodDish2,
                TestData.foodDish1
            )
        )
    }
}