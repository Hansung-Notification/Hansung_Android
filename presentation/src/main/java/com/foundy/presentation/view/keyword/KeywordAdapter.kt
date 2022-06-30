package com.foundy.presentation.view.keyword

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.foundy.presentation.databinding.ItemKeywordBinding
import com.foundy.presentation.model.KeywordUiState

class KeywordAdapter : RecyclerView.Adapter<KeywordViewHolder>() {

    private val keywords = ArrayList<KeywordUiState>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeywordViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemKeywordBinding.inflate(layoutInflater, parent, false)
        return KeywordViewHolder(binding, ::remove)
    }

    override fun onBindViewHolder(holder: KeywordViewHolder, position: Int) {
        holder.bind(keywords[position])
    }

    override fun getItemCount(): Int = keywords.size

    fun addAll(keywordUiState: List<KeywordUiState>) {
        val startIndex = keywords.size
        keywords.addAll(keywordUiState)
        notifyItemRangeInserted(startIndex, keywordUiState.size)
    }

    private fun remove(keywordUiState: KeywordUiState) {
        val index = keywords.indexOf(keywordUiState)
        keywords.removeAt(index)
        notifyItemRemoved(index)
    }
}