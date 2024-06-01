package com.example.greenlifeapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.greenlifeapp.data.PostRepositoryImpl
import com.example.greenlifeapp.model.Post
import com.google.firebase.database.ValueEventListener

class PostViewModel: ViewModel() {
    private val repository = PostRepositoryImpl

    fun addPost(post: Post) {
        repository.addPost(post)
    }

    fun getPost(listener: ValueEventListener) {
        repository.getPosts(listener)
    }

    fun getRecommendedPosts(listener: ValueEventListener, currentTag: String) {
        repository.getRecommendedPosts(listener, currentTag)
    }
}