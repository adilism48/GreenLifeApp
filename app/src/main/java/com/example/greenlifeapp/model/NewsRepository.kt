package com.example.greenlifeapp.model

import com.example.greenlifeapp.data.ApiService

class NewsRepository(private val apiService: ApiService) {
    suspend fun getUsers() = apiService.getUsers()
}