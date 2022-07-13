package com.foundy.presentation.view.home.notice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.annotation.VisibleForTesting
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.foundy.presentation.R
import com.foundy.presentation.databinding.FragmentNoticeBinding
import com.foundy.presentation.extension.addDividerDecoration
import com.foundy.presentation.model.NoticeUiState
import com.foundy.presentation.view.common.PagingLoadStateAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NoticeFragment(
    @VisibleForTesting factory: (() -> ViewModelProvider.Factory)? = null
) : Fragment(R.layout.fragment_notice) {

    private val viewModel: NoticeViewModel by activityViewModels(factory)

    private val adapter = NoticeAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentNoticeBinding.bind(view)

        initRecyclerView(binding)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect(::updateUi)
            }
        }
    }

    private fun initRecyclerView(binding: FragmentNoticeBinding) {
        binding.apply {
            recyclerView.addDividerDecoration()
            recyclerView.adapter = adapter.withLoadStateFooter(
                PagingLoadStateAdapter { adapter.retry() }
            )
            recyclerView.layoutManager = LinearLayoutManager(context)

            retryButton.setOnClickListener {
                adapter.retry()
            }

            adapter.addLoadStateListener { loadStates ->
                val isError = loadStates.refresh is LoadState.Error
                progressBar.isVisible = loadStates.refresh is LoadState.Loading
                retryButton.isVisible = isError
                errorMsg.isVisible = isError
            }
        }
    }

    private fun updateUi(uiState: NoticeUiState) {
        adapter.submitData(viewLifecycleOwner.lifecycle, uiState.noticeItemPagingData)
    }
}