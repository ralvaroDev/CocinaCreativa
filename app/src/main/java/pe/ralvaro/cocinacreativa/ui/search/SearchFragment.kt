package pe.ralvaro.cocinacreativa.ui.search

import android.widget.FrameLayout
import android.widget.SearchView.OnQueryTextListener
import androidx.activity.addCallback
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HIDDEN
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import pe.ralvaro.cocinacreativa.R
import pe.ralvaro.cocinacreativa.databinding.FragmentSearchBinding
import pe.ralvaro.cocinacreativa.ui.BaseFragment
import pe.ralvaro.cocinacreativa.util.applyCheckToSome
import pe.ralvaro.cocinacreativa.util.applyCheckedOnAll
import pe.ralvaro.cocinacreativa.util.hideKeyboard
import pe.ralvaro.cocinacreativa.util.ignoreException
import pe.ralvaro.cocinacreativa.util.launchAndRepeatWithViewLifecycle
import pe.ralvaro.cocinacreativa.util.obtainCheckedChildTag
import pe.ralvaro.cocinacreativa.util.showKeyboard

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    private val searchViewModel: SearchViewModel by viewModels()

    private lateinit var filterFragment: BottomSheetBehavior<FrameLayout>

    override fun onBackHandler() {
        requireActivity().onBackPressedDispatcher.addCallback(owner = viewLifecycleOwner) {
            if (filterFragment.state != STATE_HIDDEN) {
                filterFragment.state = STATE_HIDDEN
            } else {
                findNavController().popBackStack()
            }
        }
    }

    /**
     * [SearchFragment] represent a group of two quasi fragments, filter fragment and search result fragment
     */
    override fun onViewCreated() {
        // Load search result fragment views
        initOwnFragment()
        // Load bottom sheet filter fragment
        initBottomSheet()

        binding.toolbar.apply {
            setOnMenuItemClickListener {
                if (it.itemId == R.id.action_open_filters) {
                    findFiltersFragment()
                    true
                } else {
                    false
                }
            }
        }


    }

    private fun checkIfTagsArePreviouslySelected() {
        searchViewModel.selectedFilters.let { selectedFilters ->
            if (selectedFilters.isNotEmpty()) {
                SelectableSectionFilterAdapter.ViewHolder.referenceToChipsGroup.onEach {
                    it.applyCheckToSome(selectedFilters.map { filter -> filter.tag })
                }
            }
        }
    }

    private fun initOwnFragment() {

        val adapter = FoodListAdapter {
            goToDetail(it)
        }
        launchAndRepeatWithViewLifecycle {
            searchViewModel.searchResults.collectLatest {
                adapter.submitList(it)
                binding.filterFragment.tvTitleFilterFragment.text =
                    if (it.isNotEmpty()) getString(R.string.template_results_count, it.size)
                    else getString(R.string.filters)
            }
        }
        binding.rvSearchResults.adapter = adapter
        binding.searchView.apply {
            setOnQueryTextListener(object : OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    hideKeyboard()
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    searchViewModel.onSearchQueryChanged(newText)
                    return true
                }

            })
            // Set focus on the SearchView and open the keyboard
            setOnQueryTextFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    showKeyboard(view.findFocus())
                }
            }
            requestFocus()
        }

    }

    private fun initBottomSheet() {
        //Setting bottom sheet view configurations
        val standardBottomSheet =
            requireView().findViewById<FrameLayout>(R.id.standard_bottom_sheet)
        filterFragment = BottomSheetBehavior.from(standardBottomSheet)
        filterFragment.peekHeight =
            resources.displayMetrics.heightPixels - resources.getDimensionPixelSize(R.dimen.top_peek) - 200
        filterFragment.state = STATE_HIDDEN
        filterFragment.skipCollapsed = true
        filterFragment.saveFlags = BottomSheetBehavior.SAVE_ALL

        setUiFilter()
    }

    /**
     * This will be like a separate fragment
     */
    private fun setUiFilter() {
        binding.filterFragment.collapseArrow.setOnClickListener {
            filterFragment.state = STATE_HIDDEN
        }

        val adapter = SelectableSectionFilterAdapter {
            val temporalTags = mutableListOf<String>()
            SelectableSectionFilterAdapter.ViewHolder.referenceToChipsGroup.onEach {
                temporalTags.addAll(it.obtainCheckedChildTag())
            }
            binding.filterFragment.reset.isVisible = temporalTags.isNotEmpty()
            searchViewModel.updateCheckedTags(temporalTags)
        }


        launchAndRepeatWithViewLifecycle {
            searchViewModel.filterChips.collectLatest {
                adapter.submitList(it)
                // To avoid race between submit and check flags
                delay(500)
                checkIfTagsArePreviouslySelected()
            }
        }
        binding.filterFragment.recyclerviewFilters.adapter = adapter
        binding.filterFragment.reset.setOnClickListener {
            SelectableSectionFilterAdapter.ViewHolder.referenceToChipsGroup.onEach { chipGroup ->
                chipGroup.applyCheckedOnAll(false)
            }
            searchViewModel.clearFilters()
            it.isInvisible = true
        }
    }

    private fun findFiltersFragment() {
        filterFragment.state = STATE_EXPANDED
        hideKeyboard()
    }

    private fun goToDetail(foodId: String) {
        ignoreException {
            val action = SearchFragmentDirections.toFoodDetail(foodId = foodId)
            findNavController().navigate(action)
        }

    }

}