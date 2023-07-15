package pe.ralvaro.cocinacreativa.data.model

import androidx.annotation.StringRes
import pe.ralvaro.cocinacreativa.R

data class LastRecipesContainer(
    val recipes: List<HomeRecipe>
) {
    @StringRes
    val titleId = R.string.last_recipes_section_title
}

data class FastestRecipesContainer(
    val recipes: List<HomeRecipe>
) {
    @StringRes
    val titleId = R.string.fastest_recipes_section_title
}

data class RecommendedRecipesContainer(
    val recipes: List<HomeRecipe>
) {
    @StringRes
    val titleId = R.string.recommendations_section_title
}

