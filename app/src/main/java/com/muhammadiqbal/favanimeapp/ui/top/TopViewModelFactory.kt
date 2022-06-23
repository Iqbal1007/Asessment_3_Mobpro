package com.muhammadiqbal.favanimeapp.ui.top

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.muhammadiqbal.favanimeapp.data.remote.db.AnimeDao
import java.lang.IllegalArgumentException

class TopViewModelFactory(private val db: AnimeDao): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TopViewModel::class.java)) {
            return TopViewModel(db) as T
        }
        throw IllegalArgumentException("Unknown ViewModelClass")
    }
}