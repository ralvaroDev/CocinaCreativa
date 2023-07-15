package pe.ralvaro.cocinacreativa.ui.food_detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pe.ralvaro.cocinacreativa.data.model.Filter
import pe.ralvaro.cocinacreativa.databinding.ItemIngredientBinding

class IngredientsDetailAdapter(
    private val ingredientList: List<Filter>
) : RecyclerView.Adapter<IngredientsDetailAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemIngredientBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(filter: Filter) {
            binding.tvIngredient.text = filter.name
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemIngredientBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return ingredientList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(ingredientList[position])
    }
}