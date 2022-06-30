package com.foundy.presentation.view.keyword

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.foundy.presentation.databinding.ItemKeywordBinding
import com.foundy.presentation.model.KeywordUiState

class KeywordAdapter : ListAdapter<KeywordUiState, KeywordViewHolder>(KeywordsComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeywordViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemKeywordBinding.inflate(layoutInflater, parent, false)
        return KeywordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: KeywordViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class KeywordsComparator : DiffUtil.ItemCallback<KeywordUiState>() {
        override fun areItemsTheSame(oldItem: KeywordUiState, newItem: KeywordUiState): Boolean {
            return oldItem.keyword == newItem.keyword
        }

        override fun areContentsTheSame(oldItem: KeywordUiState, newItem: KeywordUiState): Boolean {
            return oldItem.keyword == newItem.keyword
        }
    }
}