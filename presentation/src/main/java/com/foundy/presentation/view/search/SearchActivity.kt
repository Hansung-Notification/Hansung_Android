package com.foundy.presentation.view.search

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.foundy.presentation.databinding.ActivitySearchBinding
import com.foundy.presentation.extension.addDividerDecoration
import com.foundy.presentation.view.home.HomeViewModel
import com.foundy.presentation.view.common.PagingLoadStateAdapter
import com.foundy.presentation.view.home.notice.NoticeAdapter
import com.paulrybitskyi.persistentsearchview.adapters.model.SuggestionItem
import com.paulrybitskyi.persistentsearchview.listeners.OnSuggestionChangeListener
import com.paulrybitskyi.persistentsearchview.utils.SuggestionCreationUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    private val homeViewModel: HomeViewModel by viewModels()
    private val searchViewModel: SearchViewModel by viewModels()

    private var _binding: ActivitySearchBinding? = null
    private val binding: ActivitySearchBinding get() = requireNotNull(_binding)

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, SearchActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = NoticeAdapter()
        initSearchView(adapter)
        initRecyclerView(adapter)
        initNoSearchResultText(adapter)
    }

    override fun onBackPressed() {
        // `PersistentSearchView`의 left button 동작과 통일을 한다.
        if (binding.searchView.isExpanded) {
            binding.searchView.collapse()
        } else {
            super.onBackPressed()
        }
    }

    private fun initSearchView(adapter: NoticeAdapter) = with(binding.searchView) {
        expand(true)

        searchViewModel.recentQueries.observe(this@SearchActivity) {
            val recentList = SuggestionCreationUtil.asRecentSearchSuggestions(it)
            setSuggestions(recentList, false)
        }
        setOnSearchConfirmedListener { searchView, query ->
            searchView.collapse()
            searchNotices(query, adapter)
            searchViewModel.addOrUpdateRecent(query)
        }
        setOnSuggestionChangeListener(object : OnSuggestionChangeListener {
            override fun onSuggestionPicked(suggestion: SuggestionItem?) {
                val query = suggestion?.itemModel?.text
                query?.let {
                    searchNotices(it, adapter)
                    lifecycleScope.launch {
                        // 검색 항목을 누르자마자 순서가 변경되어 보기 안좋기 때문에 딜레이를 준다.
                        delay(400)
                        searchViewModel.addOrUpdateRecent(it)
                    }
                }
            }

            override fun onSuggestionRemoved(suggestion: SuggestionItem?) {
                suggestion?.itemModel?.text?.let { searchViewModel.removeRecent(it) }
            }
        })
        setOnLeftBtnClickListener {
            finish()
        }
    }

    private fun initRecyclerView(adapter: NoticeAdapter) = with(binding.recyclerView) {
        this.addDividerDecoration()
        this.adapter = adapter.withLoadStateFooter(
            PagingLoadStateAdapter { adapter.retry() }
        )
        layoutManager = LinearLayoutManager(context)
    }

    private fun initNoSearchResultText(adapter: NoticeAdapter) {
        adapter.addLoadStateListener { loadStates ->
            val shouldShowNoSearchResultText = loadStates.refresh is LoadState.NotLoading && adapter.itemCount < 1
            binding.noSearchResultText.isVisible = shouldShowNoSearchResultText
        }
    }

    private fun searchNotices(query: String, adapter: NoticeAdapter) {
        adapter.submitData(lifecycle, PagingData.empty())
        lifecycleScope.launch {
            homeViewModel.searchNotices(query).collectLatest {
                adapter.submitData(lifecycle, it)
            }
        }
    }
}