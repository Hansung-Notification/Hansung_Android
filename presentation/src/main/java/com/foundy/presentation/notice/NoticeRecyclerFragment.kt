package com.foundy.presentation.notice

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.foundy.presentation.R
import com.foundy.presentation.databinding.FragmentNoticeRecyclerViewBinding

class NoticeRecyclerFragment : Fragment(R.layout.fragment_notice_recycler_view) {

    private val viewModel: NoticeViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentNoticeRecyclerViewBinding.bind(view)

        binding.apply {
            recyclerView.adapter = NoticeAdapter()
            recyclerView.layoutManager = LinearLayoutManager(context)
        }

        viewModel.noticeList.observe(viewLifecycleOwner) {
            (binding.recyclerView.adapter as NoticeAdapter).addAll(it)
        }
    }
}