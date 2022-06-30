package com.foundy.presentation.view.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.foundy.presentation.databinding.ItemNoticeBinding
import com.foundy.presentation.model.NoticeUiState

class FavoriteAdapter : RecyclerView.Adapter<FavoriteViewHolder>() {

    private val notices = ArrayList<NoticeUiState>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemNoticeBinding.inflate(layoutInflater, parent, false)
        return FavoriteViewHolder(binding, ::remove)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(notices[position])
    }

    override fun getItemCount(): Int = notices.size

    fun addAll(noticeUiStates: List<NoticeUiState>) {
        val startIndex = notices.size
        notices.addAll(noticeUiStates)
        notifyItemRangeInserted(startIndex, noticeUiStates.size)
    }

    private fun remove(noticeUiState: NoticeUiState) {
        val index = notices.indexOf(noticeUiState)
        notices.removeAt(index)
        notifyItemRemoved(index)
    }
}