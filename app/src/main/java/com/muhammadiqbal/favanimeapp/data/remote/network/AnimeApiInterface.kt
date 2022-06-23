package com.muhammadiqbal.favanimeapp.data.remote.network

import com.muhammadiqbal.favanimeapp.data.models.DetailAnime
import com.muhammadiqbal.favanimeapp.data.models.ListAnime
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call
import retrofit2.http.Path

interface AnimeApiInterface {
    @GET("anime")
    fun searchAnime(@Query("q") query: String) : Call<ListAnime>

    @GET("top/anime")
    fun topAnime(@Query("page") page: Int = 1) : Call<ListAnime>

    @GET("anime/{id}")
    fun getDetailAnime(@Path("id") id: Int) : Call<DetailAnime>
}