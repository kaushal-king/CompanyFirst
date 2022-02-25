package com.the.firsttask.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiClient {
    val BASE_URL: String = "https://api.themoviedb.org/3/movie/"

 private var retrofit: Retrofit? = null

    var interceptorHttp = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    var okHttpClient: OkHttpClient = OkHttpClient()
         .newBuilder()
         .addInterceptor(interceptorHttp)
    .build()

    fun getClient(): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
        }
        return retrofit
    }
}