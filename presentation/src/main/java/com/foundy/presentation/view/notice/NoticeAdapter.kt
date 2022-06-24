package com.foundy.presentation.view.notice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.foundy.domain.model.Notice
import com.foundy.presentation.R
import com.foundy.presentation.view.MainViewModel
import com.foundy.presentation.databinding.ItemNoticeBinding
import com.foundy.presentation.view.webview.WebViewActivity
import com.foundy.presentation.extension.*

enum class NoticeAdapterStyle {
    NORMAL,
    FAVORITE_ONLY
}

class NoticeAdapter internal constructor(
    private val viewModel: MainViewModel,
    private val style: NoticeAdapterStyle
) : RecyclerView.Adapter<NoticeAdapter.ViewHolder>() {

    companion object {

        /**
         * 즐겨찾기가 아닌 공지사항도 포함하는 리사이클러 뷰 어뎁터를 생성한다.
         */
        fun normal(viewModel: MainViewModel) = NoticeAdapter(viewModel, NoticeAdapterStyle.NORMAL)

        /**
         * 즐겨찾기가 이닌 공지사항은 리스트에서 제거하는 리사이클러 뷰 어뎁터를 생성한다.
         */
        fun favoriteOnly(viewModel: MainViewModel) =
            NoticeAdapter(viewModel, NoticeAdapterStyle.FAVORITE_ONLY)
    }

    private val notices = ArrayList<Notice>()

    private val isFavoriteOnly get() = style == NoticeAdapterStyle.FAVORITE_ONLY

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemNoticeBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setContent(position)
    }

    override fun getItemCount(): Int = notices.size

    fun addAll(noticeList: List<Notice>) {
        val startIndex = notices.size
        notices.addAll(noticeList)
        notifyItemRangeInserted(startIndex, noticeList.size)
    }

    private fun remove(notice: Notice) {
        val index = notices.indexOf(notice)
        notices.removeAt(index)
        notifyItemRemoved(index)
    }

    inner class ViewHolder(
        private val binding: ItemNoticeBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun setContent(position: Int) {
            binding.apply {
                val notice = notices[position]

                title.text = notice.title
                subtitle.text = notice.writer + " · " + notice.date
                newIcon.visibility = if (notice.isNew && !isFavoriteOnly) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
                if (notice.isHeader) {
                    noticeItem.setBackgroundColor(R.color.purple_200, 30)
                    favButton.visibility = View.GONE
                } else {
                    noticeItem.addRipple()
                    favButton.visibility = View.VISIBLE
                    favButton.isChecked = viewModel.isFavorite(notice)
                }

                noticeItem.setOnClickListener {
                    val intent = WebViewActivity.getIntent(root.context, notice)
                    root.context.startActivity(intent)
                }
                favButton.setOnClickListener {
                    val isChecked = (it as ToggleButton).isChecked

                    if (isChecked) {
                        viewModel.addFavoriteItem(notice)
                    } else {
                        viewModel.removeFavoriteItem(notice)
                        if (isFavoriteOnly) remove(notice)
                    }
                }
            }
        }
    }
}