package pe.ralvaro.cocinacreativa.ui.home

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

typealias HomeItemClass = Class<out Any>

typealias HomeItemBinder = HomeItemViewBinder<Any, ViewHolder>

class HomeMultiAdapter(
    private val viewBinders: Map<HomeItemClass, HomeItemBinder>
) : ListAdapter<Any, ViewHolder>(HomeDiffCallback(viewBinders)) {

    // We create a new map keeping the values of [viewBinders] but changing the keys to their associated layouts ids
    private val viewTypeToBinders = viewBinders.mapKeys { it.value.getHomeItemType() }

    // Allows us to obtain the [HomeItemBinder] based on the layout id
    private fun getViewBinder(viewType: Int): HomeItemBinder = viewTypeToBinders.getValue(viewType)

    // Allows obtaining the layout id based on the dataclass of the element obtained by the position
    override fun getItemViewType(position: Int): Int =
        viewBinders.getValue(super.getItem(position).javaClass).getHomeItemType()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        getViewBinder(viewType).createViewHolder(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        getViewBinder(getItemViewType(position)).bindViewHolder(getItem(position), holder)

    override fun onViewRecycled(holder: ViewHolder) {
        getViewBinder(holder.itemViewType).onViewRecycled(holder)
        super.onViewRecycled(holder)
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        getViewBinder(holder.itemViewType).onViewDetachedFromWindow(holder)
        super.onViewDetachedFromWindow(holder)
    }

}

/** Encapsulates logic to create and bind a ViewHolder for a type of item in the Home. */
abstract class HomeItemViewBinder<M, in VH : ViewHolder>(
    val modelClass: Class<out M>
) : DiffUtil.ItemCallback<M>() {

    abstract fun createViewHolder(parent: ViewGroup): ViewHolder
    abstract fun bindViewHolder(model: M, viewHolder: VH)
    abstract fun getHomeItemType(): Int

    // We declare them non-abstract because not all viewBinders need to implement these methods.
    open fun onViewRecycled(viewHolder: VH) = Unit
    open fun onViewDetachedFromWindow(viewHolder: VH) = Unit
}

internal class HomeDiffCallback(
    private val viewBinders: Map<HomeItemClass, HomeItemBinder>
) : DiffUtil.ItemCallback<Any>() {

    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
        if (oldItem::class != newItem::class) {
            return false
        }
        return viewBinders[oldItem::class.java]?.areItemsTheSame(oldItem, newItem) ?: false
    }

    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
        return viewBinders[oldItem::class.java]?.areContentsTheSame(oldItem, newItem) ?: false
    }
}