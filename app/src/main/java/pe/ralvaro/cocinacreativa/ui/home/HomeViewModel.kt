package pe.ralvaro.cocinacreativa.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pe.ralvaro.cocinacreativa.data.model.FastestRecipesContainer
import pe.ralvaro.cocinacreativa.data.model.LastRecipesContainer
import pe.ralvaro.cocinacreativa.data.model.RecommendedRecipesContainer
import pe.ralvaro.cocinacreativa.domain.home.LoadLastRecipesUseCase
import pe.ralvaro.cocinacreativa.domain.home.LoadRandomRecipesUseCase
import pe.ralvaro.cocinacreativa.domain.home.LoadRecipesByMinorTimeUseCase
import pe.ralvaro.cocinacreativa.util.Result
import pe.ralvaro.cocinacreativa.util.data
import pe.ralvaro.cocinacreativa.util.succeeded
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    loadRandomRecipesUseCase: LoadRandomRecipesUseCase,
    loadLastRecipesUseCase: LoadLastRecipesUseCase,
    loadRecipesByMinorTimeUseCase: LoadRecipesByMinorTimeUseCase
) : ViewModel() {

    private val lastRecipes = loadLastRecipesUseCase(Unit)
    private val recommendedRecipes = loadRandomRecipesUseCase(Unit)
    private val fastestRecipes = loadRecipesByMinorTimeUseCase(Unit)

    private val _sharedRecipes = MutableStateFlow<List<Any>>(emptyList())
    val sharedRecipes: StateFlow<List<Any>> = _sharedRecipes

    init {
        // This is to avoid unnecessary requests
        if (_sharedRecipes.value.isEmpty()){
            updateRecipesList()
        }
    }

    private fun updateRecipesList() {
        viewModelScope.launch {
            // Here we combine the flows that will enter the multi adapter
            _sharedRecipes.update {
                combine(
                    lastRecipes,
                    recommendedRecipes,
                    fastestRecipes
                ) { last, recommended, fast ->

                    // Update only succeeded values
                    val tempRecipes = mutableListOf<Any>()
                    if (last.succeeded)
                        tempRecipes.add(last.data!!)
                    if (fast.succeeded)
                        tempRecipes.add(fast.data!!)
                    if (recommended.succeeded)
                        tempRecipes.add(recommended.data!!)
                    tempRecipes.add(SocialMediaSection)
                    tempRecipes
                }.first()
            }
        }

    }

}