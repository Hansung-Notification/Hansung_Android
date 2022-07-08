package com.foundy.presentation.view.notice

import android.widget.ToggleButton
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.foundy.presentation.R
import com.foundy.presentation.databinding.ItemNoticeBinding
import com.foundy.presentation.extension.addRipple
import com.foundy.presentation.extension.setBackgroundColor
import com.foundy.presentation.model.NoticeUiState
import com.foundy.presentation.view.webview.WebViewActivity

class NoticeViewHolder(
    private val binding: ItemNoticeBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(noticeUiState: NoticeUiState?) {
        binding.apply {
            if (noticeUiState == null) {
                title.text = root.context.getString(R.string.invalid_data)
                return@apply
            }
            val notice = noticeUiState.notice
            title.text = notice.title
            subtitle.text = root.context.getString(R.string.notice_subtitle, notice.date, notice.writer)
            newIcon.isVisible = notice.isNew
            favButton.isVisible = !notice.isHeader
            favButton.isChecked = noticeUiState.isFavorite()
            if (notice.isHeader) {
                noticeItem.setBackgroundColor(androidx.appcompat.R.attr.colorPrimary, 30)
            } else {
                noticeItem.addRipple()
            }

            noticeItem.setOnClickListener {
                val intent = WebViewActivity.getIntent(root.context, notice)
                root.context.startActivity(intent)
            }

            favButton.setOnClickListener {
                val isChecked = (it as ToggleButton).isChecked
                noticeUiState.onClickFavorite(isChecked)
            }
        }
    }
}