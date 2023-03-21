package com.example.lr4and.RESTRequest

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Part

interface Api {

    @GET("posts")
    fun getPosts(): Call<List<RequestRepository?>?>?
}