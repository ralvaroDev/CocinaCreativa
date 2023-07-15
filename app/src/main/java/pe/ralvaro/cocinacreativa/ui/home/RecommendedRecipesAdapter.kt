package pe.ralvaro.cocinacreativa.ui.home

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import pe.ralvaro.cocinacreativa.data.model.HomeRecipe
import pe.ralvaro.cocinacreativa.databinding.ItemRecommendedRecipesBinding

class RecommendedRecipesAdapter(
    private val onItemClick: (String) -> Unit
) : ListAdapter<HomeRecipe, RecommendedRecipesAdapter.RecommendedRecipeViewHolder>(DiffCallBack) {

    class RecommendedRecipeViewHolder(
        private val binding: ItemRecommendedRecipesBinding,
        private val onItemClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(recomRecipe: HomeRecipe, position: Int) {

            binding.apply {
                name.text = recomRecipe.foodName
                steps.text = "${recomRecipe.steps} pasos"
                time.text = recomRecipe.timeWithFormat
                container.setCardBackgroundColor(getColorByPosition(position))
                image.load(recomRecipe.imgUrl)
                container.setOnClickListener {
                    onItemClick(recomRecipe.id)
                }
            }
        }

        private fun getColorByPosition(position: Int): Int {
            val availableColors = arrayOf("#42c1a6", "#ffce5d", "#009ddd")
            val index = position % availableColors.size
            return Color.parseColor(availableColors[index])
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecommendedRecipeViewHolder {
        val binding = ItemRecommendedRecipesBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return RecommendedRecipeViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(
        holder: RecommendedRecipeViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position), position)
    }

    companion object {
        private val DiffCallBack = object : DiffUtil.ItemCallback<HomeRecipe>() {
            override fun areItemsTheSame(oldItem: HomeRecipe, newItem: HomeRecipe): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: HomeRecipe, newItem: HomeRecipe): Boolean {
                return oldItem == newItem
            }
        }
    }

}
