package pe.ralvaro.cocinacreativa.data.network.models

import com.google.gson.annotations.SerializedName
import org.jetbrains.annotations.TestOnly
import pe.ralvaro.cocinacreativa.data.database.FoodKFtsEntity
import pe.ralvaro.cocinacreativa.data.domain.Filters
import pe.ralvaro.cocinacreativa.data.domain.FoodData
import pe.ralvaro.cocinacreativa.data.domain.FoodDishes

data class FoodDataNetwork(
    @SerializedName("food_dishes") val foodDishes: List<FoodDishesNetwork>,
    val filters: List<FiltersNetwork>
)

data class FoodDishesNetwork(
    val id: String,
    @SerializedName("food_name") val foodName: String,
    @SerializedName("image_url") val imageUrl: String,
    val coordinates: String,
    val description: String,
    @SerializedName("filter_tags") val filterTags: List<String>
)

data class FiltersNetwork(
    val id: Int,
    val name: String,
    val category: String,
    val tag: String,
    val color: String
)

fun FoodDataNetwork.toDatabaseModel() : List<FoodKFtsEntity> {
    return foodDishes.map {
        FoodKFtsEntity(
            foodId = it.id,
            name = it.foodName,
            description = it.description
        )
    }
}

fun FoodDataNetwork.toDomainModel(): FoodData {
    val foodDishes = foodDishes.map { it.toFoodDishes() }
    val filters = filters.map { it.toFilters() }
    return FoodData(foodDishes, filters)
}

private fun FoodDishesNetwork.toFoodDishes(): FoodDishes {
    return FoodDishes(id, foodName, imageUrl, coordinates, description, filterTags)
}

@TestOnly
fun FoodDishesNetwork.toFoodDishesTest(): FoodDishes {
    return FoodDishes(id, foodName, imageUrl, coordinates, description, filterTags)
}

private fun FiltersNetwork.toFilters(): Filters {
    return Filters(id, name, category, tag, color)
}

@TestOnly
fun FiltersNetwork.toFiltersTest(): Filters {
    return Filters(id, name, category, tag, color)
}
