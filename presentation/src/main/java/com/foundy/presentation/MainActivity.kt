package com.foundy.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.foundy.presentation.notice.NoticeAdapter
import com.foundy.presentation.databinding.ActivityMainBinding
import com.foundy.presentation.notice.NoticeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: NoticeViewModel by viewModels()
    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding get() = requireNotNull(_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initEvent()
    }

    private fun initView() {
        binding.apply {
            recyclerView.adapter = NoticeAdapter()
            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun initEvent() {
        viewModel.noticeList.observe(this) {
            (binding.recyclerView.adapter as NoticeAdapter).updateList(it)
        }
    }
}