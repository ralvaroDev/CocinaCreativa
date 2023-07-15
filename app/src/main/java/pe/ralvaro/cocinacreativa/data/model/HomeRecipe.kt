package pe.ralvaro.cocinacreativa.data.model

data class HomeRecipe(
    val id: String,
    val foodName: String,
    val imgUrl: String,
    val steps: Int,
    val timeWithFormat: String,
    val rate: String
)