package com.example.greenlifeapp.data

import com.example.greenlifeapp.model.News
import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface ApiService {
    @GET("news")
    suspend fun getUsers(): List<News>

    companion object {
        private const val BASE_URL = "https://665ac6e7003609eda45ec7af.mockapi.io/GLApi/"

        fun create(): ApiService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}