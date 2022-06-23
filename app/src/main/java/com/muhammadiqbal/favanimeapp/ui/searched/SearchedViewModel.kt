package com.muhammadiqbal.favanimeapp.ui.searched

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammadiqbal.favanimeapp.data.remote.db.AnimeDao
import com.muhammadiqbal.favanimeapp.data.remote.db.AnimeEntity
import com.muhammadiqbal.favanimeapp.data.models.DataAnime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchedViewModel(private val db: AnimeDao): ViewModel() {

    fun addToFavorite(anime: DataAnime) {
        val dataAnime  = AnimeEntity(
            mal_id = anime.mal_id,
            title = anime.title,
            image = anime.images.jpg.large_image_url!!,
            rank = anime.rank,
            score = anime.score
        )

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                db.insertFavoriteAnime(dataAnime)
            }
        }
    }
}