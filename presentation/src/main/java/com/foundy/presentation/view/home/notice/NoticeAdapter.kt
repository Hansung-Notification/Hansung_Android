package com.foundy.presentation.view.home.notice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.foundy.presentation.databinding.ItemNoticeBinding
import com.foundy.presentation.model.NoticeItemUiState

class NoticeAdapter : PagingDataAdapter<NoticeItemUiState, NoticeViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemNoticeBinding.inflate(layoutInflater, parent, false)
        return NoticeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoticeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<NoticeItemUiState>() {
            override fun areItemsTheSame(oldItem: NoticeItemUiState, newItem: NoticeItemUiState): Boolean {
                return oldItem.notice.url == newItem.notice.url
            }

            override fun areContentsTheSame(
                oldItem: NoticeItemUiState,
                newItem: NoticeItemUiState
            ): Boolean {
                return oldItem.notice == newItem.notice
            }
        }
    }
}