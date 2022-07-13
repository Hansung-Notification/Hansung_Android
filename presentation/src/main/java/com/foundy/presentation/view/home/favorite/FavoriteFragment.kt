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
import com.foundy.presentation.R
import com.foundy.presentation.databinding.FragmentFavoriteBinding
import com.foundy.presentation.extension.addDividerDecoration
import com.foundy.presentation.model.FavoriteUiState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FavoriteFragment(
    @VisibleForTesting factory: (() -> ViewModelProvider.Factory)? = null
) : Fragment(R.layout.fragment_favorite) {

    private val viewModel: FavoriteViewModel by activityViewModels(factory)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentFavoriteBinding.bind(view)
        val adapter = FavoriteAdapter()

        initUi(binding, adapter)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    updateUi(it, binding, adapter)
                }
            }
        }
    }

    private fun initUi(binding: FragmentFavoriteBinding, adapter: FavoriteAdapter) {
        binding.apply {
            recyclerView.adapter = adapter
            recyclerView.addDividerDecoration()
            recyclerView.layoutManager = LinearLayoutManager(context)
        }
    }

    private fun updateUi(
        uiState: FavoriteUiState,
        binding: FragmentFavoriteBinding,
        adapter: FavoriteAdapter
    ) {
        val favoriteList = uiState.favoriteItemList
        binding.emptyText.isVisible = favoriteList.isEmpty()
        adapter.submitList(favoriteList)
    }
}