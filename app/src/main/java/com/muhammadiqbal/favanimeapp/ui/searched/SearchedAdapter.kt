package com.muhammadiqbal.favanimeapp.ui.searched

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.muhammadiqbal.favanimeapp.databinding.TopItemBinding
import com.muhammadiqbal.favanimeapp.data.models.DataAnime

class SearchedAdapter(
        private var listDataAnime: ArrayList<DataAnime>,
        private var clickListener: OnClickListener
): RecyclerView.Adapter<SearchedAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TopItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listDataAnime[position])
    }

    override fun getItemCount(): Int {
        return listDataAnime.size
    }

    inner class ViewHolder(private val binding: TopItemBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(anime: DataAnime) {
            return with(binding) {
                Glide.with(binding.root)
                    .load(anime.images.jpg.large_image_url)
                    .apply(RequestOptions.overrideOf(100, 150))
                    .into(binding.topAnimePoster)
                binding.animeTitle.text = anime.title
                binding.animeRank.text = "Rank: ${anime.rank}"
                binding.animeScore.text = "Score: ${anime.score}"
                binding.favoriteButton.setOnClickListener {
                    clickListener.onItemClicked(anime)
                }
                binding.detailButton.setOnClickListener {
                    clickListener.goToDetailAnime(anime.mal_id)
                }

            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(listAnime: ArrayList<DataAnime>) {
        listDataAnime.clear()
        listDataAnime.addAll(listAnime)
        notifyDataSetChanged()
    }

    interface OnClickListener {
        fun onItemClicked(dataAnime: DataAnime)
        fun goToDetailAnime(malId: Int)
    }
}