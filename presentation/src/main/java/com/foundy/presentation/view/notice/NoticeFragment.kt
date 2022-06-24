package com.foundy.presentation.view.notice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.foundy.presentation.view.MainViewModel
import com.foundy.presentation.R
import com.foundy.presentation.databinding.FragmentNoticeBinding
import com.foundy.presentation.extension.addDividerDecoration

class NoticeFragment : Fragment(R.layout.fragment_notice) {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentNoticeBinding.bind(view)

        binding.apply {
            recyclerView.addDividerDecoration(view.context)
            recyclerView.adapter = NoticeAdapter.normal(viewModel)
            recyclerView.layoutManager = LinearLayoutManager(context)
        }

        viewModel.noticeList.observe(viewLifecycleOwner) {
            (binding.recyclerView.adapter as NoticeAdapter).addAll(it)
        }
        viewModel.isNetworkError.observe(viewLifecycleOwner) { isError ->
            binding.errorFragment.visibility = if (isError) View.VISIBLE else View.GONE
        }
    }
}