package com.foundy.presentation.view.keyword

import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.foundy.domain.model.Keyword
import com.foundy.presentation.databinding.ItemKeywordBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class KeywordViewHolder(
    private val binding: ItemKeywordBinding,
    private val lifecycleScope: LifecycleCoroutineScope? = null,
    private val onClickDelete: (keyword: String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(keyword: Keyword) {
        binding.apply {
            title.text = keyword.title

            deleteButton.setOnClickListener {
                // 빠르게 여러번 누르는 것을 방지한다.
                val scope = lifecycleScope ?: it.findViewTreeLifecycleOwner()?.lifecycleScope
                scope?.launch {
                    deleteButton.isEnabled = false
                    delay(500)
                    deleteButton.isEnabled = true
                }
                onClickDelete(keyword.title)
            }
        }
    }
}