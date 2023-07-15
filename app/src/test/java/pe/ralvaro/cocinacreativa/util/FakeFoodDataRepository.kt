package pe.ralvaro.cocinacreativa.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import pe.ralvaro.cocinacreativa.data.FoodDataRepository
import pe.ralvaro.cocinacreativa.data.repository.food.DefaultFoodRepository
import pe.ralvaro.cocinacreativa.data.repository.tag.TagRepository
import pe.ralvaro.cocinacreativa.util.FakeFoodDatabase
import pe.ralvaro.cocinacreativa.util.TestOkDataRemoteDataSource
import pe.ralvaro.cocinacreativa.util.TestOkLocalDataSource

/**
 * Class that contains RemoteSources as main provider, also it downloads remote fake data
 * -> use [pe.ralvaro.cocinacreativa.util.TestData.foodDataRemoteOK]
 */
class FakeFoodDataRepository(dispatcher: CoroutineScope) : FoodDataRepository(
    remoteFoodDataSource = TestOkDataRemoteDataSource(),
    localFoodDataSource = TestOkLocalDataSource(),
    foodDatabase = FakeFoodDatabase()
) {
    // Here also we can override the getOffline method
    init {
        dispatcher.launch {
            super.downloadFoodFromServer()
        }
    }
}

class FakeTagRepository(dispatcher: CoroutineScope) :
    TagRepository(FakeFoodDataRepository(dispatcher))

/**
 * This repo doesn't allow test with database
 */
open class FakeDefaultFoodRepository(dispatcher: CoroutineScope) :
    DefaultFoodRepository(FakeFoodDataRepository(dispatcher), FakeFoodDatabase())