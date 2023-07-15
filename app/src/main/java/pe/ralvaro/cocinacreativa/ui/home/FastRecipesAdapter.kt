package pe.ralvaro.cocinacreativa.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import pe.ralvaro.cocinacreativa.data.model.HomeRecipe
import pe.ralvaro.cocinacreativa.databinding.ItemFastestRecipesBinding
import pe.ralvaro.cocinacreativa.ui.home.FastRecipesAdapter.FastRecipeViewHolder

class FastRecipesAdapter(
    private val onItemClick: (String) -> Unit
) : ListAdapter<HomeRecipe, FastRecipeViewHolder>(DiffCallBack) {

    class FastRecipeViewHolder(
        private val binding: ItemFastestRecipesBinding,
        private val onItemClick: (String) -> Unit
    ) : ViewHolder(binding.root) {
        fun bind(fastRecipe: HomeRecipe, position: Int) {
            binding.apply {
                tvName.text = fastRecipe.foodName
                tvSteps.text = "${fastRecipe.steps} pasos"
                tvTime.text = fastRecipe.timeWithFormat
                ivImage.load(fastRecipe.imgUrl)
                container.setOnClickListener {
                    onItemClick(fastRecipe.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FastRecipeViewHolder {
        val binding = ItemFastestRecipesBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return FastRecipeViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: FastRecipeViewHolder, position: Int) {
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
