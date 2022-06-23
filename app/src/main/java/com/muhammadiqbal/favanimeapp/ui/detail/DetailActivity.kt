package com.muhammadiqbal.favanimeapp.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ProgressBar
import android.widget.Toast
import androidx.navigation.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.muhammadiqbal.favanimeapp.databinding.ActivityDetailBinding
import com.muhammadiqbal.favanimeapp.data.models.DetailAnime
import com.muhammadiqbal.favanimeapp.data.remote.network.AnimeApiInterface
import com.muhammadiqbal.favanimeapp.data.remote.network.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {
    private val args: DetailActivityArgs by navArgs()
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Detail anime"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        getDetailAnime(args.malId)
        binding.shareButton.setOnClickListener { shareAnime() }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        if (item.itemId ==  android.R.id.home) {
            finish()
            return true
        }

        return false
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun shareAnime() {
        val shareIntent = Intent(Intent.ACTION_SEND)
        var message = "Saya menemukan anime bagus namanya: ''" + binding.topAnimeTitle.text
        message += "\n\ndengan sinopsis: \n"
        message += binding.topAnimeSynopsis.text
        shareIntent.setType("text/plain").putExtra(Intent.EXTRA_TEXT, message)
        if (shareIntent.resolveActivity(
                applicationContext.packageManager
            ) != null) {
            startActivity(shareIntent)
        }
    }

    private fun getDetailAnime(malId: Int) {
        binding.progressBar.visibility = ProgressBar.VISIBLE
        val animeApi: AnimeApiInterface = Api.getInstance().create(AnimeApiInterface::class.java)
        animeApi.getDetailAnime(malId)
            .enqueue(object : Callback<DetailAnime> {
                override fun onResponse(
                    call: Call<DetailAnime>,
                    response: Response<DetailAnime>
                ) {
                    val data = response.body()
                    if (response.isSuccessful) {
                        data?.let { displayDetailAnime(it) }
                        binding.progressBar.visibility = ProgressBar.GONE
                        binding.shareButton.isEnabled = true
                    }
                }

                override fun onFailure(call: Call<DetailAnime>, t: Throwable) {
                    val toast = Toast.makeText(applicationContext, "Gagal mengambil data", Toast.LENGTH_SHORT)
                    toast.show()
                    binding.progressBar.visibility = ProgressBar.GONE
                }
            })
    }

    @SuppressLint("SetTextI18n")
    fun displayDetailAnime(detailAnime: DetailAnime) {
        var genre = ""

        Glide.with(this)
            .load(detailAnime.data.images.jpg.large_image_url)
            .apply(RequestOptions.overrideOf(150, 200))
            .into(binding.topAnimeImage)
        binding.topAnimeTitle.text = detailAnime.data.title
        binding.topAnimeRating.text = "Rating: ${detailAnime.data.rating}"
        binding.topAnimeRank.text = "Rank: ${detailAnime.data.rank}"
        binding.topAnimeEpisode.text = "Episode: ${detailAnime.data.episodes}"
        binding.topAnimeScore.text = "Score: ${detailAnime.data.score}"
        binding.topAnimeSynopsis.text = detailAnime.data.synopsis
        detailAnime.data.genres.forEach { genresItem -> genre += " " + genresItem.name }
        binding.topAnimeGenre.text = "Genre: $genre"
    }
}