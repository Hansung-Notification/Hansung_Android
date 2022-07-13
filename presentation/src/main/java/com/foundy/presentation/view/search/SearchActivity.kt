package com.foundy.presentation.view.search

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.foundy.presentation.databinding.ActivitySearchBinding
import com.foundy.presentation.extension.addDividerDecoration
import com.foundy.presentation.model.NoticeItemUiState
import com.foundy.presentation.model.SearchUiState
import com.foundy.presentation.view.common.PagingLoadStateAdapter
import com.foundy.presentation.view.home.notice.NoticeAdapter
import com.paulrybitskyi.persistentsearchview.adapters.model.SuggestionItem
import com.paulrybitskyi.persistentsearchview.listeners.OnSuggestionChangeListener
import com.paulrybitskyi.persistentsearchview.utils.SuggestionCreationUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    private val viewModel: SearchViewModel by viewModels()

    private var _binding: ActivitySearchBinding? = null
    private val binding: ActivitySearchBinding get() = requireNotNull(_binding)

    private val adapter = NoticeAdapter()

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, SearchActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initSearchView()
        initRecyclerView()
        initNoSearchResultText()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect(::updateUi)
            }
        }
    }

    override fun onBackPressed() {
        // `PersistentSearchView`의 left button 동작과 통일을 한다.
        if (binding.searchView.isExpanded) {
            binding.searchView.collapse()
        } else {
            super.onBackPressed()
        }
    }

    private fun initSearchView() = with(binding.searchView) {
        expand(true)

        setOnSearchConfirmedListener { searchView, query ->
            searchView.collapse()
            searchNotices(query)
            viewModel.addOrUpdateRecent(query)
        }
        setOnSuggestionChangeListener(object : OnSuggestionChangeListener {
            override fun onSuggestionPicked(suggestion: SuggestionItem?) {
                val query = suggestion?.itemModel?.text
                query?.let {
                    searchNotices(it)
                    lifecycleScope.launch {
                        // 검색 항목을 누르자마자 순서가 변경되어 보기 안좋기 때문에 딜레이를 준다.
                        delay(400)
                        viewModel.addOrUpdateRecent(it)
                    }
                }
            }

            override fun onSuggestionRemoved(suggestion: SuggestionItem?) {
                suggestion?.itemModel?.text?.let { viewModel.removeRecent(it) }
            }
        })
        setOnLeftBtnClickListener {
            finish()
        }
    }

    private fun initRecyclerView() = with(binding.recyclerView) {
        this.addDividerDecoration()
        this.adapter = this@SearchActivity.adapter.withLoadStateFooter(
            PagingLoadStateAdapter { this@SearchActivity.adapter.retry() }
        )
        layoutManager = LinearLayoutManager(context)
    }

    private fun initNoSearchResultText() {
        adapter.addLoadStateListener { loadStates ->
            val shouldShowNoSearchResultText =
                loadStates.refresh is LoadState.NotLoading && adapter.itemCount < 1
            binding.noSearchResultText.isVisible = shouldShowNoSearchResultText
        }
    }

    private fun updateUi(uiState: SearchUiState) {
        updateSearchView(uiState.recentQueries)
        updateRecyclerView(uiState.searchedNoticePagingData)
    }

    private fun updateSearchView(recentQueries: List<String>) = with(binding.searchView) {
        val recentList = SuggestionCreationUtil.asRecentSearchSuggestions(recentQueries)
        setSuggestions(recentList, false)
    }

    private fun updateRecyclerView(pagingData: PagingData<NoticeItemUiState>) {
        adapter.submitData(lifecycle, pagingData)
    }

    private fun searchNotices(query: String) {
        adapter.submitData(lifecycle, PagingData.empty())
        viewModel.searchNotices(query)
    }
}