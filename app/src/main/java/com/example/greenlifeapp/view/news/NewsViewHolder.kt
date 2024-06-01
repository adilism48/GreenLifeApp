package com.example.greenlifeapp.view.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.greenlifeapp.databinding.NewsListItemBinding
import com.example.greenlifeapp.model.News

class NewsViewHolder(private val binding: NewsListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(news: News) = with(binding) {
        tvTitle.text = news.newsTitle
        tvText.text = news.newsText
        tvCreatedAt.text = news.createdAt
    }

    companion object {
        fun create(parent: ViewGroup): NewsViewHolder {
            return NewsViewHolder(
                NewsListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }
}