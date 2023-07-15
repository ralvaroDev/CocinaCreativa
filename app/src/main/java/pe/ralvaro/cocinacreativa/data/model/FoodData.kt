package pe.ralvaro.cocinacreativa.data.model

import pe.ralvaro.cocinacreativa.util.toHourMinuteFormat

data class FoodData(
    val foodDishes: List<FoodDish>,
    val filters: List<Filter>
)

data class FoodDish(
    val id: String,
    val foodName: String,
    val imageUrl: String,
    val coordinates: String,
    val description: String,
    val steps:String,
    val time: String,
    val placeName: String,
    val rate: String,
    val filterTags: List<Filter>
)

data class Filter(
    val id: Int,
    val name: String,
    val category: String,
    val tag: String,
    val color: String
) {

    companion object {
        /** Category value for topic tags */
        const val CATEGORY_INGREDIENT = "ingredient"
        const val CATEGORY_FOOD_TYPE = "food_type"
    }

    /** Only IDs are used for equality. */
    override fun equals(other: Any?): Boolean = this === other || (other is Filter && other.id == id)

    override fun hashCode(): Int {
        return id.hashCode()
    }

}

fun List<FoodDish>.toLastRecipesContainer(): LastRecipesContainer {
    return LastRecipesContainer(toHomeRecipe())
}

fun List<FoodDish>.toFastestRecipesContainer(): FastestRecipesContainer {
    return FastestRecipesContainer(toHomeRecipe())
}

fun List<FoodDish>.toRecommendedRecipesContainer(): RecommendedRecipesContainer {
    return RecommendedRecipesContainer(toHomeRecipe())
}

private fun List<FoodDish>.toHomeRecipe(): List<HomeRecipe> {
    return map {
        HomeRecipe(
            it.id,
            it.foodName,
            it.imageUrl,
            it.steps.split("\n").size,
            it.time.toIntOrNull()?.toHourMinuteFormat() ?: "",
            it.rate
        )
    }
}