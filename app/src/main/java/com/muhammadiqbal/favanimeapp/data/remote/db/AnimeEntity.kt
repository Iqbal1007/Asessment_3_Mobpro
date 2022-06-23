package com.muhammadiqbal.favanimeapp.data.remote.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "anime")
data class AnimeEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var mal_id: Int,
    var title: String,
    var image: String,
    var rank: Int,
    var score: Double
)