package com.foundy.presentation.view.favorite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.foundy.presentation.view.MainViewModel
import com.foundy.presentation.R
import com.foundy.presentation.databinding.FragmentFavoriteBinding
import com.foundy.presentation.extension.addDividerDecoration

class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentFavoriteBinding.bind(view)
        val adapter = FavoriteAdapter()

        binding.apply {
            recyclerView.adapter = adapter
            recyclerView.addDividerDecoration(view.context)
            recyclerView.layoutManager = LinearLayoutManager(context)

            adapter.addAll(viewModel.favoriteList)
        }
    }
}