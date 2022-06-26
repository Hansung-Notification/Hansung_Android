package com.foundy.presentation.view.notice

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ToggleButton
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.foundy.domain.model.Notice
import com.foundy.presentation.R
import com.foundy.presentation.view.MainViewModel
import com.foundy.presentation.databinding.ItemNoticeBinding
import com.foundy.presentation.extension.addRipple
import com.foundy.presentation.extension.setBackgroundColor
import com.foundy.presentation.view.webview.WebViewActivity

class NoticeAdapter(
    private val viewModel: MainViewModel,
) : PagingDataAdapter<Notice, NoticeAdapter.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemNoticeBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemNoticeBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(notice: Notice?) {
            binding.apply {
                if (notice == null) {
                    title.text = binding.root.context.getString(R.string.invalid_data)
                    return@apply
                }
                title.text = notice.title
                subtitle.text = notice.date + " · " + notice.writer
                newIcon.isVisible = notice.isNew
                favButton.isVisible = !notice.isHeader
                favButton.isChecked = viewModel.isFavorite(notice)
                if (notice.isHeader) {
                    noticeItem.setBackgroundColor(R.color.purple_200, 30)
                } else {
                    noticeItem.addRipple()
                }

                noticeItem.setOnClickListener {
                    val intent = WebViewActivity.getIntent(root.context, notice)
                    root.context.startActivity(intent)
                }
                favButton.setOnClickListener {
                    if ((it as ToggleButton).isChecked) {
                        viewModel.addFavoriteItem(notice)
                    } else {
                        viewModel.removeFavoriteItem(notice)
                    }
                }
            }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Notice>() {
            override fun areItemsTheSame(oldItem: Notice, newItem: Notice): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: Notice, newItem: Notice): Boolean {
                return oldItem.url == newItem.url
            }
        }
    }
}