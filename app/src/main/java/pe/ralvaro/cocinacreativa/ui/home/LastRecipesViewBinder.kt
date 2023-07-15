package pe.ralvaro.cocinacreativa.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import pe.ralvaro.cocinacreativa.R
import pe.ralvaro.cocinacreativa.data.model.LastRecipesContainer
import pe.ralvaro.cocinacreativa.databinding.ItemLatestRecipesContainerBinding
import pe.ralvaro.cocinacreativa.ui.home.LastRecipesViewBinder.*

// Quasi adapter for Latest Recipes Recycler
class LastRecipesViewBinder(
    private val onItemClick: (String) -> Unit
) : HomeItemViewBinder<LastRecipesContainer, HomeLastRecipesViewHolder>(
    LastRecipesContainer::class.java
) {

    class HomeLastRecipesViewHolder(
        private val binding: ItemLatestRecipesContainerBinding,
        private val onItemClick: (String) -> Unit
    ) : ViewHolder(binding.root) {
        fun bind(lastRecipeItem: LastRecipesContainer) {
            val lastRecipeChildAdapter = LastRecipesAdapter(onItemClick)
            binding.titleLast.text = itemView.context.getString(lastRecipeItem.titleId)
            binding.recyclerLasts.apply {
                layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                adapter = lastRecipeChildAdapter
            }

            lastRecipeChildAdapter.submitList(lastRecipeItem.recipes)
        }
    }

    override fun createViewHolder(parent: ViewGroup): ViewHolder {
        return HomeLastRecipesViewHolder(
            ItemLatestRecipesContainerBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            onItemClick
        )
    }

    override fun getHomeItemType(): Int = R.layout.item_latest_recipes_container

    override fun areContentsTheSame(oldItem: LastRecipesContainer, newItem: LastRecipesContainer) =
        oldItem == newItem

    override fun areItemsTheSame(oldItem: LastRecipesContainer, newItem: LastRecipesContainer) =
        true

    override fun bindViewHolder(
        model: LastRecipesContainer,
        viewHolder: HomeLastRecipesViewHolder
    ) = viewHolder.bind(model)

}

