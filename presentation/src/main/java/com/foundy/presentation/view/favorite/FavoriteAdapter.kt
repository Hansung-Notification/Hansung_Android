package com.foundy.presentation.view.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.foundy.domain.model.Notice
import com.foundy.presentation.view.MainViewModel
import com.foundy.presentation.databinding.ItemNoticeBinding
import com.foundy.presentation.view.webview.WebViewActivity

class FavoriteAdapter(
    private val viewModel: MainViewModel,
) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    private val notices = ArrayList<Notice>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemNoticeBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(notices[position])
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

        fun bind(notice: Notice) {
            binding.apply {
                title.text = notice.title
                subtitle.text = notice.date + " · " + notice.writer
                newIcon.visibility = View.GONE
                favButton.isChecked = viewModel.isFavorite(notice)

                noticeItem.setOnClickListener {
                    val intent = WebViewActivity.getIntent(root.context, notice)
                    root.context.startActivity(intent)
                }
                favButton.setOnClickListener {
                    if ((it as ToggleButton).isChecked) {
                        viewModel.addFavoriteItem(notice)
                    } else {
                        viewModel.removeFavoriteItem(notice)
                        remove(notice)
                    }
                }
            }
        }
    }
}