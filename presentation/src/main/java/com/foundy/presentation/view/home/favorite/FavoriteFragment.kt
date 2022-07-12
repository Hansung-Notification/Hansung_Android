package com.foundy.presentation.view.home.favorite

import android.os.Bundle
import android.view.View
import androidx.annotation.VisibleForTesting
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.foundy.presentation.view.home.HomeViewModel
import com.foundy.presentation.R
import com.foundy.presentation.databinding.FragmentFavoriteBinding
import com.foundy.presentation.extension.addDividerDecoration
import kotlinx.coroutines.launch

class FavoriteFragment(
    @VisibleForTesting factory: (() -> ViewModelProvider.Factory)? = null
) : Fragment(R.layout.fragment_favorite) {

    private val viewModel: HomeViewModel by activityViewModels(factory)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentFavoriteBinding.bind(view)
        val adapter = FavoriteAdapter()

        binding.apply {
            recyclerView.adapter = adapter
            recyclerView.addDividerDecoration()
            recyclerView.layoutManager = LinearLayoutManager(context)

            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.favoritesState.collect {
                        emptyText.isVisible = it.isEmpty()
                        adapter.submitList(it)
                    }
                }
            }
        }
    }
}