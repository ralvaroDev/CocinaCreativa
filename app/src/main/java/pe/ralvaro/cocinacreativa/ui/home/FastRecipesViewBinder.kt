package pe.ralvaro.cocinacreativa.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pe.ralvaro.cocinacreativa.R
import pe.ralvaro.cocinacreativa.data.model.FastestRecipesContainer
import pe.ralvaro.cocinacreativa.databinding.ItemFastedRecipesContainerBinding
import pe.ralvaro.cocinacreativa.ui.home.FastRecipesViewBinder.HomeFastRecipesViewHolder

class FastRecipesViewBinder(
    private val onClickItem: (String) -> Unit
) : HomeItemViewBinder<FastestRecipesContainer, HomeFastRecipesViewHolder>(
    FastestRecipesContainer::class.java
){
    class HomeFastRecipesViewHolder(
        private val binding: ItemFastedRecipesContainerBinding,
        private val onItemClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(fastRecipeItem: FastestRecipesContainer) {
            val fastRecipeChildAdapter = FastRecipesAdapter(onItemClick)
            binding.tvTitleContainer.text = itemView.context.getString(fastRecipeItem.titleId)
            binding.rvRecyclerFasted.apply {
                layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                adapter = fastRecipeChildAdapter
            }
            fastRecipeChildAdapter.submitList(fastRecipeItem.recipes)
        }
    }

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return HomeFastRecipesViewHolder(
            ItemFastedRecipesContainerBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            onClickItem
        )
    }

    override fun getHomeItemType(): Int = R.layout.item_fasted_recipes_container

    override fun areContentsTheSame(
        oldItem: FastestRecipesContainer,
        newItem: FastestRecipesContainer
    ): Boolean = oldItem == newItem

    override fun areItemsTheSame(
        oldItem: FastestRecipesContainer,
        newItem: FastestRecipesContainer
    ): Boolean = true

    override fun bindViewHolder(
        model: FastestRecipesContainer,
        viewHolder: HomeFastRecipesViewHolder
    ) = viewHolder.bind(model)


}
