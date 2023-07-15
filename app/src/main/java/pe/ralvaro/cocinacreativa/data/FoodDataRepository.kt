package pe.ralvaro.cocinacreativa.data

import pe.ralvaro.cocinacreativa.data.database.FoodDatabase
import pe.ralvaro.cocinacreativa.data.database.FoodKFtsEntity
import pe.ralvaro.cocinacreativa.data.model.FoodData
import pe.ralvaro.cocinacreativa.data.network.models.FoodDataNetwork
import pe.ralvaro.cocinacreativa.data.network.models.toDatabaseModel
import pe.ralvaro.cocinacreativa.data.network.models.toDomainModel
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

/**
 * Single point of access to food data for the presentation layer
 * This ensure one type of source data, remote or local.
 */
@Singleton
open class FoodDataRepository @Inject constructor(
    @Named("remoteDataSource") private val remoteFoodDataSource: FoodDataSource,
    @Named("localDataSource") private val localFoodDataSource: FoodDataSource,
    private val foodDatabase: FoodDatabase
) {

    // Represents the food cache data
    private var foodDataCache: FoodData? = null

    // Key to prevent race conditions
    private val loadFoodDataLocker = Any()

    /**
     * This function only asks to download the remote data
     * @throws Exception when request fails or there is no internet connection
     */
    suspend fun downloadFoodFromServer() {
        val food = try {
            // Try to get data from server
            remoteFoodDataSource.remoteData()
        } catch (e: Exception) {
            Timber.e(e,"Connection failed, no data from remote")
            null
        }

        if (food == null) {
            val e = Exception("Remote return no data")
            throw e
        }

        /*
        * This ensure that only one sub process access to this code, to avoid inconsistent data
        * because of concurrency
        * */
        synchronized(loadFoodDataLocker) {
            // Network data success
            foodDataCache = food.toDomainModel()
            updateFoodDataForSearch(food.toDatabaseModel())
        }
    }

    // Returns the dummy data when remote doesn't work
    private fun loadLocalDummyData(): FoodDataNetwork {
        val localData = localFoodDataSource.localData()!!
        updateFoodDataForSearch(localData.toDatabaseModel())
        return localData
    }

    // Return food data from cache
    fun getOfflineFoodData() : FoodData {
        synchronized(loadFoodDataLocker) {
            val cacheData = foodDataCache ?: loadLocalDummyData().toDomainModel()
            foodDataCache = cacheData
            return cacheData
        }
    }

    // Update the room data for searching
    private fun updateFoodDataForSearch(foodKeysFilters: List<FoodKFtsEntity>) {
        foodDatabase.foodKFtsDao().insertAll(foodKeysFilters)
    }

}