package com.foundy.presentation.notice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.foundy.presentation.R
import com.foundy.presentation.databinding.FragmentNoticeBinding

class NoticeFragment : Fragment(R.layout.fragment_notice) {

    private val viewModel: NoticeViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentNoticeBinding.bind(view)

        binding.apply {
            recyclerView.adapter = NoticeAdapter()
            recyclerView.layoutManager = LinearLayoutManager(context)
        }

        viewModel.noticeList.observe(viewLifecycleOwner) {
            (binding.recyclerView.adapter as NoticeAdapter).addAll(it)
        }
        viewModel.isError.observe(viewLifecycleOwner) { isError ->
            binding.errorFragment.visibility = if (isError) View.VISIBLE else View.GONE
        }
    }
}