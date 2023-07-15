package pe.ralvaro.cocinacreativa.ui.home

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import pe.ralvaro.cocinacreativa.R
import pe.ralvaro.cocinacreativa.databinding.FragmentHomeBinding
import pe.ralvaro.cocinacreativa.ui.BaseFragment
import pe.ralvaro.cocinacreativa.util.ignoreException
import pe.ralvaro.cocinacreativa.util.launchAndRepeatWithViewLifecycle
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val homeViewModel: HomeViewModel by viewModels()

    private var adapter: HomeMultiAdapter? = null

    override fun onViewCreated() {

        binding.toolbar.run {
            setOnMenuItemClickListener { item ->
                if (item.itemId == R.id.search) {
                    openSearch()
                    true
                } else {
                    false
                }
            }
        }

        launchAndRepeatWithViewLifecycle {
            homeViewModel.sharedRecipes.collect {
                showItems(it)
            }
        }

    }

    private fun showItems(list: List<Any>) {
        if (adapter == null) {
            val lastRecipesViewBinder = LastRecipesViewBinder { navigateToDetail(recipeId = it) }
            val recommendedRecipesViewBinder = RecommendedRecipesViewBinder { navigateToDetail(recipeId = it) }
            val fastestRecipesViewBinder = FastRecipesViewBinder { navigateToDetail(recipeId = it) }
            val socialMediaViewBinder = SocialMediaSectionViewBinder()

            val viewBinders = LinkedHashMap<HomeItemClass, HomeItemBinder>()

            // Adding the section adapters
            viewBinders[lastRecipesViewBinder.modelClass] = lastRecipesViewBinder as HomeItemBinder
            viewBinders[fastestRecipesViewBinder.modelClass] = fastestRecipesViewBinder as HomeItemBinder
            viewBinders[recommendedRecipesViewBinder.modelClass] = recommendedRecipesViewBinder as HomeItemBinder
            viewBinders[socialMediaViewBinder.modelClass] = socialMediaViewBinder as HomeItemBinder

            adapter = HomeMultiAdapter(viewBinders)
        }

        if (binding.rvMultiRecycler.adapter == null) {
            binding.rvMultiRecycler.adapter = adapter
        }
        (binding.rvMultiRecycler.adapter as HomeMultiAdapter).submitList(list)
    }

    private fun openSearch() {
        ignoreException {
            findNavController().navigate(HomeFragmentDirections.toSearch())
        }
    }

    private fun navigateToDetail(recipeId: String) {
        ignoreException {
            findNavController().navigate(HomeFragmentDirections.toFoodDetailFromHome(recipeId))
        }
    }

}