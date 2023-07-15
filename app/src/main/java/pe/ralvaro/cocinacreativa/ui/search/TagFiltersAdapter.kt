package pe.ralvaro.cocinacreativa.ui.search

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.TextViewCompat
import androidx.recyclerview.widget.RecyclerView
import pe.ralvaro.cocinacreativa.data.model.Filter
import pe.ralvaro.cocinacreativa.databinding.ItemFoodFilterBinding
import pe.ralvaro.cocinacreativa.util.filterChipTint

//Cambiar todo el tipado para obtener el color de cada uno
class TagFiltersAdapter(
    private val tagFilterList: List<Filter>
) : RecyclerView.Adapter<TagFiltersAdapter.ViewHolder>() {


    class ViewHolder(private val binding: ItemFoodFilterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(filter: Filter) {
            binding.tvTitleFilter.text = filter.name
            TextViewCompat.setCompoundDrawableTintList(
                binding.tvTitleFilter,
                itemView.context.filterChipTint(Color.parseColor(filter.color))
            )
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            ItemFoodFilterBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tagFilterList[position])
    }


    override fun getItemCount(): Int {
        return tagFilterList.size
    }

}