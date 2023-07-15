package pe.ralvaro.cocinacreativa.domain.search

import kotlinx.coroutines.CoroutineDispatcher
import pe.ralvaro.cocinacreativa.data.model.Filter
import pe.ralvaro.cocinacreativa.data.repository.tag.TagRepository
import pe.ralvaro.cocinacreativa.di.IoDispatcher
import pe.ralvaro.cocinacreativa.domain.UseCase
import javax.inject.Inject

private val FILTER_CATEGORIES = listOf(
    Filter.CATEGORY_INGREDIENT,
    Filter.CATEGORY_FOOD_TYPE
)

class LoadSearchFiltersUseCase @Inject constructor(
    private val tagRepository: TagRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<Unit, List<Filter>>(dispatcher) {

    // Before adding the filters we sorted in function of the categories and in case they have the
    // same category order by its [id]
    // return first ingredients, then food types
    override suspend fun execute(parameters: Unit): List<Filter> {
        return tagRepository.getFilters()
            .sortedWith(
                compareBy({ FILTER_CATEGORIES.indexOf(it.category) }, { it.id })
            )
    }

}