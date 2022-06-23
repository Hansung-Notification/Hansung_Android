package com.foundy.presentation.notice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.foundy.domain.model.Notice
import com.foundy.presentation.databinding.ItemNoticeBinding
import com.foundy.presentation.webview.WebViewActivity

class NoticeAdapter: RecyclerView.Adapter<NoticeAdapter.ViewHolder>() {

    private val notices = ArrayList<Notice>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemNoticeBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setContent(position)
    }

    override fun getItemCount(): Int = notices.size

    fun updateList(noticeList: List<Notice>) {
        notices.addAll(noticeList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(
        private val binding: ItemNoticeBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun setContent(position: Int) {
            binding.apply {
                val notice = notices[position]
                title.text = notice.title
                subtitle.text = notice.writer + " · " + notice.date

                noticeItem.setOnClickListener {
                    val intent = WebViewActivity.getIntent(root.context, notice)
                    root.context.startActivity(intent)
                }
            }
        }
    }
}