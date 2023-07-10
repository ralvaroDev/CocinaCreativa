package pe.ralvaro.cocinacreativa.data

import pe.ralvaro.cocinacreativa.data.network.RecipeApiService
import pe.ralvaro.cocinacreativa.data.network.models.FoodDataNetwork
import pe.ralvaro.cocinacreativa.util.NetworkUtils
import timber.log.Timber
import javax.inject.Inject

interface FoodDataSource {
    suspend fun remoteData(): FoodDataNetwork?
    fun localData(): FoodDataNetwork?
}

/**
 * Download food data parsed by Retrofit with Gson Converter
 */
open class RemoteFoodDataSource @Inject constructor(
    private val recipeApiService: RecipeApiService,
    private val networkUtils: NetworkUtils
) : FoodDataSource {

    private suspend fun getRemoteFoodData(): FoodDataNetwork {
        return recipeApiService.getFoodData()
    }

    /**
     * Returns the remote data or null if there is no internet connection
     */
    override suspend fun remoteData(): FoodDataNetwork? {
        return if (!networkUtils.hasNetworkConnection()) {
            Timber.d("No internet connection")
            null
        } else getRemoteFoodData()
    }

    override fun localData(): FoodDataNetwork? {
        return null
    }

}