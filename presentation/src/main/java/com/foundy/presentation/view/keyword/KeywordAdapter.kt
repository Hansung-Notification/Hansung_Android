package com.foundy.presentation.view.keyword

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.foundy.domain.model.Keyword
import com.foundy.presentation.databinding.ItemKeywordBinding

class KeywordAdapter(
    private val onClickDelete: (keyword: String) -> Unit,
    private val lifecycleScope: LifecycleCoroutineScope? = null
) : ListAdapter<Keyword, KeywordViewHolder>(KeywordsComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeywordViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemKeywordBinding.inflate(layoutInflater, parent, false)
        return KeywordViewHolder(binding, lifecycleScope, onClickDelete)
    }

    override fun onBindViewHolder(holder: KeywordViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class KeywordsComparator : DiffUtil.ItemCallback<Keyword>() {
        override fun areItemsTheSame(oldItem: Keyword, newItem: Keyword): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Keyword, newItem: Keyword): Boolean {
            return oldItem == newItem
        }
    }
}