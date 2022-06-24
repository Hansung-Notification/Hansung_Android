package com.foundy.presentation.view.notice

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.foundy.presentation.view.MainViewModel
import com.foundy.presentation.R
import com.foundy.presentation.databinding.FragmentErrorBinding

class ErrorFragment : Fragment(R.layout.fragment_error) {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentErrorBinding.bind(view)

        binding.apply {
            message.text = "연결 실패" // TODO 디테일 하게 표현하도록 메시지를 전달받아서 보이기
            retryButton.setOnClickListener {
                viewModel.updateNoticeList()
            }
        }
    }
}