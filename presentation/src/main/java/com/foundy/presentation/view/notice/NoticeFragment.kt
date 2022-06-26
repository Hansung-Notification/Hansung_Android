package com.foundy.presentation.view.notice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.foundy.presentation.view.MainViewModel
import com.foundy.presentation.R
import com.foundy.presentation.databinding.FragmentNoticeBinding
import com.foundy.presentation.extension.addDividerDecoration
import com.foundy.presentation.view.paging.PagingLoadStateAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NoticeFragment : Fragment(R.layout.fragment_notice) {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentNoticeBinding.bind(view)
        val adapter = NoticeAdapter(viewModel)

        binding.apply {
            recyclerView.addDividerDecoration(view.context)
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

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.noticeFlow.collectLatest {
                    adapter.submitData(viewLifecycleOwner.lifecycle, it)
                }
            }
        }
    }
}