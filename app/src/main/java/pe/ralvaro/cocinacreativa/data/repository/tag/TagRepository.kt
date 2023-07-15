package pe.ralvaro.cocinacreativa.data.repository.tag

import pe.ralvaro.cocinacreativa.data.FoodDataRepository
import pe.ralvaro.cocinacreativa.data.model.Filter
import javax.inject.Inject

/**
 * Single point of access to food filters or tags
 */
open class TagRepository @Inject constructor(
    private val foodDataRepository: FoodDataRepository
) {

    fun getFilters() : List<Filter> = foodDataRepository.getOfflineFoodData().filters

}

