package com.foundy.presentation.view.home.favorite

import android.view.View
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.foundy.presentation.R
import com.foundy.presentation.databinding.ItemNoticeBinding
import com.foundy.presentation.model.NoticeItemUiState
import com.foundy.presentation.view.webview.WebViewActivity

class FavoriteViewHolder(
    private val binding: ItemNoticeBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(noticeItemUiState: NoticeItemUiState) {
        binding.apply {
            val notice = noticeItemUiState.notice
            title.text = notice.title
            subtitle.text = root.context.getString(R.string.notice_subtitle, notice.date, notice.writer)
            newIcon.visibility = View.GONE
            favButton.isChecked = noticeItemUiState.isFavorite()

            noticeItem.setOnClickListener {
                val intent = WebViewActivity.getIntent(root.context, notice)
                root.context.startActivity(intent)
            }
            favButton.setOnClickListener {
                val isChecked = (it as ToggleButton).isChecked
                noticeItemUiState.onClickFavorite(isChecked)
            }
        }
    }
}