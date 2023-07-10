package pe.ralvaro.cocinacreativa.data.network

import pe.ralvaro.cocinacreativa.data.network.models.FoodDataNetwork
import retrofit2.http.GET

/**
 * Used to download food data from the API
 */
interface RecipeApiService {

    @GET("food_data")
    suspend fun getFoodData() : FoodDataNetwork

}