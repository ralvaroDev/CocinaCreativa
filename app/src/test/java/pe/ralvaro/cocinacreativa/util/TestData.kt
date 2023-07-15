package pe.ralvaro.cocinacreativa.util

import pe.ralvaro.cocinacreativa.data.model.Filter
import pe.ralvaro.cocinacreativa.data.model.FoodData
import pe.ralvaro.cocinacreativa.data.model.FoodDish
import pe.ralvaro.cocinacreativa.data.network.models.FiltersNetwork
import pe.ralvaro.cocinacreativa.data.network.models.FoodDataNetwork
import pe.ralvaro.cocinacreativa.data.network.models.FoodDishNetwork

object TestData {

    val fishIngredient = Filter(1, "Pescado", "ingredient", "ingredient_fish", "#4768FD")
    val fishNetIngredient = FiltersNetwork(1, "Pescado", "ingredient", "ingredient_fish", "#4768FD")
    val lemonIngredient = Filter(2, "Limon", "ingredient", "ingredient_lemon", "#A8D83D")
    val lemonNetIngredient = FiltersNetwork(2, "Limon", "ingredient", "ingredient_lemon", "#A8D83D")

    val beefIngredient = Filter(3, "Carne", "ingredient", "ingredient_beef", "#E13939")
    val beefNetIngredient = FiltersNetwork(3, "Carne", "ingredient", "ingredient_beef", "#E13939")

    val onionIngredient = Filter(4, "Cebolla", "ingredient", "ingredient_onion", "#FD7C47")
    val onionNetIngredient =
        FiltersNetwork(4, "Cebolla", "ingredient", "ingredient_onion", "#FD7C47")

    val chickenIngredient = Filter(5, "Pollo", "ingredient", "ingredient_chicken", "E13C47")
    val chickenNetIngredient =
        FiltersNetwork(5, "Pollo", "ingredient", "ingredient_chicken", "E13C47")


    val bottomFoodType = Filter(7, "Fondo", "food_type", "food_type_dish_fondo", "#48A7F2")
    val bottomNetFoodType =
        FiltersNetwork(7, "Fondo", "food_type", "food_type_dish_fondo", "#48A7F2")

    val entryFoodType = Filter(8, "Entrada", "food_type", "food_type_dish_entry", "#FD7C47")
    val entryNetFoodType =
        FiltersNetwork(8, "Entrada", "food_type", "food_type_dish_entry", "#FD7C47")


    val filterList = listOf(
        chickenIngredient,
        fishIngredient,
        lemonIngredient,
        beefIngredient,
        onionIngredient,
        bottomFoodType,
        entryFoodType
    )
    val filterOnlyIngredientList =
        listOf(chickenIngredient, fishIngredient, lemonIngredient, beefIngredient, onionIngredient)
    val filterOnlyFoodTypeList = listOf(bottomFoodType, entryFoodType)

    val foodDish1 = FoodDish(
        id = "1a",
        foodName = "Ceviche",
        imageUrl = "www",
        coordinates = "-12.086585,-77.046739",
        description = "Este plato es a base de pescado, originario de las zonas de la costa peruana...",
        steps = "Lavar el pescado y cortarlo en cubos de 1 a 2 cm de diámetro. Sazonar con sal y reservar.\n" +
                "Colocar los cubos de pescado en un recipiente y agregar el jugo de limón recién exprimido.",
        time = "50",
        placeName = "Lima, Peru",
        rate = "3/5",
        filterTags = listOf(
            fishIngredient, lemonIngredient, bottomFoodType
        )
    )

    val foodDishNet1 = FoodDishNetwork(
        id = "1a",
        foodName = "Ceviche",
        imageUrl = "www",
        coordinates = "-12.086585,-77.046739",
        description = "Este plato es a base de pescado, originario de las zonas de la costa peruana...",
        steps = "Lavar el pescado y cortarlo en cubos de 1 a 2 cm de diámetro. Sazonar con sal y reservar.\n" +
                "Colocar los cubos de pescado en un recipiente y agregar el jugo de limón recién exprimido.",
        time = "50",
        place = "Lima, Peru",
        rate = "3/5",
        filterTags = listOf(
            "ingredient_fish",
            "ingredient_lemon",
            "food_type_dish_fondo"
        )
    )

