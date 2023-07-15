package pe.ralvaro.cocinacreativa.ui.search

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import pe.ralvaro.cocinacreativa.R
import pe.ralvaro.cocinacreativa.data.model.Filter
import pe.ralvaro.cocinacreativa.databinding.ItemSectionFilterBinding
import pe.ralvaro.cocinacreativa.util.filterChipTint

class SelectableSectionFilterAdapter(
    private val onSelectFilter: () -> Unit
) : ListAdapter<ItemFilterChip, SelectableSectionFilterAdapter.ViewHolder>(DiffCallBack) {

    class ViewHolder(private val binding: ItemSectionFilterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            val referenceToChipsGroup: MutableSet<ChipGroup> = mutableSetOf()
        }

        fun bind(current: ItemFilterChip, onSelectFilter: () -> Unit) {
            binding.filterSectionHeader.text = itemView.context.getString(current.title)


            /*if (current.filters.first().category == "food_type"){
                Timber.d("We are in foodType")

            }*/

            current.filters.forEach {
                addChip(it, onSelectFilter)
            }
            referenceToChipsGroup.add(binding.chipGroup)
        }

        private fun addChip(item: Filter, onSelectFilter: () -> Unit) {
            val chip = LayoutInflater.from(itemView.context)
                .inflate(R.layout.item_filter_chip, binding.chipGroup, false) as Chip

            chip.apply {
                text = item.name
                chipIconTint = itemView.context.filterChipTint(
                    Color.parseColor(item.color)
                )
                tag = item.tag
                setOnClickListener {
                    onSelectFilter()
                }
            }
            binding.chipGroup.addView(chip)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemSectionFilterBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, onSelectFilter)
    }

    companion object {

        private val DiffCallBack = object : DiffUtil.ItemCallback<ItemFilterChip>() {
            override fun areItemsTheSame(
                oldItem: ItemFilterChip,
                newItem: ItemFilterChip
            ): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(
                oldItem: ItemFilterChip,
                newItem: ItemFilterChip
            ): Boolean {
                return oldItem.filters == newItem.filters
            }

        }
    }

}