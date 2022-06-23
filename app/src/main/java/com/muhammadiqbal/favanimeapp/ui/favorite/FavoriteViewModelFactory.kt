package com.muhammadiqbal.favanimeapp.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.muhammadiqbal.favanimeapp.data.remote.db.AnimeDao
import java.lang.IllegalArgumentException

class FavoriteViewModelFactory (
    private val db: AnimeDao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(db) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}