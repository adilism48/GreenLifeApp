package com.example.greenlifeapp.view.news

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.greenlifeapp.model.News

class NewsAdapter : ListAdapter<News, NewsViewHolder>(NewsDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}