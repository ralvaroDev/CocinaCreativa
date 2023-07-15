package pe.ralvaro.cocinacreativa.domain.search

import pe.ralvaro.cocinacreativa.data.model.Filter
import pe.ralvaro.cocinacreativa.data.model.FoodDish
import timber.log.Timber

class FoodFilterMatcher(filters: List<Filter>) {

    // This contains the filters grouped by category eg. ingredient or food_type
    private val tagsByCategory = filters.groupBy { it.category }

    fun matches(item: FoodDish): Boolean {
        // Here we get filters that contains the current food
        val foodTags = item.filterTags

        // Then for every category in [tagsByCategory] we compare if some of the filter
        // tag selected by the user are in common with the current food tags
        // - case yes it means that the food belongs to the selected filter
        // - case no, the food doesn't match with the user selected filter


        //Este incluye el plato si al menos contiene un filtro -> Inclusive
        tagsByCategory.forEach { (_, tagsInCategory) ->
            if (foodTags.intersect(tagsInCategory.toSet()).isEmpty()) {
                return false
            }
        }
        /*
        // Este solo incluye si tiene todos los ingredientes marcados -> Exclusive
        tagsByCategory.forEach { (_, tagsInCategory) ->
            if (foodTags.intersect(tagsInCategory.toSet()) != tagsInCategory.toSet()) {
                return false
            }
        }
        */
        return true
    }
}