package com.example.greenlifeapp.data

import com.example.greenlifeapp.model.Post
import com.example.greenlifeapp.model.PostRepository
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

object PostRepositoryImpl : PostRepository {
    private val myRef = Firebase.database.getReference("posts")

    override fun addPost(post: Post) {
        myRef.child(myRef.push().key ?: "").setValue(post)
    }

    override fun getPosts(listener: ValueEventListener) {
        myRef.addValueEventListener(listener)
    }

    override fun getRecommendedPosts(listener: ValueEventListener, currentTag: String) {
        myRef.orderByChild("tag").equalTo(currentTag).addValueEventListener(listener)
    }
}