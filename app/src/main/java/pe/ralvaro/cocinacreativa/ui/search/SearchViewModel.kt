package pe.ralvaro.cocinacreativa.ui.search

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pe.ralvaro.cocinacreativa.R
import pe.ralvaro.cocinacreativa.data.model.Filter
import pe.ralvaro.cocinacreativa.data.model.FoodDish
import pe.ralvaro.cocinacreativa.domain.search.FoodSearchUseCase
import pe.ralvaro.cocinacreativa.domain.search.FoodSearchUseCaseParams
import pe.ralvaro.cocinacreativa.domain.search.LoadSearchFiltersUseCase
import pe.ralvaro.cocinacreativa.util.Result
import pe.ralvaro.cocinacreativa.util.successOr
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    loadSearchFiltersUseCase: LoadSearchFiltersUseCase,
    private val foodSearchUseCase: FoodSearchUseCase
) : ViewModel() {

    private val _filterChips = MutableStateFlow<List<ItemFilterChip>>(emptyList())
    val filterChips: StateFlow<List<ItemFilterChip>> = _filterChips.asStateFlow()

    private val _searchResults = MutableStateFlow<List<FoodDish>>(emptyList())
    val searchResults: StateFlow<List<FoodDish>> = _searchResults.asStateFlow()

    var selectedFilters = mutableListOf<Filter>()
        private set
    private var _filters = listOf<Filter>()

    private var textQuery = ""
    private var searchJob: Job? = null

    init {

        viewModelScope.launch {
            loadSearchFiltersUseCase(Unit).successOr(emptyList()).apply {
                handleFiltersToEmit(this)
                _filters = this
            }
        }

    }



    fun onSearchQueryChanged(query: String) {
        val newQuery = query.trim().takeIf { it.length >= 2 } ?: ""
        if (textQuery != newQuery) {
            textQuery = newQuery
            executeSearch()
        }
    }

    fun updateCheckedTags(checkedTags: List<String>) {
        selectedFilters = _filters.filter { it.tag in checkedTags }.toMutableList()
        executeSearch()
    }

    private fun executeSearch() {
        // Cancel any in-flight searches
        searchJob?.cancel()

        if (textQuery.isEmpty() && selectedFilters.isEmpty()) {
            clearSearchResults()
            return
        }

        searchJob = viewModelScope.launch {
            // The user could be typing or toggling filters rapidly. Giving the search job
            // a slight delay and cancelling it on each call to this method effectively debounces.
            delay(500)
            foodSearchUseCase(
                FoodSearchUseCaseParams(textQuery, selectedFilters.toList())
            ).collect {
                processSearchResult(it)
            }
        }

    }

    private fun processSearchResult(searchResult: Result<List<FoodDish>>) {
        _searchResults.update {
            searchResult.successOr(emptyList())
        }
    }

    private fun clearSearchResults() {
        _searchResults.update { emptyList() }
    }

    private fun handleFiltersToEmit(filters: List<Filter>) {
        _filterChips.update {
            filters.toFilterChips()
        }
    }

    fun clearFilters() {
        selectedFilters.clear()
        executeSearch()
    }

}

fun List<Filter>.toFilterChips(): List<ItemFilterChip> {
    val separated = groupBy {
        it.category
    }

    val ingredients = ItemFilterChip(
        title = R.string.ingredients_section_title,
        separated[Filter.CATEGORY_INGREDIENT]!!
    )

    val foodTypes = ItemFilterChip(
        title = R.string.food_type_section_title,
        separated[Filter.CATEGORY_FOOD_TYPE]!!
    )

    return listOf(
        ingredients, foodTypes
    )

}

data class ItemFilterChip(
    @StringRes val title: Int,
    val filters: List<Filter>
)