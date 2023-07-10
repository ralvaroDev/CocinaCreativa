package pe.ralvaro.cocinacreativa.data.domain

data class FoodData(
    val foodDishes: List<FoodDishes>,
    val filters: List<Filters>
)

data class FoodDishes(
    val id: String,
    val foodName: String,
    val imageUrl: String,
    val coordinates: String,
    val description: String,
    val filterTags: List<String>
)

data class Filters(
    val id: Int,
    val name: String,
    val category: String,
    val tag: String,
    val color: String
)

