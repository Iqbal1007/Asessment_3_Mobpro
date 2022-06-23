package com.muhammadiqbal.favanimeapp.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.muhammadiqbal.favanimeapp.databinding.FragmentFavoriteBinding
import com.muhammadiqbal.favanimeapp.data.remote.db.AnimeDb
import com.muhammadiqbal.favanimeapp.ui.searched.SearchedFragmentDirections

class FavoriteFragment : Fragment() {
    private lateinit var fragmentFavoritBinding: FragmentFavoriteBinding
    private lateinit var favoriteAdapter: FavoriteAdapter

    private val viewModel: FavoriteViewModel by lazy {
        val db = AnimeDb.getInstace(requireContext())
        val factory = FavoriteViewModelFactory(db.dao)
        ViewModelProvider(this, factory)[FavoriteViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentFavoritBinding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        return fragmentFavoritBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        favoriteAdapter = FavoriteAdapter(object : FavoriteAdapter.OnClickListener {
            override fun onItemClick(id: Long) {
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Konfirmasi penghapusan")
                    .setMessage("Yakin ingin menghapus anime ini dari favorit ?")
                    .setNegativeButton("Batal") { _, _ ->
                    }
                    .setPositiveButton("Hapus") { _, _ ->
                        viewModel.deleteFavoriteAnime(id)
                    }.show()
            }

            override fun goToDetailAnime(malId: Int) {
                findNavController().navigate(
                    FavoriteFragmentDirections.actionFavoriteFragmentToDetailActivity(malId)
                )
            }
        })
        with(fragmentFavoritBinding.favoriteRecyclerView) {
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
            adapter = favoriteAdapter
            setHasFixedSize(true)
        }
        viewModel.favoriteAnimeData.observe(viewLifecycleOwner) {
            fragmentFavoritBinding.emptyTextView.visibility = if (it.isEmpty())
                View.VISIBLE else View.GONE
            favoriteAdapter.submitList(it)
        }
    }

}