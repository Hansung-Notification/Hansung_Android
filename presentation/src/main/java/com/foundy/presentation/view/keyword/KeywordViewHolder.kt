package com.foundy.presentation.view.keyword

import androidx.recyclerview.widget.RecyclerView
import com.foundy.presentation.databinding.ItemKeywordBinding
import com.foundy.presentation.model.KeywordUiState

class KeywordViewHolder(
    private val binding: ItemKeywordBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(keywordUiState: KeywordUiState) {
        binding.apply {
            title.text = keywordUiState.keyword

            deleteButton.setOnClickListener {
                keywordUiState.onClickDelete()
            }
        }
    }
}