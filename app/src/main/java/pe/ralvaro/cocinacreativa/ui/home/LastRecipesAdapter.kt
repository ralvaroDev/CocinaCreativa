package pe.ralvaro.cocinacreativa.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import pe.ralvaro.cocinacreativa.data.model.HomeRecipe
import pe.ralvaro.cocinacreativa.databinding.ItemLatestRecipesBinding


class LastRecipesAdapter(
    private val onItemClick: (String) -> Unit
) : ListAdapter<HomeRecipe, LastRecipesAdapter.LastRecipeViewHolder>(DiffCallBack) {

    class LastRecipeViewHolder(
        private val binding: ItemLatestRecipesBinding,
        private val onItemClick: (String) -> Unit
    ) : ViewHolder(binding.root) {
        fun bind(lastRecipe: HomeRecipe) {
            binding.apply {
                tvName.text = lastRecipe.foodName
                tvSteps.text = "${lastRecipe.steps} pasos"
                tvTime.text = lastRecipe.timeWithFormat
                ivImage.load(lastRecipe.imgUrl)
                container.setOnClickListener {
                    onItemClick(lastRecipe.id)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LastRecipeViewHolder {
        val binding = ItemLatestRecipesBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return LastRecipeViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: LastRecipeViewHolder, position: Int) {
        holder.bind(getItem(position))
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

