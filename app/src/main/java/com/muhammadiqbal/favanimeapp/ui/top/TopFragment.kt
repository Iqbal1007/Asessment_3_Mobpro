package com.muhammadiqbal.favanimeapp.ui.top

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.muhammadiqbal.favanimeapp.databinding.FragmentTopBinding
import com.muhammadiqbal.favanimeapp.data.remote.db.AnimeDb
import com.muhammadiqbal.favanimeapp.data.models.DataAnime
import com.muhammadiqbal.favanimeapp.data.models.ListAnime
import com.muhammadiqbal.favanimeapp.data.remote.network.AnimeApiInterface
import com.muhammadiqbal.favanimeapp.data.remote.network.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TopFragment : Fragment() {
    private lateinit var fragmentTopBinding: FragmentTopBinding
    private lateinit var topFragmentAdapter: TopAdapter
    private var page: Int = 1

    private val viewModel: TopViewModel by lazy {
        val db = AnimeDb.getInstace(requireContext())
        val factory = TopViewModelFactory(db.dao)
        ViewModelProvider(this, factory)[TopViewModel::class.java]
    }

    override fun onStart() {
        super.onStart()
        initRecyclerView()
        getTopAnime()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentTopBinding = FragmentTopBinding.inflate(inflater, container, false)
        return fragmentTopBinding.root
    }

    private fun initRecyclerView() {
        topFragmentAdapter = TopAdapter(arrayListOf(), object : TopAdapter.OnClickListener {
            override fun onItemClicked(dataAnime: DataAnime) {
                viewModel.addToFavorite(dataAnime)
                val toast = Toast.makeText(context, "Berhasil ditambahkan ke favorit", Toast.LENGTH_SHORT)
                toast.show()
            }

            override fun goToDetailAnime(malId: Int) {
                findNavController().navigate(
                    TopFragmentDirections.actionTopFragmentToDetailActivity(malId)
                )
            }
        })

        with(fragmentTopBinding.listTopAnime) {
            addItemDecoration(DividerItemDecoration(activity, RecyclerView.VERTICAL))
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = topFragmentAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(1)) {
                        page += 1
                        getTopAnime()
                    }
                }
            })
        }
    }

    private fun getTopAnime() {
        fragmentTopBinding.progressBar.visibility = ProgressBar.VISIBLE
        val animeApi: AnimeApiInterface = Api.getInstance().create(AnimeApiInterface::class.java)
        animeApi.topAnime(page)
            .enqueue(object : Callback<ListAnime> {
                override fun onResponse(
                    call: Call<ListAnime>,
                    response: Response<ListAnime>
                ) {
                    val data = response.body()
                    if (response.isSuccessful) {
                        data?.data?.let { topFragmentAdapter.setData(it) }
                        fragmentTopBinding.progressBar.visibility = ProgressBar.GONE
                    }
                }

                override fun onFailure(call: Call<ListAnime>, t: Throwable) {
                    val toast = Toast.makeText(context, "Gagal mengambil data top anime", Toast.LENGTH_SHORT)
                    toast.show()
                    fragmentTopBinding.progressBar.visibility = ProgressBar.GONE
                }
            })
    }
}