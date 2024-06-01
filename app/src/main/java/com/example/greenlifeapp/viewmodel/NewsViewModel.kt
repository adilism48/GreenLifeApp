package com.example.greenlifeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.greenlifeapp.model.NewsRepository
import kotlinx.coroutines.Dispatchers


class NewsViewModel(private val userRepository: NewsRepository) : ViewModel() {
    val users = liveData(Dispatchers.IO) {
        val retrievedUsers = userRepository.getUsers()
        emit(retrievedUsers)
    }
}