package com.foundy.presentation.view.home.notice

import android.widget.ToggleButton
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.foundy.presentation.R
import com.foundy.presentation.databinding.ItemNoticeBinding
import com.foundy.presentation.extension.addRipple
import com.foundy.presentation.extension.setBackgroundColor
import com.foundy.presentation.model.NoticeItemUiState
import com.foundy.presentation.view.webview.WebViewActivity

class NoticeViewHolder(
    private val binding: ItemNoticeBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(noticeItemUiState: NoticeItemUiState?) {
        binding.apply {
            if (noticeItemUiState == null) {
                title.text = root.context.getString(R.string.invalid_data)
                return@apply
            }
            val notice = noticeItemUiState.notice
            title.text = notice.title
            subtitle.text = root.context.getString(R.string.notice_subtitle, notice.date, notice.writer)
            newIcon.isVisible = notice.isNew
            favButton.isVisible = !notice.isHeader
            favButton.isChecked = noticeItemUiState.isFavorite()
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
                noticeItemUiState.onClickFavorite(isChecked)
            }
        }
    }
}