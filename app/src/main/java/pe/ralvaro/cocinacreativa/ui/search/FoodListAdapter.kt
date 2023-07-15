package pe.ralvaro.cocinacreativa.ui.search

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexboxLayoutManager
import pe.ralvaro.cocinacreativa.data.model.FoodDish
import pe.ralvaro.cocinacreativa.databinding.ItemFoodsBinding
import timber.log.Timber


class FoodListAdapter(
    private val onSelectionFood: (String) -> Unit
) : ListAdapter<FoodDish, FoodListAdapter.ViewHolder>(
    DiffCallBack
) {

    class ViewHolder(private val binding: ItemFoodsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(current: FoodDish, onSelectionFood: (String) -> Unit) {
            binding.tvTitleFood.text = current.foodName
            binding.tvDescription.text = current.description

            binding.itemContainer.setOnClickListener {
                Timber.d("Pre go to Detail")
                onSelectionFood(current.id)
            }

            val layoutManager = NoScrollFlexboxLayoutManager(itemView.context)
            val adapter = TagFiltersAdapter(current.filterTags)

            binding.rvTags.layoutManager = layoutManager
            binding.rvTags.adapter = adapter
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemFoodsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, onSelectionFood)
    }

    companion object {
        private val DiffCallBack = object : DiffUtil.ItemCallback<FoodDish>() {
            override fun areItemsTheSame(
                oldItem: FoodDish,
                newItem: FoodDish
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: FoodDish,
                newItem: FoodDish
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

}

class NoScrollFlexboxLayoutManager(context: Context) : FlexboxLayoutManager(context) {
    override fun canScrollVertically(): Boolean = false
    override fun canScrollHorizontally(): Boolean = false
}