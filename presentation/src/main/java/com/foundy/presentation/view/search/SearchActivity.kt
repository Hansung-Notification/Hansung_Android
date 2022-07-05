package com.foundy.presentation.view.search

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.foundy.presentation.databinding.ActivitySearchBinding
import com.foundy.presentation.view.MainViewModel
import com.foundy.presentation.view.common.PagingLoadStateAdapter
import com.foundy.presentation.view.notice.NoticeAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

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
        initToolBar()
        initSearchView(adapter)
        initRecyclerView(adapter)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun initToolBar() {
        binding.toolbar.apply {
            title = ""
            setSupportActionBar(this)
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initSearchView(adapter: NoticeAdapter) {
        binding.searchView.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                override fun onQueryTextSubmit(query: String?): Boolean {
                    lifecycleScope.launch {
                        viewModel.search(query ?: "").collectLatest {
                            adapter.submitData(lifecycle, it)
                        }
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }
    }

    private fun initRecyclerView(adapter: NoticeAdapter) {
        binding.recyclerView.apply {
            this.adapter = adapter.withLoadStateFooter(
                PagingLoadStateAdapter { adapter.retry() }
            )
            layoutManager = LinearLayoutManager(context)
        }
    }
}