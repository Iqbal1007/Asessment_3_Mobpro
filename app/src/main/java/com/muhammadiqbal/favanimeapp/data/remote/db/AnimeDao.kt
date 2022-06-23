package com.muhammadiqbal.favanimeapp.data.remote.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete

@Dao
interface AnimeDao {
    @Insert
    fun insertFavoriteAnime(bmi: AnimeEntity)

    @Query("SELECT * FROM anime ORDER BY id DESC")
    fun getFavoriteAnime(): LiveData<List<AnimeEntity>>

    @Query("SELECT * FROM anime WHERE mal_id = :malId")
    fun getOneFavoriteAnime(malId: Int): LiveData<AnimeEntity?>

    @Query("Delete FROM anime WHERE id = :id")
    fun deleteFavoriteAnime(id: Long)
}