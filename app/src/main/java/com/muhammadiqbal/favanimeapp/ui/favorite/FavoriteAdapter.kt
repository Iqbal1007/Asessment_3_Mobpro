package com.muhammadiqbal.favanimeapp.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.muhammadiqbal.favanimeapp.databinding.FavoriteItemBinding
import com.muhammadiqbal.favanimeapp.data.remote.db.AnimeEntity

class FavoriteAdapter(private val clickListener: OnClickListener): ListAdapter<AnimeEntity, FavoriteAdapter.ViewHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<AnimeEntity>() {
            override fun areItemsTheSame(oldData: AnimeEntity, newData: AnimeEntity): Boolean {
                return oldData.id == newData.id
            }
            override fun areContentsTheSame(oldData: AnimeEntity, newData: AnimeEntity): Boolean {
                return oldData == newData
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FavoriteItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: FavoriteItemBinding,
        private val clickListener: OnClickListener
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(anime: AnimeEntity) = with(binding) {
            Glide.with(root)
                .load(anime.image)
                .apply(RequestOptions.overrideOf(100, 150))
                .into(topAnimePoster)
            animeTitle.text = anime.title
            animeRank.text = "Rank: ${anime.rank}"
            animeScore.text = "Score: ${anime.score}"
            deleteFavoriteButton.setOnClickListener {
                clickListener.onItemClick(anime.id)
            }
            detailButton.setOnClickListener {
                clickListener.goToDetailAnime(anime.mal_id)
            }
        }
    }

    interface OnClickListener {
        fun onItemClick(id: Long)
        fun goToDetailAnime(malId: Int)
    }
}