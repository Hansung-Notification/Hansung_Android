package com.foundy.presentation.view.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.foundy.presentation.databinding.ItemNoticeBinding
import com.foundy.presentation.model.NoticeUiState

class FavoriteAdapter : ListAdapter<NoticeUiState, FavoriteViewHolder>(KeywordsComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemNoticeBinding.inflate(layoutInflater, parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class KeywordsComparator : DiffUtil.ItemCallback<NoticeUiState>() {
        override fun areItemsTheSame(oldItem: NoticeUiState, newItem: NoticeUiState): Boolean {
            return oldItem.notice.url == newItem.notice.url
        }

        override fun areContentsTheSame(oldItem: NoticeUiState, newItem: NoticeUiState): Boolean {
            return oldItem.notice.url == newItem.notice.url
        }
    }
}
