package com.example.greenlifeapp.view.news

import androidx.recyclerview.widget.DiffUtil
import com.example.greenlifeapp.model.News

class NewsDiffCallback : DiffUtil.ItemCallback<News>() {
    override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
        return oldItem == newItem
    }
}