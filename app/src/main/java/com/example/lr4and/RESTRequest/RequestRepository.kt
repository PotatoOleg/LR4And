package com.example.lr4and.RESTRequest

import com.google.gson.annotations.SerializedName

data class RequestRepository (

        val userId:Int,
        val id:Int,
        val title:String,

        @SerializedName("body")
        val text:String
    )