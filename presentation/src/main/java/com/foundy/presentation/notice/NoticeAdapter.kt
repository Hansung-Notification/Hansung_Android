package com.foundy.presentation.notice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.foundy.domain.model.Notice
import com.foundy.presentation.databinding.ItemNoticeBinding

class NoticeAdapter: RecyclerView.Adapter<NoticeAdapter.ViewHolder>() {

    private val items = ArrayList<Notice>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemNoticeBinding.inflate(layoutInflater)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setContent(position)
    }

    override fun getItemCount(): Int = items.size

    fun updateList(noticeList: List<Notice>) {
        items.addAll(noticeList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(
        private val binding: ItemNoticeBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun setContent(position: Int) {
            binding.apply {
                val item = items[position]
                title.text = item.title
                subtitle.text = item.writer + " · " + item.date
            }
        }
    }
}