    val foodDish2 = FoodDish(
        id = "2b",
        foodName = "Lomo Saltado",
        imageUrl = "www",
        coordinates = "-12.092336,-77.031019",
        description = "Este plato combina la carne de res con cebolla, tomate y papas fritas...",
        steps = "Lavar el pescado y cortarlo en cubos de 1 a 2 cm de diámetro. Sazonar con sal y reservar.\n" +
                "Colocar los cubos de pescado en un recipiente y agregar el jugo de limón recién exprimido.",
        time = "70",
        placeName = "Lima, Peru",
        rate = "3/5",
        filterTags = listOf(
            beefIngredient, bottomFoodType, onionIngredient
        )
    )

    val foodDishNet2 = FoodDishNetwork(
        id = "2b",
        foodName = "Lomo Saltado",
        imageUrl = "www",
        coordinates = "-12.092336,-77.031019",
        description = "Este plato combina la carne de res con cebolla, tomate y papas fritas...",
        steps = "Lavar el pescado y cortarlo en cubos de 1 a 2 cm de diámetro. Sazonar con sal y reservar.\n" +
                "Colocar los cubos de pescado en un recipiente y agregar el jugo de limón recién exprimido.",
        time = "70",
        place = "Lima, Peru",
        rate = "3/5",
        filterTags = listOf(
            "ingredient_beef",
            "food_type_dish_fondo",
            "ingredient_onion"
        )
    )

    val foodDish3 = FoodDish(
        id = "3c",
        foodName = "Causa Rellena",
        imageUrl = "www.image.com",
        coordinates = "-12.0453566,-77.031019",
        description = "Plato a base de pollo, arroz y papas",
        steps = "Lavar el pescado y cortarlo en cubos de 1 a 2 cm de diámetro. Sazonar con sal y reservar.\n" +
                "Colocar los cubos de pescado en un recipiente y agregar el jugo de limón recién exprimido.",
        time = "80",
        placeName = "Lima, Peru",
        rate = "3/5",
        filterTags = listOf(
            entryFoodType, chickenIngredient, lemonIngredient
        )
    )

    val foodDishNet3 = FoodDishNetwork(
        id = "3c",
        foodName = "Causa Rellena",
        imageUrl = "www.image.com",
        coordinates = "-12.0453566,-77.031019",
        description = "Plato a base de pollo, arroz y papas",
        steps = "Lavar el pescado y cortarlo en cubos de 1 a 2 cm de diámetro. Sazonar con sal y reservar.\n" +
                "Colocar los cubos de pescado en un recipiente y agregar el jugo de limón recién exprimido.",
        time = "80",
        place = "Lima, Peru",
        rate = "3/5",
        filterTags = listOf(
            "food_type_dish_entry",
            "ingredient_chicken",
            "ingredient_lemon"
        )
    )

    val foodDishList = listOf(foodDish1, foodDish2, foodDish3)

    val foodsRemoteOk = listOf(foodDishNet1, foodDishNet2)
    val ingredientsRemoteOK = listOf(
        fishNetIngredient,
        lemonNetIngredient,
        bottomNetFoodType,
        beefNetIngredient,
        onionNetIngredient
    )

    // In case of change -> [filters] should only have those that correspond to the [foodDishes]
    val foodDataRemoteOK = FoodDataNetwork(
        foodDishes = foodsRemoteOk,
        filters = ingredientsRemoteOK
    )

    val foodDataRemoteOKToFoodDish = FoodData(
        foodDishes = listOf(foodDish1, foodDish2),
        filters = listOf(
            fishIngredient,
            lemonIngredient,
            bottomFoodType,
            beefIngredient,
            onionIngredient
        )
    )


    // In case of change -> [filters] should only have those that correspond to the [foodDishes]
    val foodDataLocalOK = FoodDataNetwork(
        foodDishes = listOf(foodDishNet3),
        filters = listOf(entryNetFoodType, chickenNetIngredient, lemonNetIngredient)
    )

}