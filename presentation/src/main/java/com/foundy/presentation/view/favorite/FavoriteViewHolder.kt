package com.foundy.presentation.view.favorite

import android.view.View
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.foundy.presentation.R
import com.foundy.presentation.databinding.ItemNoticeBinding
import com.foundy.presentation.view.notice.NoticeUiState
import com.foundy.presentation.view.webview.WebViewActivity

class FavoriteViewHolder(
    private val binding: ItemNoticeBinding,
    private val remove: (NoticeUiState) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(noticeUiState: NoticeUiState) {
        binding.apply {
            val notice = noticeUiState.notice
            title.text = notice.title
            subtitle.text = root.context.getString(R.string.notice_subtitle, notice.date, notice.writer)
            newIcon.visibility = View.GONE
            favButton.isChecked = noticeUiState.isFavorite()

            noticeItem.setOnClickListener {
                val intent = WebViewActivity.getIntent(root.context, notice)
                root.context.startActivity(intent)
            }
            favButton.setOnClickListener {
                val isChecked = (it as ToggleButton).isChecked
                noticeUiState.onClickFavorite(isChecked)
                if (!isChecked) remove(noticeUiState)
            }
        }
    }
}