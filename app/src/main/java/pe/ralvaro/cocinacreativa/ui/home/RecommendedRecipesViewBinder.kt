package pe.ralvaro.cocinacreativa.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import pe.ralvaro.cocinacreativa.R
import pe.ralvaro.cocinacreativa.data.model.RecommendedRecipesContainer
import pe.ralvaro.cocinacreativa.databinding.ItemRecommendedRecipesContainerBinding
import pe.ralvaro.cocinacreativa.ui.home.RecommendedRecipesViewBinder.*

class RecommendedRecipesViewBinder(
    private val onItemClick: (String) -> Unit
) : HomeItemViewBinder<RecommendedRecipesContainer, HomeRecommendedRecipesViewHolder>(
    RecommendedRecipesContainer::class.java
) {
    class HomeRecommendedRecipesViewHolder(
        private val binding: ItemRecommendedRecipesContainerBinding,
        private val onItemClick: (String) -> Unit
    ):ViewHolder(binding.root) {

        fun bind(recommendedItem: RecommendedRecipesContainer) {
            val recommendedChildAdapter = RecommendedRecipesAdapter(onItemClick)
            binding.titleRecommended.text = itemView.context.getString(recommendedItem.titleId)
            binding.recyclerRecommended.apply {
                layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                adapter = recommendedChildAdapter
            }

            recommendedChildAdapter.submitList(recommendedItem.recipes)
        }

    }

    override fun createViewHolder(parent: ViewGroup): ViewHolder {
        return HomeRecommendedRecipesViewHolder(
            ItemRecommendedRecipesContainerBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            onItemClick
        )
    }

    override fun getHomeItemType(): Int = R.layout.item_recommended_recipes_container

    override fun areContentsTheSame(
        oldItem: RecommendedRecipesContainer,
        newItem: RecommendedRecipesContainer
    ): Boolean = oldItem == newItem

    override fun areItemsTheSame(
        oldItem: RecommendedRecipesContainer,
        newItem: RecommendedRecipesContainer
    ): Boolean = true

    override fun bindViewHolder(
        model: RecommendedRecipesContainer,
        viewHolder: HomeRecommendedRecipesViewHolder
    ) = viewHolder.bind(model)
}