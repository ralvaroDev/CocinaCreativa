package pe.ralvaro.cocinacreativa.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pe.ralvaro.cocinacreativa.R
import pe.ralvaro.cocinacreativa.databinding.ItemSocialMediaBinding
import pe.ralvaro.cocinacreativa.ui.home.SocialMediaSectionViewBinder.SocialMediaSectionViewHolder

object SocialMediaSection

class SocialMediaSectionViewBinder :
    HomeItemViewBinder<SocialMediaSection, SocialMediaSectionViewHolder>(
        SocialMediaSection::class.java
    ) {
    class SocialMediaSectionViewHolder(
        private val binding: ItemSocialMediaBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind() {

        }

    }

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        SocialMediaSectionViewHolder(
            ItemSocialMediaBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun getHomeItemType() = R.layout.item_social_media

    override fun bindViewHolder(
        model: SocialMediaSection,
        viewHolder: SocialMediaSectionViewHolder
    ) = viewHolder.bind()


    override fun areItemsTheSame(
        oldItem: SocialMediaSection,
        newItem: SocialMediaSection
    ) = true

    override fun areContentsTheSame(
        oldItem: SocialMediaSection,
        newItem: SocialMediaSection
    ) = true
}