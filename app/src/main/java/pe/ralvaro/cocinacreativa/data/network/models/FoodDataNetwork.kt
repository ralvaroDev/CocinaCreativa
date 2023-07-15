package pe.ralvaro.cocinacreativa.data.network.models

import androidx.annotation.VisibleForTesting
import com.google.gson.annotations.SerializedName
import pe.ralvaro.cocinacreativa.data.database.FoodKFtsEntity
import pe.ralvaro.cocinacreativa.data.model.Filter
import pe.ralvaro.cocinacreativa.data.model.FoodData
import pe.ralvaro.cocinacreativa.data.model.FoodDish

data class FoodDataNetwork(
    @SerializedName("food_dishes") val foodDishes: List<FoodDishNetwork>,
    val filters: List<FiltersNetwork>
)

data class FoodDishNetwork(
    val id: String,
    @SerializedName("food_name") val foodName: String,
    @SerializedName("image_url") val imageUrl: String,
    val coordinates: String,
    val description: String,
    val steps: String,
    val time: String,
    val place: String,
    val rate: String,
    @SerializedName("filter_tags") val filterTags: List<String>
)

data class FiltersNetwork(
    val id: Int,
    val name: String,
    val category: String,
    val tag: String,
    val color: String
)

fun FoodDataNetwork.toDatabaseModel(): List<FoodKFtsEntity> {
    return foodDishes.map {
        FoodKFtsEntity(
            foodId = it.id,
            name = it.foodName,
            description = it.description
        )
    }
}

fun FoodDataNetwork.toDomainModel(): FoodData {
    val foodDishes = foodDishes.map { it.toFoodDishes(filters) }
    val filters = filters.map { it.toFilters() }
    return FoodData(foodDishes, filters)
}

private fun FoodDishNetwork.toFoodDishes(filters: List<FiltersNetwork>): FoodDish {
    return FoodDish(
        id = id,
        foodName = foodName,
        imageUrl = imageUrl,
        coordinates = coordinates,
        description = description,
        steps = steps,
        time = time,
        placeName = place,
        rate = rate,
        filterTags = filterTags.map { tag ->
            //Timber.d("El tag que no esta es -> $tag")
            filters.find { it.tag == tag }!!.toFilters()
        }
    )
}

@VisibleForTesting
fun FoodDishNetwork.toFoodDishesTest(filters: List<FiltersNetwork>): FoodDish {
    return FoodDish(
        id,
        foodName,
        imageUrl,
        coordinates,
        description,
        steps,
        time,
        place,
        rate,
        filterTags.map { tag ->
            filters.find { it.tag == tag }!!.toFilters()
        }
    )
}

private fun FiltersNetwork.toFilters(): Filter {
    return Filter(id, name, category, tag, color)
}

@VisibleForTesting
fun FiltersNetwork.toFiltersTest(): Filter {
    return Filter(id, name, category, tag, color)
}
