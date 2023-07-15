package pe.ralvaro.cocinacreativa.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * The Data Access Object for the [FoodKFtsEntity] class
 */
@Dao
interface FoodKFtsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(foodKFtsEntity: List<FoodKFtsEntity>)

    @Query("SELECT food_id from food_key_filters WHERE food_key_filters MATCH :query")
    suspend fun getAllFoodDishes(query: String): List<String>

}