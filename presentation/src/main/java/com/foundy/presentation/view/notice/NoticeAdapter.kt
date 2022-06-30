package com.foundy.presentation.view.notice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.foundy.presentation.databinding.ItemNoticeBinding
import com.foundy.presentation.model.NoticeUiState

class NoticeAdapter : PagingDataAdapter<NoticeUiState, NoticeViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemNoticeBinding.inflate(layoutInflater, parent, false)
        return NoticeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoticeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<NoticeUiState>() {
            override fun areItemsTheSame(oldItem: NoticeUiState, newItem: NoticeUiState): Boolean {
                return oldItem.notice.url == newItem.notice.url
            }

            override fun areContentsTheSame(
                oldItem: NoticeUiState,
                newItem: NoticeUiState
            ): Boolean {
                return oldItem.notice.url == newItem.notice.url
            }
        }
    }
}