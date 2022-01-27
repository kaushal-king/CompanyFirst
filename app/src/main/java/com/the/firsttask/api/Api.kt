package com.the.firsttask.api

import MovielistDataClass
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {


    @GET("top_rated")
    fun topRatedlist(
        @Query("api_key") apiKey: String?,
    ): Call<MovielistDataClass>


    @GET("popular")
    fun popularList(
        @Query("api_key") apiKey: String?,
    ): Call<MovielistDataClass>
}